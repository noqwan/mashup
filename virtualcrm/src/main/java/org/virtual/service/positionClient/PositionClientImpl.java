package org.virtual.service.positionClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.virtual.dto.PositionDTO;

public class PositionClientImpl implements PositionClient {

  private final static String ENDPOINT_ADDRESS_PARAMETER = "https://nominatim.openstreetmap.org/search?";

  private final Map<String, PositionDTO> cache = new HashMap<>();


  /**
   * Turn informations (street, postalCode, city and country) into a position (lat, lon)
   *
   * @param street     street of the residence
   * @param postalCode postalCode of the residence
   * @param city       city city of the residence
   * @param country    country of the residence
   * @return PositionDTO(lat, lon)
   * @throws NullPointerException if position not found
   */
  public PositionDTO getPositionDTO(String street, String postalCode, String city, String country) {

    String key = (street == null ? "" : street) + "|" +
        (postalCode == null ? "" : postalCode) + "|" +
        (city == null ? "" : city) + "|" +
        (country == null ? "" : country);

    // Check if pos in cache
    if (cache.containsKey(key)) {
      return cache.get(key);
    }

    boolean firstArgument = true;

    try {
      StringBuilder parameterizedAddress = new StringBuilder(ENDPOINT_ADDRESS_PARAMETER);
      if (street != null && !street.isEmpty()) {
        parameterizedAddress.append("street=")
            .append(URLEncoder.encode(street, StandardCharsets.UTF_8));
        firstArgument = false;
      }
      if (city != null && !city.isEmpty()) {
        if (!firstArgument) {
          parameterizedAddress.append("&");
        }
        parameterizedAddress.append("city=")
            .append(URLEncoder.encode(city, StandardCharsets.UTF_8));
        firstArgument = false;
      }
      if (country != null && !country.isEmpty()) {
        if (!firstArgument) {
          parameterizedAddress.append("&");
        }
        parameterizedAddress.append("country=")
            .append(URLEncoder.encode(country, StandardCharsets.UTF_8));
        firstArgument = false;
      }
      if (postalCode != null && !postalCode.isEmpty()) {
        if (!firstArgument) {
          parameterizedAddress.append("&");
        }
        parameterizedAddress.append("postalcode=")
            .append(URLEncoder.encode(postalCode, StandardCharsets.UTF_8));
      }

      parameterizedAddress.append("&format=json&limit=1");

      ClassicHttpResponse response = (ClassicHttpResponse) Request.get(
              String.valueOf(parameterizedAddress)).
          execute().returnResponse();

      ObjectMapper mapper = new ObjectMapper();

      JsonNode node = mapper.readTree(response.getEntity().getContent());
      JsonNode firstNode = node.get(0);

      PositionDTO result = mapper.treeToValue(firstNode, PositionDTO.class);

      // Save positionDTO in cache
      cache.put(key, result);

      return result;

    } catch (NullPointerException npe) {
      throw new NullPointerException(npe.getMessage());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
