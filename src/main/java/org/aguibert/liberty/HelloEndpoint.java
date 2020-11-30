package org.aguibert.liberty;

import javax.enterprise.context.ApplicationScoped;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.json.bind.config.PropertyNamingStrategy;
import javax.json.bind.config.PropertyOrderStrategy;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
@ApplicationScoped
public class HelloEndpoint {

    @GET
    public String sayHello() {
        Book myBook = new Book(0001, "CICS Book1", "Tony", 23.99);

        JsonbConfig config = new JsonbConfig()
                .withPropertyNamingStrategy(PropertyNamingStrategy.UPPER_CAMEL_CASE_WITH_SPACES)
                .withPropertyOrderStrategy(PropertyOrderStrategy.LEXICOGRAPHICAL)//
                .withFormatting(true);

        Jsonb jsonb = JsonbBuilder.create(config);
        String json = jsonb.toJson(myBook);
        System.out.println("Got JSON:\n" + json);
        return json;
    }
}
