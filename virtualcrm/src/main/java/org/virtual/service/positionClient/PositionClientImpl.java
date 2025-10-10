package org.virtual.service.positionClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.virtual.dto.PositionDTO;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.core5.http.ClassicHttpResponse;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class PositionClientImpl implements PositionClient {

    private final static String ENDPOINT_ADDRESS_PARAMETER = "https://nominatim.openstreetmap.org/search?";

    /**
     * Turn informations (street, postalCode, city and country) into a position (lat, lon)
     * @param street street of the residence
     * @param postalCode postalCode of the residence
     * @param city city city of the residence
     * @param country country of the residence
     * @return PositionDTO(lat, lon)
     * @throws NullPointerException if position not found
     */
    public PositionDTO getPositionDTO(String street, String postalCode, String city, String country) {
        boolean firstArgument = true;
        try {
            StringBuilder parameterizedAddress = new StringBuilder(ENDPOINT_ADDRESS_PARAMETER);
            if (street != null && !street.isEmpty()) {
                parameterizedAddress.append("street=").append(URLEncoder.encode(street, StandardCharsets.UTF_8));
                firstArgument = false;
            }
            if (city != null && !city.isEmpty()) {
                if (!firstArgument) {
                    parameterizedAddress.append("&");
                }
                parameterizedAddress.append("city=").append(URLEncoder.encode(city, StandardCharsets.UTF_8));
                firstArgument = false;
            }
            if (country != null && !country.isEmpty()) {
                if (!firstArgument) {
                    parameterizedAddress.append("&");
                }
                parameterizedAddress.append("country=").append(URLEncoder.encode(country, StandardCharsets.UTF_8));
                firstArgument = false;
            }
            if (postalCode != null && !postalCode.isEmpty()) {
                if (!firstArgument) {
                    parameterizedAddress.append("&");
                }
                parameterizedAddress.append("postalcode=").append(URLEncoder.encode(postalCode, StandardCharsets.UTF_8));
            }

            parameterizedAddress.append("&format=json&limit=1");

            //System.out.println("parameterizedAddress: " + parameterizedAddress);

            ClassicHttpResponse response = (ClassicHttpResponse) Request.get(String.valueOf(parameterizedAddress)).
                    execute().returnResponse();

            ObjectMapper mapper = new ObjectMapper();

            JsonNode node = mapper.readTree(response.getEntity().getContent());
            JsonNode firstNode = node.get(0);

            //System.out.println(response);

            return mapper.treeToValue(firstNode, PositionDTO.class);
        //If node.get(0) (line 68) is null, it means there is no position found : throws nullPointerException
        }catch (NullPointerException npe){
            throw new NullPointerException(npe.getMessage());
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
