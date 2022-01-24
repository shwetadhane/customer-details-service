package com.cg.customerdetailsservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@EnableDiscoveryClient
public class CustomerDetailsController {

    @Autowired
    WebClient.Builder webClientBuilder;

    @Autowired
    DiscoveryClient discoveryClient;

    @RequestMapping("/details")
    public String getCustomerDetails(){


        String bankDetail = webClientBuilder.build()
                .get()
                .uri("http://172.22.41.201:31229/bankdetails")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return "Shweta : "+ bankDetail + " ********Services : "+discoveryClient.getServices();
    }

}
