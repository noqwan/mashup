package org.virtual.service.crm;

import java.util.Calendar;
import java.util.List;
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
  protected List<SaleForceLeadDTO> findLeadsSpecific(double lowAnnualRevenue, double highAnnualRevenue,String state) {
    List<SaleForceLeadDTO> result = new ArrayList<>();

    postForAuthorizationToken();

    String query="SELECT+Id,+FirstName,+LastName,+AnnualRevenue,+Phone,+CreatedDate,+Company,+State,+Street,+PostalCode,+City,+Country,+Address+FROM+Lead";


    var objectMapper = new ObjectMapper();


    try{
      HttpClient client = HttpClient.newHttpClient();
      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(uri+"/services/data/v45.0/query/?q="+query))
          .headers("Authorization","Bearer "+authorizationToken)
          .build();

      HttpResponse<String> response = client.send(request,
          HttpResponse.BodyHandlers.ofString());

      System.out.println(response.body());
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }



    return result;
  }

  @Override
  protected List<SaleForceLeadDTO> findLeadsByDateSpecific(Calendar startDate, Calendar endDate) {
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
      ObjectMapper mapper = new ObjectMapper();
      HashMap<String,String> responseHaspMap= mapper.readValue(response.body(),HashMap.class);
      authorizationToken=responseHaspMap.get("access_token");
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
