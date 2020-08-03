package org.aguibert.liberty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

@Readiness
@ApplicationScoped
public class WarmConnectionsReady implements HealthCheck {
  
  @Inject
  ConnectionWarmer warmer;

  @Override
  public HealthCheckResponse call() {
    return HealthCheckResponse.named("DB connections warm")
        .state(warmer.isWarm())
        .build();
  }

}
