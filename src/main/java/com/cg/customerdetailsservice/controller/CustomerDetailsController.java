package com.cg.customerdetailsservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;

@RestController
public class CustomerDetailsController {

    @Autowired
    WebClient.Builder webClientBuilder;

    @Autowired
    DiscoveryClient discoveryClient;

    @RequestMapping("/details")
    public String getCustomerDetails(){

        URI uri = discoveryClient.getInstances("banking-details-service").stream()
                        .map(si->si.getUri()).findFirst().map(s->s.resolve("/bankdetails")).get();

        String bankDetail = webClientBuilder.build()
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return "Shweta : "+ bankDetail + " ********Services : "+discoveryClient.getServices();
    }

}
