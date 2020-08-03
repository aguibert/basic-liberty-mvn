package org.aguibert.liberty;

import java.lang.management.ManagementFactory;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.management.InstanceNotFoundException;
import javax.management.JMX;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.sql.DataSource;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.ibm.ws.jca.cm.mbean.ConnectionManagerMBean;

@ApplicationScoped
public class ConnectionWarmer {
  
  @Inject
  @ConfigProperty(name = "DB_PREWARM_CONNECTIONS", defaultValue = "5")
  int preWarmConnections;
  
  @Resource(lookup = "jdbc/myDB", shareable = false)
  DataSource ds;
  
  private volatile boolean isWarm = false;
  
  public boolean isWarm() {
    return isWarm;
  }
  
  public void warmConnections(@Observes @Initialized(ApplicationScoped.class) Object context) throws Exception {
    if (preWarmConnections < 1)
      return;
    
    System.out.println("Pre-warming " + preWarmConnections + " DB connections");
    
    List<Connection> connections = new ArrayList<>();
    try {
      for (int i = 0; i < preWarmConnections; i++) {
        connections.add(ds.getConnection());
      }
    } finally {
      connections.forEach(this::closeQuietly);
    }
    
    isWarm = true;
    
    System.out.println("Done prewarming " + preWarmConnections + " connections");
    
    // This part is optional and only required if you want to print the connection pool contents
    // to visually verify that the connection pool has been populated
    ConnectionManagerMBean cmBean = getConnecionManagerBean("jdbc/myDB");
    System.out.println("Pool contents are: \n  " + cmBean.showPoolContents().replace("\n", "\n    "));
  }
  
  private void closeQuietly(Connection con) {
    try {
      con.close();
    } catch (SQLException ignore) {
    }
  }

  /**
   * Obtains a proxy to the {@link ConnectionManagerMBean} for the specified datasource.
   * NOTE: This method is not required to pre-warm connections, it's only purpose is to display
   * the pool contents so you can visually confirm that the pool is pre-populated.
   */
  private ConnectionManagerMBean getConnecionManagerBean(String jndiName) throws MalformedObjectNameException, InstanceNotFoundException {
    MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
    ObjectName objectName = new ObjectName("WebSphere:type=" + ConnectionManagerMBean.class.getCanonicalName() + 
        ",jndiName=" + jndiName + ",*");
    Set<ObjectInstance> mbeans = mbs.queryMBeans(objectName, null);
    if (mbeans.size() < 1)
      return null;
    return JMX.newMBeanProxy(mbs, mbeans.iterator().next().getObjectName(), ConnectionManagerMBean.class);
  }

}
