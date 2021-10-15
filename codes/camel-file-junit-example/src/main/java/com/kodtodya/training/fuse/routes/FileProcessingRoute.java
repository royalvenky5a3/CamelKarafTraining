
package com.kodtodya.training.fuse.routes;

import org.apache.camel.InvalidPayloadException;
import org.apache.camel.builder.RouteBuilder;

import com.kodtodya.training.fuse.processors.ConvertXmltoJson;

public class FileProcessingRoute extends RouteBuilder {


    @Override
    public void configure() throws Exception {

        onException(InvalidPayloadException.class)
                .to("log:exceptionLogger");

        // read messages from file & send to file
        from("file:{{input.file.location}}")
        .routeId("file-reader-route")
        .process(new ConvertXmltoJson())
                .to("direct:myDirectComponent").id("direct-producer");

        from("direct:myDirectComponent")
        .routeId("file-writer-route")
              	.to("file:{{output.file.location}}?fileName={{output.file.name}}")
        .id("file-writer");
    }
}