package com.sample.controller;

import static com.sample.domain.Response.STATUS.FAILED;
import static com.sample.domain.Response.STATUS.SUCCESS;
import static com.sample.domain.Response.STATUS_MSG.EXIST;

import com.sample.domain.Product;
import com.sample.domain.Response;
import com.sample.domain.Response.STATUS;
import com.sample.domain.Response.STATUS_MSG;
import com.sample.service.ProductService;
import java.util.Optional;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/product")
public class ProductController {

  private ProductService service;

  public ProductController(ProductService service) {
    this.service = service;
  }

  /**
   * @Usage curl -H "Content-Type: application/json" -X GET localhost:8765/product
   * @return [{"goodsName":"test1","active":true,"count":10,"price":1000}] 
   */
  @RequestMapping(method = RequestMethod.GET, path = "",
    consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public Response<Object> getProductList() {
    log.debug("GET /product");
//    return Optional.ofNullable(service.getProductList()).orElse(null);
    return new Response(Optional.ofNullable(service.getProductList()));
  }

  /**
   * @param product
   * @Usage curl -d '{"goodsName":"test1", "active":true, "count":10, "price": 1000}' \
   *             -H "Content-Type: application/json" \
   *             -X POST localhost:8765/product/register
   * @return SUCCESS
   */
  @RequestMapping(method = RequestMethod.POST, path = "/register",
  consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public Response<Object> register(@RequestBody @NonNull Product product) {
    log.debug("POST /product/register :: Product = {}", product);
    boolean svcResult = service.registerProduct(product);
    Response response = new Response(STATUS.getStatus(svcResult));
    response.setMessage(svcResult ? null : EXIST.message());
    return response;
  }

  @RequestMapping(method = RequestMethod.GET, path = "/{goodsName}",
    consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public Response<Object> checkProduct(@PathVariable String goodsName) {
    log.debug("GET /product/{}", goodsName);
    return new Response(service.isExist(goodsName));
  }

  @RequestMapping(method = RequestMethod.POST, path = "/remove/{goodsName}",
    consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public Response<Object> removeProduct(@PathVariable String goodsName) {
    log.debug("POST /product/remove/{}", goodsName);
    return new Response<>(STATUS.getStatus(service.removeProduct(goodsName)));
  }
}
