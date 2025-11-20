package org.virtual.service.crm;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import org.virtual.dto.SaleForceLeadDTO;
import org.virtual.dto.VirtualLeadDTO;
import org.virtual.dto.converter.SaleForceDtoConverter;
import java.io.FileInputStream;
import org.virtual.service.exceptions.SalesForcePropertiesException;

public class SalesForceClient extends CRMClient<SaleForceLeadDTO> {

  private String authorizationToken;
  private final String uri;
  private final Properties salesForceProperties;
  public SalesForceClient() {
    this.converter = new SaleForceDtoConverter();
    authorizationToken="";


    //String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    String salesForcePropertiesPath = /*rootPath +*/ "conf/salesforce.properties";
  try {
    salesForceProperties = new Properties();
    salesForceProperties.load(new FileInputStream(salesForcePropertiesPath));

    uri="https://"+salesForceProperties.getProperty("salesforce_uri");
  }
  catch(IOException e){
    throw new SalesForcePropertiesException(e.getMessage());
  }
  }

  @Override
  protected List<SaleForceLeadDTO> findLeadsSpecific(double lowAnnualRevenue, double highAnnualRevenue,String state) {
    List<SaleForceLeadDTO> result = new ArrayList<>();

    postForAuthorizationToken();

    String query="SELECT+Id%2C+FirstName%2C+LastName%2C+AnnualRevenue%2C+Phone%2C+CreatedDate%2C+Company%2C+State%2C+Street%2C+PostalCode%2C+City%2C+Country%2C+Address+FROM+Lead+WHERE+AnnualRevenue+%3E%3D+"+lowAnnualRevenue+"+AND+AnnualRevenue+%3C%3D+"+highAnnualRevenue;


    var mapper = new ObjectMapper();


    try{
      HttpClient client = HttpClient.newHttpClient();
      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(uri+"/services/data/v45.0/query/?q="+query))
          .headers("Authorization","Bearer "+authorizationToken)
          .build();

      HttpResponse<String> response = client.send(request,
          HttpResponse.BodyHandlers.ofString());

      //System.out.println(response.body());

      HashMap<String, String> responseHashMap = mapper.readValue(
          response.body(),
          new TypeReference<HashMap<String, String>>() {}
      );
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }

    return result;
  }

  @Override
  protected List<SaleForceLeadDTO> findLeadsByDateSpecific(Calendar startDate, Calendar endDate) {
    List<SaleForceLeadDTO> result = new ArrayList<>();

    postForAuthorizationToken();

    String query="SELECT+Id%2C+FirstName%2C+LastName%2C+AnnualRevenue%2C+Phone%2C+CreatedDate%2C+Company%2C+State%2C+Street%2C+PostalCode%2C+City%2C+Country%2C+Address+FROM+Lead+WHERE+CreatedDate+%3E%3D+"+startDate+"+AND+CreatedDate+%3C%3D+"+endDate;


    var mapper = new ObjectMapper();


    try{
      HttpClient client = HttpClient.newHttpClient();
      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(uri+"/services/data/v45.0/query/?q="+query))
          .headers("Authorization","Bearer "+authorizationToken)
          .build();

      HttpResponse<String> response = client.send(request,
          HttpResponse.BodyHandlers.ofString());

      //System.out.println(response.body());

      HashMap<String, String> responseHashMap = mapper.readValue(
          response.body(),
          new TypeReference<HashMap<String, String>>() {}
      );
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }

    return result;
  }

  @Override
  protected void addSpecific(SaleForceLeadDTO lead) {
    return;
  }

  @Override
  protected void deleteSpecific(SaleForceLeadDTO lead) {
    List<SaleForceLeadDTO> result = new ArrayList<>();

    postForAuthorizationToken();

    String query="SELECT+Id+FROM+Lead+WHERE+FirstName+%3D+"+lead.getFirstName()+"+AND+LastName+%3D+"+lead.getLastName()+"+AND+Phone+%3D+"+lead.getPhone()+"+AND+CreatedDate+%3D+"+lead.getCreationDate();


    var mapper = new ObjectMapper();


    try{
      HttpClient client = HttpClient.newHttpClient();
      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(uri+"/services/data/v45.0/query/?q="+query))
          .headers("Authorization","Bearer "+authorizationToken)
          .build();

      HttpResponse<String> response = client.send(request,
          HttpResponse.BodyHandlers.ofString());

      //System.out.println(response.body());


      JsonNode root = mapper.readTree(response.body());

      if (!root.has("records") || root.get("records").isEmpty()) {
        throw new RuntimeException("Lead not found for " + lead.getFirstName() + " " + lead.getLastName());
      }

      String leadId = root.get("records").get(0).get("Id").asText();


      String deleteUrl = uri+"/services/data/v45.0/sobjects/Lead/" + leadId;

      HttpRequest deleteRequest = HttpRequest.newBuilder()
          .uri(URI.create(deleteUrl))
          .header("Authorization", "Bearer " + authorizationToken)
          .DELETE()
          .build();

      HttpResponse<String> deleteResponse = client.send(deleteRequest, HttpResponse.BodyHandlers.ofString());

      if (deleteResponse.statusCode() == 204) {
        System.out.println("Lead deleted successfully: " + leadId);
      } else {
        System.out.println("Failed to delete lead. Status: " + deleteResponse.statusCode());
        System.out.println(deleteResponse.body());
      }
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
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
     // String requestBody = objectMapper.writeValueAsString(values);

      String requestBody = values.entrySet()
          .stream()
          .map(e -> e.getKey() + "=" + URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8))
          .collect(Collectors.joining("&"));
      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create("https://login.salesforce.com/services/oauth2/token"))
          .headers("Content-Type","application/x-www-form-urlencoded")
          .POST(HttpRequest.BodyPublishers.ofString(requestBody))
          .build();


      HttpResponse<String> response = client.send(request,
          HttpResponse.BodyHandlers.ofString());


      ObjectMapper mapper = new ObjectMapper();
      HashMap<String, String> responseHashMap = mapper.readValue(
          response.body(),
          new TypeReference<HashMap<String, String>>() {}
      );
      authorizationToken=responseHashMap.get("access_token");
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
