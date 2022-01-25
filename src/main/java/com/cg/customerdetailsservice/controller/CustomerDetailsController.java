package com.cg.customerdetailsservice.controller;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

@RestController
public class CustomerDetailsController {

    @Autowired
    WebClient.Builder webClientBuilder;

    @Autowired
    DiscoveryClient discoveryClient;

    @RequestMapping("/details")
    public String getCustomerDetails() throws URISyntaxException, MalformedURLException {

        String targetHost =  discoveryClient.getInstances("banking-details-service").stream()
                .map(si->si.getServiceId()).findFirst().get();

        Integer targetPort =  discoveryClient.getInstances("banking-details-service").stream()
                .map(si->si.getPort()).findFirst().get();


        URIBuilder builder = new URIBuilder();
        builder.setScheme("http");
        builder.setHost(targetHost);
        builder.setPort(targetPort);
        builder.setPath("/check");
        //builder.addParameter("abc", "xyz");
        String url = builder.build().toURL().toString();
        System.out.println("Raw url = " + url);

        String bankDetail = webClientBuilder.build()
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        System.out.println("Raw url = " + bankDetail);

        return "Shweta : "+ bankDetail + " ********Services : "+discoveryClient.getServices();
   }

}
