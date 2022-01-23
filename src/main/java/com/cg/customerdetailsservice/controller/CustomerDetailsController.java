package com.cg.customerdetailsservice.controller;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableDiscoveryClient
public class CustomerDetailsController {

    @RequestMapping("/details")
    public String getCustomerDetails(){
        return "Shweta";
    }

}
