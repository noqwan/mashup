package org.virtual.service.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

  // logger optionnel si tu veux juste savoir qu'une exception est captée
  private static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

  @ExceptionHandler(NoSuchLeadException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public Map<String, Object> handleNoSuchLead(NoSuchLeadException ex) {
    // log silencieux: seulement le message, pas de stack trace
    logger.debug("Handled NoSuchLeadException: {}", ex.getMessage());
    Map<String, Object> body = new HashMap<>();
    body.put("error", "No such lead");
    body.put("message", ex.getMessage());
    body.put("timestamp", LocalDateTime.now().toString());
    return body;
  }

  @ExceptionHandler(WrongOrderForRevenue.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Map<String, Object> handleWrongOrderRevenue(WrongOrderForRevenue ex) {
    logger.debug("Handled WrongOrderForRevenue: {}", ex.getMessage());
    Map<String, Object> body = new HashMap<>();
    body.put("error", "Wrong order for revenue");
    body.put("message", ex.getMessage());
    body.put("timestamp", LocalDateTime.now().toString());
    return body;
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public Map<String, Object> handleAll(Exception ex) {
    // log silencieux
    logger.debug("Handled generic exception: {}", ex.getMessage());
    Map<String, Object> body = new HashMap<>();
    body.put("error", "Internal server error");
    body.put("message", ex.getMessage());
    body.put("timestamp", LocalDateTime.now().toString());
    return body;
  }
}
