package org.virtual.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NoSuchLeadException extends Exception {

  public NoSuchLeadException(Exception e) {
    super(e);
  }
}
