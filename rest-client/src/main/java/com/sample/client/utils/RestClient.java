package com.sample.client.utils;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * todo list
 * * authentication using spring-security
 * * json type check
 */
@Slf4j
@Component
public class RestClient {

  private final RestTemplate template;

  @Value("${rest.svr}")
  private String restServerAddress; // REST_SERVER_IP:REST_SERVER_PORT

  @Autowired
  public RestClient(RestTemplate template) {
    this.template = template;
  }

//  public RestClient(RestTemplate template, String restServer) {
//    this.template = template;
//    this.restServer = restServer;
//  }

  public String get(String uri) {
    log.debug("GET :: BASE URL = {}", cnvtUrl(uri));
    return template.exchange(cnvtUrl(uri), GET, cnvtReq(), String.class).getBody();
  }

  public String get(String uri, Object request) {
    log.debug("GET :: BASE URL = {}, REQUEST = {}", cnvtUrl(uri), request);
    return template.exchange(cnvtUrl(uri), GET, cnvtReq(request), String.class).getBody();
  }

  public String post(String uri, String pathParam) {
    log.debug("POST :: BASE URL = {}, PATH PARAMETER = {}", cnvtUrl(uri), pathParam);
    return template.exchange(cnvtUrl(uri, pathParam), POST, cnvtReq(), String.class).getBody();
  }

  public String post(String uri, Object requestBody) {
    log.debug("POST :: BASE URL = {}, REQUEST BODY = {}", cnvtUrl(uri), requestBody);
    return template.exchange(cnvtUrl(uri), POST, cnvtReq(requestBody), String.class).getBody();
  }

  private HttpEntity<?> cnvtReq() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    return new HttpEntity<>(headers);
  }

  private HttpEntity<?> cnvtReq(Object requset) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    return new HttpEntity<>(requset, headers);
  }

  private String cnvtUrl(String uri) {
    log.debug("restServer = {}, uri = {}", restServerAddress, uri);
    return removeLastChar(restServerAddress) + "/" + removeFirstChar(uri);
  }

  private String cnvtUrl(String uri, String path) {
    log.debug("restServer = {}, uri = {}, path = {}", restServerAddress, uri, path);
    return removeLastChar(cnvtUrl(uri)) + "/" + removeFirstChar(path);
  }

  private String removeLastChar(String str) {
    if (Character.toString(str.charAt(str.length() - 1)).equals("/")) {
      str = str.substring(0, str.length() - 1);
    }
    return str;
  }

  private String removeFirstChar(String str) {
    if (Character.toString(str.charAt(0)).equals("/")) {
      str = str.substring(1, str.length());
    }
    return str;
  }
}
