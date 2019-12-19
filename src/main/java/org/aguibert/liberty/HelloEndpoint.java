package org.aguibert.liberty;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
@ApplicationScoped
@DeclareRoles("Administrator")
@RolesAllowed("Administrator")
public class HelloEndpoint {

    @GET
    public String sayHello() {
        System.out.println("Hello world");
        return "Hello World";
    }
}
