package com.sample.client.service;

import com.sample.client.config.RestTemplateConfig;
import com.sample.client.domain.Product;
import com.sample.client.utils.RestClient;
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
public class ProductClientServiceTest {

  private ProductClientService service;

  @Before
  public void setUp() {
    RestTemplateConfig template = new RestTemplateConfig();
    RestClient restClient = new RestClient(template.restClientTemplate());
    ReflectionTestUtils.setField(restClient, "restserverAddress", "http://localhost:8765");
    this.service = new ProductClientService(restClient);
  }

  @Test
  public void getAllProducts() {
    // given
    Product product = Product.builder().goodsName("sample").active(false).build();
    service.registerProduct(product);

    // when
    List<Product> result = service.getAllProducts();
    System.out.println("result = " + result);

    //then
    Assertions.assertTrue(result.size() > 0);
  }

  @Test
  public void registerProduct() {
    // given
    service.removeProduct("sample-test");

    // when
    Product product = Product.builder().goodsName("sample-test").active(false).build();
    boolean result = service.registerProduct(product);
    System.out.println("result = " + result);

    // then
    Assertions.assertTrue(result);
  }

  @Test
  public void removeProduct() {
    // given
    Product product = Product.builder().goodsName("sample-test").active(false).build();

    // when
    boolean result = service.removeProduct("sample-test");
    System.out.println("result = " + result);

    // then
    Assertions.assertTrue(result);
  }
}