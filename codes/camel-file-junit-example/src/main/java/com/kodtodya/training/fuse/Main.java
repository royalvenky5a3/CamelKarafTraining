package com.kodtodya.training.fuse;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class Main {

	public static void main(String[] args) throws Exception {

        try (CamelContext camelContext = new DefaultCamelContext()) {

            // Add route to send messages to Kafka

            camelContext.addRoutes(new RouteBuilder() {
                public void configure() {
                    camelContext.getPropertiesComponent().setLocation("classpath:application.properties");
                    
                    from("file:{{input.file.location}}")
                    .routeId("file-reader-route")
                    .log("${body}")
                    .setBody(simple("Hello World"))
                    .log("${body}")
                    .end();
                    
                    
                }
            });
            
            
            
            camelContext.start();

            // let it run for 5 minutes before shutting down
            Thread.sleep(5L * 60 * 1000);

            camelContext.stop();
	}

}
}
