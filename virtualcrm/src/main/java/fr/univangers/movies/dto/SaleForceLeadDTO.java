package fr.univangers.movies.dto;

import java.util.Date;

public class SaleForceLeadDTO implements ConvertibleDTO {

  private String id;
  private String firstName;
  private String lastName;
  private double annualRevenue;
  private String phone;
  private Date creationDate;
  private String company;
  private String state;
  private String street;
  private String postalCode;
  private String city;
  private String country;

  public SaleForceLeadDTO(
      String id, String firstName, String lastName, double annualRevenue, String phone,
      Date creationDate, String company, String state, String street, String postalCode,
      String city, String country
  ) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
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

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
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
