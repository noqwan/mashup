package fr.univangers.movies.model;

import java.util.Calendar;
import java.util.Date;

public class Lead {
    private String id;
    private double annualRevenue;
    private String phone;
    private Date creationDate;
    private String company;
    private String state;
    private String street;
    private String postalCode;
    private String city;
    private String country;

    public Lead(String id,
                double annualRevenue,
                String phone,
                Date creationDate,
                String company,
                String state,
                String street,
                String postalCode,
                String city,
                String country){
        this.id = id;
        this.annualRevenue = annualRevenue;
        this.phone = phone;
        this.creationDate = creationDate;
        this.company = company;
        this.state = state;
        this.street = street;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getAnnualRevenue() {
        return annualRevenue;
    }

    public void setAnnualRevenue(double annualRevenue) {
        this.annualRevenue = annualRevenue;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
