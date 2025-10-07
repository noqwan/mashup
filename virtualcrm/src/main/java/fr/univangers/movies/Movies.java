package fr.univangers.movies;

import fr.univangers.movies.model.Position;
import fr.univangers.movies.model.PositionDTO;
import fr.univangers.movies.model.VirtualLead;
import fr.univangers.movies.service.PositionClient.PositionClientImpl;

import java.util.Date;

public class Movies {

    public static void main(String[] args) {
        VirtualLead lead = new VirtualLead("1", 1000.0,"0652092354",new Date(), "Infotel", "", "", "72100", "Le Mans", "FRance", "Nathan", "Raccouard");
        PositionClientImpl client = new PositionClientImpl();
        try{
            PositionDTO positionDTO = client.getPositionDTO(lead.getStreet(), lead.getPostalCode(), lead.getCity(), lead.getCountry());
            lead.setPosition(new Position(positionDTO.getLat(), positionDTO.getLon()));
        }catch(NullPointerException e){
            System.err.println("No positions found");
        }

        if(lead.getPosition() != null){
            System.out.println(lead.getPosition().getLatitude());
            System.out.println(lead.getPosition().getLongitude());
        }

    }
}

