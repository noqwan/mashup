package org.virtual.service.positionClient;

import org.virtual.dto.PositionDTO;

public interface PositionClient {

  PositionDTO getPositionDTO(String street, String postalCode, String city, String country);
}
