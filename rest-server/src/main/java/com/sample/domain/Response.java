package com.sample.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {

  private STATUS status;
  private String message;
  private T data;

  public Response(STATUS status) {
    this.status = status;
  }

  public Response(String message) {
    this.status = STATUS.FAILED;
    this.message = message;
  }

  public Response(T data) {
    this.status = STATUS.SUCCESS;
    this.data = data;
  }

  public enum STATUS {
    SUCCESS("1", true),
    FAILED("0", false);

    private String statusCode;
    private boolean statusValue;

    STATUS(String statusCode, boolean statusValue) {
      this.statusCode = statusCode;
      this.statusValue = statusValue;
    }

    public static STATUS getStatus(boolean statusValue) {
      return statusValue ? SUCCESS : FAILED;
    }

    public String statusCode() {
      return statusCode;
    }

    public boolean statusValue() {
      return statusValue;
    }
  }

  public enum STATUS_MSG {
    EXIST("It's already exist"),
    EMPTY("It's not valid value");

    private String detailMsg;

    STATUS_MSG(String detailMsg) {
      this.detailMsg = detailMsg;
    }

    public String message() {
      return detailMsg;
    }
  }
}
