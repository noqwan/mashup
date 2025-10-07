package fr.univangers.movies.service.PositionClient;

import fr.univangers.movies.model.Position;

public interface PositionClient {

    public Position getPosition(String street, String postalCode, String city, String country);
}
