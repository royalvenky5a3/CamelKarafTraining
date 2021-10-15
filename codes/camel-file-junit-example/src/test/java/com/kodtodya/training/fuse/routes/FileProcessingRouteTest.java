package com.kodtodya.training.fuse.routes;

import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.spi.PropertiesComponent;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileProcessingRouteTest extends CamelTestSupport {

    @Before
    public void setup() throws Exception {
        PropertiesComponent pc = context.getPropertiesComponent();
        pc.setLocation("file:src/test/resources/application.properties");
        context.setPropertiesComponent(pc);

        context.addRoutes(new FileProcessingRoute());
        context.start();
    }

    @Test
    public void test000() throws Exception {
        // assert that the CamelContext starts up correctly
        assertTrue(context.getStatus().isStarted());
 
    }

    // known issue for SAXNotRecognizedException: https://issues.apache.org/jira/browse/XERCESJ-1654
    @Test
    public void test001() throws Exception {

        AdviceWithRouteBuilder.adviceWith(context, "file-reader-route", routeBuilder -> {
            routeBuilder.replaceFromWith("direct:send-to-direct");
            routeBuilder.weaveById("direct-producer").replace().to("mock:mockDirectComponent");
//            routeBuilder.onException(Exception.class).setBody(routeBuilder.constant("adviceWith")).continued(true);
            routeBuilder.mockEndpointsAndSkip();
        });
        
        context.start();
        template.sendBody("direct:send-to-direct", new File("src/test/resources/sample-data/PACS.008.001.xml"));

        Thread.sleep(2000);
        String response = getMockEndpoint("mock:mockDirectComponent").getExchanges().get(0).getIn().getBody(String.class);
        System.out.println("response : " + response);
        assertNotNull(response);
        String expected = Files.readString(Path.of("src/test/resources/sample-json-data/pacs.008.001.converted.json"));
        JSONAssert.assertEquals(expected, response, JSONCompareMode.LENIENT);
    }

    @Test
    public void test002() throws Exception {

        String myTestBody = "test-body";

        AdviceWithRouteBuilder.adviceWith(context, "file-writer-route", routeBuilder -> {
            routeBuilder.weaveById("file-writer").replace().to("mock:fileComponent");
            routeBuilder.onException(Exception.class).setBody(routeBuilder.constant("adviceWith")).continued(false);
            routeBuilder.mockEndpointsAndSkip();
        });
        context.start();
        template.sendBody("direct:myDirectComponent", myTestBody);

        Thread.sleep(2000);
        String response = getMockEndpoint("mock:fileComponent").getExchanges().get(0).getIn().getBody(String.class);
        System.out.println("response : " + response);
        assertNotNull(response);
        assertEquals(myTestBody, response);
        context.stop();
    }
}
