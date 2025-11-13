package org.virtual.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class WrongOrderForDate extends Exception {

  public WrongOrderForDate(Exception e) {
    super(e);
  }
}
