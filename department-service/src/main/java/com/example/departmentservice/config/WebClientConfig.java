package com.example.departmentservice.config;

import com.example.departmentservice.client.EmployeeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class WebClientConfig {

    @Autowired
    private LoadBalancedExchangeFilterFunction filterFunction;


    @Bean
    public WebClient employeeWebClient() {
        return WebClient.builder()
                .baseUrl("http://employee-service")
                .filter(filterFunction)
                .build();
    }

    @Bean
    public EmployeeClient employeeClient(WebClient employeeWebClient) {
        HttpServiceProxyFactory httpServiceProxyFactory
                = HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(employeeWebClient()))
                .build();
        return httpServiceProxyFactory.createClient(EmployeeClient.class);
    }




    // in case of RestTemplate:
//    @Bean
//    @LoadBalanced
//    public RestTemplate restTemplateBean() {
//        return new RestTemplate();
//    }



    // for another call to another service use @Qualifier
    // in case of two beans
//    @Bean
//    @Qualifier("employeeWebClient")
//    public WebClient employeeWebClient() {
//        return WebClient.builder()
//                .baseUrl("http://employee-service")
//                .filter(filterFunction) // Assuming you have a filterFunction bean
//                .build();
//    }
//
//    @Bean
//    @Qualifier("customerWebClient")
//    public WebClient customerWebClient() {
//        return WebClient.builder()
//                .baseUrl("http://customer-service")
//                .build();
//    }
//

//    @Bean
//    public EmployeeClient employeeClient(@Qualifier("employeeWebClient") WebClient employeeWebClient) {
//        HttpServiceProxyFactory httpServiceProxyFactory =
//                HttpServiceProxyFactory
//                        .builderFor(WebClientAdapter.create(employeeWebClient))
//                        .build();
//        return httpServiceProxyFactory.createClient(EmployeeClient.class);
//    }
//
//    @Bean
//    public EmployeeClient anotherEmployeeClient(@Qualifier("customerWebClient") WebClient customerWebClient) {
//        HttpServiceProxyFactory httpServiceProxyFactory =
//                HttpServiceProxyFactory
//                        .builderFor(WebClientAdapter.create(customerWebClient))
//                        .build();
//        return httpServiceProxyFactory.createClient(EmployeeClient.class);
//    }


    // final note, if you want to use the beans as fields
//    @Autowired
//    @Qualifier("employeeWebClient")
//    private WebClient employeeWebClient;
//
//    @Autowired
//    @Qualifier("customerWebClient")
//    private WebClient customerWebClient;

}
