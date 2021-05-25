package com.sample.client.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.client.domain.Product;
import com.sample.client.domain.Response;
import com.sample.client.utils.RestClient;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductClientService {

  @Value("${rest.svr}")
  private String restServer;
  private RestClient restClient;

  private ObjectMapper mapper = new ObjectMapper();

  private final String PRODUCT_LIST = "/product";
  private final String PRODUCT_REGISTER = "/product/register";
  private final String PRODUCT_REMOVE = "/product/remove";

  public ProductClientService(RestClient restClient) {
    this.restClient = restClient;
  }

  public List<Product> getAllProducts() {
    log.debug("getAllProducts() :: GET {}{}", restServer, PRODUCT_LIST);
    List<Product> productList = new ArrayList<>();
    String result = restClient.get(PRODUCT_LIST);

    try {
      Response<List<Product>> response = mapper.readValue(result, Response.class);
      productList = response.getData();
    } catch (JsonProcessingException e) {
      log.error("getAllProducts() :: JsonProcessingException : {}", e.getMessage());
    }
    return productList;
  }

  public boolean registerProduct(Product product) {
    String result = restClient.post(PRODUCT_REGISTER, product);
    return setResult(result);
  }

  public boolean removeProduct(String goodsName) {
    String result = restClient.post(PRODUCT_REMOVE, goodsName);
    return setResult(result);
  }

  private boolean setResult(String result) {
    try {
      Response<Object> response = mapper.readValue(result, Response.class);
      return response.getStatus().equals("SUCCESS");
    } catch (JsonProcessingException e) {
      log.error("setResult() :: JsonProcessingException : {}", e.getMessage());
      return false;
    }
  }
}
