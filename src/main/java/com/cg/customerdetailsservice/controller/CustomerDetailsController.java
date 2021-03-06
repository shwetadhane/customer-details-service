package com.cg.customerdetailsservice.controller;

import com.cg.customerdetailsservice.model.BankAccount;
import com.cg.customerdetailsservice.model.Customer;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

@RestController
public class CustomerDetailsController {

    @Autowired
    WebClient.Builder webClientBuilder;

    @Autowired
    DiscoveryClient discoveryClient;

    @RequestMapping("/details")
    public Customer getCustomerDetails() throws URISyntaxException, MalformedURLException {


        String targetHost =  discoveryClient.getInstances("banking-details-service").stream()
                .map(si->si.getServiceId()).findFirst().get();

        Integer targetPort =  discoveryClient.getInstances("banking-details-service").stream()
                .map(si->si.getPort()).findFirst().get();


        URIBuilder builder = new URIBuilder();
        builder.setScheme("http");
        builder.setHost(targetHost);
        builder.setPort(targetPort);
        builder.setPath("/bankdetails");
        //builder.addParameter("abc", "xyz");
        String url = builder.build().toURL().toString();
        System.out.println("Raw url = " + url);

        BankAccount bankAccount = webClientBuilder.build()
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(BankAccount.class)
                .block();

        Customer customer = new Customer("Shweta", "UK", "9922911831",bankAccount.getAccountNo());

        return  customer;
   }

}
