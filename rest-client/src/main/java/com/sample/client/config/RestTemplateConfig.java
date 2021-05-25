package com.sample.client.config;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

  @Bean
  public RestTemplate restClientTemplate() {
    HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
    factory.setReadTimeout(3000);                                 // Read T/O = 3s
    factory.setConnectTimeout(3000);                              // Connect T/O = 3s
    HttpClient client = HttpClientBuilder.create()
                                         .setMaxConnTotal(10)     // Connection Pool. 최대 오픈 커넥션 수
                                         .setMaxConnPerRoute(10)  // IP:Port 1쌍에 대한 최대 연결 수
                                         .build();
    factory.setHttpClient(client);
    return new RestTemplate(factory);
  }

}
