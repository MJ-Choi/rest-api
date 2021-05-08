package com.sample.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class Product {

  private String goodsName;
  private boolean active;
  private int count;
  private int price;
}
