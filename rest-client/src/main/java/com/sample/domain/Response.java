package com.sample.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Response<T> {

  private String status;
  private String message;
  private T data;

}
