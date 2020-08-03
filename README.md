# Pre-warming DB connections with Liberty

Sample to accompany Open Liberty blog post

## To run this sample:

Run the following commands in a terminal:

```
# clone the repo
$ git clone git@github.com:aguibert/basic-liberty-mvn.git -b datasource-prewarm
cd basic-liberty-mvn

# in a separate terminal window, start DB2
cd /path/to/basic-liberty-mvn
./startDB2.sh

# in the original terminal window run the app
mvn liberty:dev
```

Once all dependencies have been downloaded and the app is running, you should see this output confirming that the connection pool has been populated:

```
$ mvn liberty:dev
...

[INFO] [AUDIT   ] CWWKT0016I: Web application available (default_host): http://localhost:9080/                                                                                                                                               [INFO] Pre-warming 5 DB connections                                                                                                                                                                                                          [INFO] Done prewarming 5 connections                                                                                                                                                                                                         [INFO] Pool contents are:                                                                                                                                                                                                                    [INFO]   PoolManager@60e806ff                                                                                                                                                                                                                [INFO]     name=WebSphere:type=com.ibm.ws.jca.cm.mbean.ConnectionManagerMBean,jndiName=jdbc/myDB,name=dataSource[default-0]/connectionManager                                                                                                [INFO]     jndiName=jdbc/myDB                                                                                                                                                                                                                [INFO]     maxPoolSize=50                                                                                                                                                                                                                    [INFO]     size=5                                                                                                                                                                                                                            [INFO]     waiting=0                                                                                                                                                                                                                         [INFO]     unshared=0                                                                                                                                                                                                                        [INFO]     shared=0                                                                                                                                                                                                                          [INFO]     available=5                                                                                                                                                                                                                       [INFO]      ManagedConnection@1bfa51cd=Reusable                                                                                                                                                                                              [INFO]      ManagedConnection@e8b54d7=Reusable                                                                                                                                                                                               [INFO]      ManagedConnection@1a46d931=Reusable                                                                                                                                                                                              [INFO]      ManagedConnection@1cf4ef16=Reusable                                                                                                                                                                                              [INFO]      ManagedConnection@18bc30de=Reusable     
```

You can also check the MicroProfile Health readiness endpoint to confirm that the prewarm procedure has completed:

```
$ curl http://localhost:9080/health/ready                                                                            {"checks":[{"data":{},"name":"DB connections warm","status":"UP"}],"status":"UP"}
```

If you want to try this on a database besides DB2, you can find the relevant Docker commands, Maven dependencies, and server.xml configuration here:
link:https://aguibert.github.io/openliberty-cheat-sheet/#_ibm_db2[OpenLiberty Cheat Sheet - Databases]
