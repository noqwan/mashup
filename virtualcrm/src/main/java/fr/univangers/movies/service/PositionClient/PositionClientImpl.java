package fr.univangers.movies.service.PositionClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univangers.movies.model.Position;

import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.core5.http.ClassicHttpResponse;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class PositionClientImpl implements PositionClient {

    private final static String ENDPOINT_ADDRESS_PARAMETER = "https://nominatim.openstreetmap.org/ui/search.html?";

    public Position getPosition(String street, String postalCode, String city, String country) {
        try {
            StringBuilder parameterizedAddress = new StringBuilder(ENDPOINT_ADDRESS_PARAMETER);
            if(street != null && !street.isEmpty()) {
                parameterizedAddress.append("&street=").append(URLEncoder.encode(street, StandardCharsets.UTF_8));
            }
            if(city != null && !city.isEmpty()) {
                parameterizedAddress.append("&city=").append(URLEncoder.encode(city, StandardCharsets.UTF_8));
            }
            if(country != null && !country.isEmpty()) {
                parameterizedAddress.append("&country=").append(URLEncoder.encode(country, StandardCharsets.UTF_8));
            }
            if(postalCode != null && !postalCode.isEmpty()) {
                parameterizedAddress.append("&postalcode=").append(URLEncoder.encode(postalCode, StandardCharsets.UTF_8));
            }

            ClassicHttpResponse response = (ClassicHttpResponse) Request.get(String.valueOf(parameterizedAddress)).
                    execute().returnResponse();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(response.getEntity().getContent());

            JsonNode firstNode = node.get(0);

            double lat = Double.parseDouble(firstNode.get("lat").asText());
            double lon = Double.parseDouble(firstNode.get("lon").asText());

            return new Position(lat, lon);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getEndpointAddress() {
        return ENDPOINT_ADDRESS_PARAMETER;
    }

}
