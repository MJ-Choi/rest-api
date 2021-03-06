package com.sample.client.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.client.config.RestTemplateConfig;
import com.sample.client.domain.Product;
import com.sample.client.domain.Response;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * rest-server 가 실행중인 상태에서 테스트 코드를 실행해야 함
 */
@RunWith(MockitoJUnitRunner.class)
public class RestClientTest {

  private final String svrAddr = "http://localhost:8765";

  private RestTemplateConfig template = new RestTemplateConfig();

  private RestClient restClient = new RestClient(template.restClientTemplate());

  private ObjectMapper mapper = new ObjectMapper();

  @Before
  public void setup() {
    ReflectionTestUtils.setField(restClient, "restserverAddress", svrAddr);
  }

  @Test
  public void post() {
    String uri = "/product/register";
    Product sample = Product.builder()
      .goodsName("sample02")
      .active(true)
      .price(1000)
      .count(10).build();
    String result = restClient.post(uri, sample);
    System.out.println("result = " + result);
    Assertions.assertNotNull(result);
  }

  @Test
  public void get() throws JsonProcessingException {
    String uri = "product";
    String result = restClient.get(uri);
    System.out.println("result = " + result);
    Assertions.assertNotNull(result);

    Response<List<Product>> response = mapper.readValue(result, Response.class);
    System.out.println("response.getData() = " + response.getData());
    int productCnt = response.getData().size();
    System.out.println("productCnt = " + productCnt);
  }
}