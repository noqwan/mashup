package fr.univangers.movies.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PositionDTO {
    private double lat;
    private double lon;


    public PositionDTO(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public PositionDTO() {}

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
