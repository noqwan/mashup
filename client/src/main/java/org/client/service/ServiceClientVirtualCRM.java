package org.client.service;

import java.io.IOException;
import org.client.service.dto.VirtualLeadDTO;
import org.client.service.rest.RestClientVirtualCRM;

public class ServiceClientVirtualCRM {

  private final RestClientVirtualCRM restClient;

  public ServiceClientVirtualCRM(RestClientVirtualCRM restClient) {
    this.restClient = restClient;
  }

  public VirtualLeadDTO[] findLeads(double lowRevenue, double highRevenue, String state)
      throws IOException {
    return restClient.findLeads(lowRevenue, highRevenue, state);
  }

  public VirtualLeadDTO[] findLeadsByDate(String startDate, String endDate) throws IOException {
    return restClient.findLeadsByDate(startDate, endDate);
  }

  public void addLead(VirtualLeadDTO lead) throws IOException {
    restClient.addLead(lead);
  }

  public void deleteLead(VirtualLeadDTO lead) throws IOException {
    restClient.deleteLead(lead);
  }
}
