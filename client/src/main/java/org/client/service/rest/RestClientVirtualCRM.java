package org.client.service.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.client.service.dto.VirtualLeadDTO;

public class RestClientVirtualCRM {

  private final String baseUrl;
  private final ObjectMapper mapper = new ObjectMapper();
  ;

  public RestClientVirtualCRM(String baseUrl) {
    this.baseUrl = baseUrl;
  }

  public VirtualLeadDTO[] findLeads(double lowRevenue, double highRevenue, String state)
      throws IOException {
    String query = String.format("/findLeads?lowAnnualRevenue=%s&highAnnualRevenue=%s&state=%s",
        lowRevenue, highRevenue, state);
    return sendGetRequest(query);
  }

  public VirtualLeadDTO[] findLeadsByDate(String startDate, String endDate) throws IOException {
    String query = String.format("/findLeadsByDate?startDate=%s&endDate=%s", startDate, endDate);
    return sendGetRequest(query);
  }

  public void addLead(VirtualLeadDTO lead) throws IOException {
    sendRequest("/add", "POST", lead);
  }

  public void deleteLead(VirtualLeadDTO lead) throws IOException {
    sendRequest("/delete", "POST", lead);
  }

  private VirtualLeadDTO[] sendGetRequest(String endpoint) throws IOException {
    URL url = new URL(baseUrl + endpoint);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("GET");
    conn.setRequestProperty("Accept", "application/json");

    int code = conn.getResponseCode();

    InputStreamReader in;
    if (code >= 200 && code < 300) {
      in = new InputStreamReader(conn.getInputStream());
      VirtualLeadDTO[] leads = mapper.readValue(in, VirtualLeadDTO[].class);
      in.close();
      conn.disconnect();
      return leads;
    } else {
      in = new InputStreamReader(conn.getErrorStream());
      Map<String, Object> errorResponse = mapper.readValue(in,
          new com.fasterxml.jackson.core.type.TypeReference<Map<String, Object>>() {
          });
      in.close();
      conn.disconnect();
      throw new IOException("Server returned an error: " + errorResponse);
    }
  }

  private void sendRequest(String endpoint, String method, VirtualLeadDTO lead) throws IOException {
    URL url = new URL(baseUrl + endpoint);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod(method);
    conn.setRequestProperty("Content-Type", "application/json");
    conn.setDoOutput(true);

    String json = mapper.writeValueAsString(lead);
    try (OutputStream os = conn.getOutputStream()) {
      os.write(json.getBytes(StandardCharsets.UTF_8));
    }

    int code = conn.getResponseCode();

    InputStreamReader in;
    if (code >= 200 && code < 300) {
      in = new InputStreamReader(conn.getInputStream());
      in.close();
    } else {
      // Lire l'erreur JSON renvoyée par Spring
      in = new InputStreamReader(conn.getErrorStream());
      Map<String, Object> errorResponse = mapper.readValue(in,
          new com.fasterxml.jackson.core.type.TypeReference<Map<String, Object>>() {
          });

      in.close();
      throw new IOException("Server returned an error: " + errorResponse);
    }

    conn.disconnect();
  }

}
