package fr.univangers.movies.model;

import java.util.Date;

public class VirtualLead  extends Lead{
    private String firstName;
    private String lastName;
    private fr.univangers.movies.model.Position position;

    public VirtualLead(String id,
                       double annualRevenue,
                       String phone,
                       Date creationDate,
                       String company,
                       String state,
                       String street,
                       String postalCode,
                       String city,
                       String country,
                       String firstName,
                       String lastName) {
        super(id, annualRevenue, phone, creationDate, company,state, street, postalCode, city, country);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(fr.univangers.movies.model.Position position) {
        this.position = position;
    }
}
