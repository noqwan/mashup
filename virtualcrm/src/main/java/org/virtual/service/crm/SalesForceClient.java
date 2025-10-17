package org.virtual.service.crm;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import org.virtual.dto.SaleForceLeadDTO;
import org.virtual.dto.VirtualLeadDTO;
import org.virtual.dto.converter.SaleForceDtoConverter;
import java.util.List;

public class SalesForceClient extends CRMClient<SaleForceLeadDTO> {

  private String authorizationToken;
  private String uri;
  private Properties salesForceProperties;
  public SalesForceClient() {
    this.converter = new SaleForceDtoConverter();
    authorizationToken="";


    String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    String salesForcePropertiesPath = rootPath + "conf/salesforce.properties";
  try {
    salesForceProperties = new Properties();
    salesForceProperties.load(new FileInputStream(salesForcePropertiesPath));

    uri="https://"+salesForceProperties.getProperty("salesforce_uri");
  }
  catch(IOException e){
    e.printStackTrace();
  }
  }

  @Override
  protected List<SaleForceLeadDTO> findLeadsSpecific() {
    List<SaleForceLeadDTO> result = new ArrayList<>();



    return result;
  }

  @Override
  protected List<SaleForceLeadDTO> findLeadsByDateSpecific() {
    return List.of();
  }

  @Override
  protected void addSpecific(SaleForceLeadDTO lead) {
    return;
  }

  @Override
  protected void deleteSpecific(SaleForceLeadDTO lead) {
    return;
  }

  @Override
  protected VirtualLeadDTO convertToVirtual(SaleForceLeadDTO specificLead) {
    return this.converter.convertToVirtual(specificLead);
  }

  @Override
  protected SaleForceLeadDTO convertFromVirtual(VirtualLeadDTO virtualLead) {
    return this.converter.convertFromVirtual(virtualLead);
  }

  private void postForAuthorizationToken(){
    var values = new HashMap<String, String>() {{
      put("grant_type", "password");
      put("client_id", salesForceProperties.getProperty("client_id"));
      put("client_secret",salesForceProperties.getProperty("client_secret"));
      put("username",salesForceProperties.getProperty("username"));
      put("password", salesForceProperties.getProperty("password"));
    }};

    var objectMapper = new ObjectMapper();


    try{
      HttpClient client = HttpClient.newHttpClient();
      String requestBody = objectMapper.writeValueAsString(values);
      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(uri))
          .POST(HttpRequest.BodyPublishers.ofString(requestBody))
          .build();

      HttpResponse<String> response = client.send(request,
          HttpResponse.BodyHandlers.ofString());

      System.out.println(response.body());
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
