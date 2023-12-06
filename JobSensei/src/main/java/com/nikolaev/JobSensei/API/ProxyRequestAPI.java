package com.nikolaev.JobSensei.API;

import java.net.InetSocketAddress;
import java.net.Proxy;

import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProxyRequestAPI {
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    private RestTemplate createRestTemplate(String host, int port) {

        final String proxyHost = host;
        final Integer proxyPort = port;

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();

        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
        factory.setProxy(proxy);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(factory);
        return restTemplate;
    }

    public RestTemplate change() {
        return createRestTemplate("138.36.95.99", 50100);
    }
}
