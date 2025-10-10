package fr.univangers.movies;

import fr.univangers.movies.model.Lead;
import fr.univangers.movies.model.VirtualLead;
import fr.univangers.movies.service.PositionClient.PositionClient;
import fr.univangers.movies.service.PositionClient.PositionClientImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

public class Movies {

    public static void main(String[] args) {
        VirtualLead lead = new VirtualLead("1", 1000.0,"0652092354",new Date(), "Infotel", "Sarthe", "andromede", "72100", "Le Mans", "France", "Nathan", "Raccouard");
        PositionClientImpl client = new PositionClientImpl();
        lead.setPosition(client.getPosition(lead.getStreet(), lead.getCity(), lead.getPostalCode(), lead.getCountry()));
    }
}

