package com.mycompany.fuse7hello;


import org.apache.camel.Header;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.language.Simple;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Routes extends RouteBuilder {

    @Value("${my.greeting}")
    private String greeting;

    @Override
    public void configure() throws Exception {

        restConfiguration("servlet")
            .bindingMode(RestBindingMode.json)
            .apiContextPath("/swagger") //swagger endpoint path; Final URL: Camel path + apiContextPath: /api/swagger
            .apiContextRouteId("swagger")

            .contextPath("/api")
            .apiProperty("api.title", "Example REST api")
            .apiProperty("api.version", "1.0")
            .apiProperty("host","")
        ;

        from("timer:myTimer?period=2000&repeatCount=3")
            .routeId("timer")
            .log("Hello World!");

        rest()
            .get("hello")
                .route()
                .routeId("hello")
                .setBody().body(()->new HelloResponse(new Timestamp(new Date().getTime()) + " " + greeting +" World!\n"))
                .delay(constant("{{my.delay}}"))
                .removeHeaders("*")
                .endRest()
            //"name" is an Exchange header
            .get("hello/{name}")
                //swagger
                .description("Hello with name")
                .param().name("name").type(RestParamType.path).description("Name of the user").required(true).dataType("string").endParam()
                .responseMessage().code(200).responseModel(HelloResponse.class).endResponseMessage() //OK

                .route()
                .routeId("hello-name")
                .setBody(method(this,"setHelloWithName"))
                .delay(constant("{{my.delay}}"))
                .removeHeaders("*")
                .endRest()
        ;

    }

    //Get exchange header and add to response
    public HelloResponse setHelloWithName(@Simple("${properties:my.greeting} ${header.name}!") String message){
        return new HelloResponse(message);
    }
}
