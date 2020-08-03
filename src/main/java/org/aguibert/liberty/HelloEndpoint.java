package org.aguibert.liberty;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
@ApplicationScoped
public class HelloEndpoint {

    @GET
    public String sayHello() {
        return "Hello World";
    }
}
