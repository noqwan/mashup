package fr.univangers.movies.service.PositionClient;

import fr.univangers.movies.model.PositionDTO;

public interface PositionClient {

    PositionDTO getPositionDTO(String street, String postalCode, String city, String country);
}
