package org.client.service;

import org.client.service.rest.RestClientVirtualCRM;

public class FactoryServiceClientVirtualCRM {

  private static final String BASE_URL = "http://localhost:8080/virtualcrm";

  public static ServiceClientVirtualCRM createService() {
    RestClientVirtualCRM restClient = new RestClientVirtualCRM(BASE_URL);
    return new ServiceClientVirtualCRM(restClient);
  }
}
