package org.virtual.service.crm;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import org.virtual.dto.SaleForceLeadDTO;
import org.virtual.dto.VirtualLeadDTO;
import org.virtual.dto.converter.SaleForceDtoConverter;
import java.io.FileInputStream;
import org.virtual.service.exceptions.SalesForcePropertiesException;
import org.virtual.service.exceptions.SalesForceTokenObtentionException;

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
    HttpResponse<String> response;

    try{
      HttpClient client = HttpClient.newHttpClient();
      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(uri+"/services/data/v45.0/query/?q="+query))
          .headers("Authorization","Bearer "+authorizationToken)
          .build();

          response = client.send(request,
          HttpResponse.BodyHandlers.ofString());




    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
    try {
      JsonNode root = mapper.readTree(response.body());
      if (!root.has("records") || root.get("records").isEmpty()) {
        return result;
      }

      for(JsonNode leadNode : root.get("records")){
        result.add(nodeTOLead(leadNode));
      }

    }
    catch(Exception e){
      throw  new RuntimeException(e);
    }

    return result;
  }

  @Override
  protected List<SaleForceLeadDTO> findLeadsByDateSpecific(Calendar startDate, Calendar endDate) {
    List<SaleForceLeadDTO> result = new ArrayList<>();

    postForAuthorizationToken();

    String query =
        "SELECT+Id%2C+FirstName%2C+LastName%2C+AnnualRevenue%2C+Phone%2C+CreatedDate%2C+Company%2C+State%2C+Street%2C+PostalCode%2C+City%2C+Country%2C+Address+FROM+Lead+WHERE+CreatedDate+%3E%3D+"
            + startDate + "+AND+CreatedDate+%3C%3D+" + endDate;

    var mapper = new ObjectMapper();

    HttpResponse<String> response;
    try {
      HttpClient client = HttpClient.newHttpClient();
      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(uri + "/services/data/v45.0/query/?q=" + query))
          .headers("Authorization", "Bearer " + authorizationToken)
          .build();

      response = client.send(request,
          HttpResponse.BodyHandlers.ofString());

    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
    try {
      JsonNode root = mapper.readTree(response.body());
      if (!root.has("records") || root.get("records").isEmpty()) {
        return result;
      }

      for(JsonNode leadNode : root.get("records")){
          result.add(nodeTOLead(leadNode));
      }

    }
    catch(Exception e){
      throw  new RuntimeException(e);
    }

    return result;
  }

  @Override
  protected void addSpecific(SaleForceLeadDTO lead) {
    //UNSUPPORTED
  }

  @Override
  protected void deleteSpecific(SaleForceLeadDTO lead) {

    postForAuthorizationToken();




    try{
      HttpClient client = HttpClient.newHttpClient();


      String leadId = lead.getId();


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
      JsonNode root = mapper.readTree(response.body());
      if (!root.has("access_token") || root.get("access_token").asText().isEmpty()) {
        throw new SalesForceTokenObtentionException("Error while retrieving authorization token");
      }
      authorizationToken=root.get("access_token").asText();
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  private SaleForceLeadDTO nodeTOLead(JsonNode node) {

    String id = node.hasNonNull("Id") ? node.get("Id").asText() : "";
    String firstName = node.hasNonNull("FirstName") ? node.get("FirstName").asText() : "";
    String lastName = node.hasNonNull("LastName") ? node.get("LastName").asText() : "";
    String phone = node.hasNonNull("Phone") ? node.get("Phone").asText() : "";
    String company = node.hasNonNull("Company") ? node.get("Company").asText() : "";
    String state = node.hasNonNull("State") ? node.get("State").asText() : "";
    String street = node.hasNonNull("Street") ? node.get("Street").asText() : "";
    String postalCode = node.hasNonNull("PostalCode") ? node.get("PostalCode").asText() : "";
    String city = node.hasNonNull("City") ? node.get("City").asText() : "";
    String country = node.hasNonNull("Country") ? node.get("Country").asText() : "";


    double annualRevenue = 0.0;
    if (node.hasNonNull("AnnualRevenue")) {
      try {
        annualRevenue = Double.parseDouble(node.get("AnnualRevenue").asText());
      } catch (NumberFormatException e) {
        annualRevenue = 0.0; // default if parsing fails
      }
    }


    Date creationDate = null;
    if (node.hasNonNull("CreatedDate")) {
      try {
        // Salesforce returns ISO 8601 date strings, e.g. "2025-11-20T14:33:00.000+0000"
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        creationDate = sdf.parse(node.get("CreatedDate").asText());
      } catch (ParseException e) {
        throw new RuntimeException(e);
      }
    }

    return new SaleForceLeadDTO(id, firstName, lastName, annualRevenue, phone,
        creationDate, company, state, street, postalCode, city, country);
  }
}
