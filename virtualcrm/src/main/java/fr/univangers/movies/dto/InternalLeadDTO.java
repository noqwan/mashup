package fr.univangers.movies.dto;

import java.util.Date;

public class InternalLeadDTO implements ConvertibleDTO {

  private String id;
  private String lastNameFirstName;
  private double annualRevenue;
  private String phone;
  private Date creationDate;
  private String company;
  private String state;
  private String street;
  private String postalCode;
  private String city;
  private String country;

  public InternalLeadDTO(
      String id, String lastNameFirstName, double annualRevenue, String phone,
      Date creationDate, String company, String state, String street, String postalCode,
      String city, String country
  ) {
    this.id = id;
    this.lastNameFirstName = lastNameFirstName;
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

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getLastNameFirstName() {
    return lastNameFirstName;
  }

  public double getAnnualRevenue() {
    return annualRevenue;
  }

  public String getPhone() {
    return phone;
  }

  public Date getCreationDate() {
    return creationDate;
  }

  public String getCompany() {
    return company;
  }

  public String getState() {
    return state;
  }

  public String getStreet() {
    return street;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public String getCity() {
    return city;
  }

  public String getCountry() {
    return country;
  }

}
