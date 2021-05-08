package com.sample.service;

import com.sample.domain.Product;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductService {

  Map<String, Product> productMap = new ConcurrentHashMap<>();

  public boolean registerProduct(Product product) {
    log.debug("registerProduct() :: Product = {}", product);
    if (productMap.size() > 0 && productMap.containsKey(product.getGoodsName())) {
      return false;
    }
    productMap.put(product.getGoodsName(), product);
    return true;
  }

  public List<Product> getProductList() {
    log.debug("getProductList()");
    List<Product> allProductList = new ArrayList<>();
    productMap.forEach((k, v) -> allProductList.add(v));
    return allProductList;
  }

  public Product isExist(String goodsName) {
    log.debug("isExist() :: goodsName = {}", goodsName);
    if (productMap.size() < 1) {
      log.error("There is no Goods in Product Map");
      return null;
    }
    return productMap.get(goodsName);
  }

  public boolean removeProduct(String goodsName) {
    log.debug("removeProduct() :: goodsName = {}", goodsName);
    try {
      if (productMap.size() < 1 || !productMap.containsKey(goodsName)) {
        log.error("There is No [ {} ] in ProductMap. Check your goods name", goodsName);
        throw new IOException("There is No Goods. Check your goods name");
      }
      productMap.remove(goodsName);
      return true;
    } catch (IOException e) {
      log.error("removeProduct :: IOException :: {}", e.getMessage());
    }
    return false;
  }
}
