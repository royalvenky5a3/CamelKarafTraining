package com.kodtodya.training.fuse.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.json.JSONObject;
import org.json.XML;

public class ConvertXmltoJson implements Processor{

	@Override
	public void process(Exchange exchange) throws Exception {
		System.out.println("helloo");
		System.out.println(exchange.getIn().getBody(String.class));
		String payload = exchange.getIn().getBody(String.class);
		JSONObject jsonObject = XML.toJSONObject(payload);
		exchange.getIn().setBody(jsonObject.toString());
		
	}
	
	

}
