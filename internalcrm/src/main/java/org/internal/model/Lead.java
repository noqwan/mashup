package org.internal.model;

import java.util.Calendar;

public class Lead {

  public int id;
  public String lastNamefirstName;
  public double annualRevenue;
  public String phone;
  public Calendar creationDate;
  private String company;
  private String state;
  private String street;
  private String postalCode;
  private String city;
  private String country;

  public Lead() {

  }

  public Lead(int id, String lastNamefirstName, double annualRevenue, String phone,
      Calendar creationDate, String company, String state, String street, String postalCode,
      String city, String country) {
    this.id = id;
    this.lastNamefirstName = lastNamefirstName;
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

  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getLastNamefirstName() {
    return lastNamefirstName;
  }

  public void setLastNamefirstName(String lastNamefirstName) {
    this.lastNamefirstName = lastNamefirstName;
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

  public Calendar getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(Calendar creationDate) {
    this.creationDate = creationDate;
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

  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
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
