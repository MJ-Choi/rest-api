package com.sample.svr.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Product {

  private String goodsName;
  private boolean active;
  private int count;
  private int price;

}
