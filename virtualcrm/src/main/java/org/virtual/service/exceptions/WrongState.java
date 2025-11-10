package org.virtual.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class WrongState extends Exception {

  public WrongState(Exception e) {
    super(e);
  }
}
