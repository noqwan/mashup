package org.internal.model;

import java.util.Calendar;
import java.util.Objects;

public class Lead {

  private int id;
  private String lastNamefirstName;
  private double annualRevenue;
  private String phone;
  private Calendar creationDate;
  private String company;
  private String state;
  private String street;
  private String postalCode;
  private String city;

  private String country;

  // Empty constructor for serialisation
  public Lead() {}

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

  public double getAnnualRevenue() {
    return annualRevenue;
  }

  public void setAnnualRevenue(double annualRevenue) {
    this.annualRevenue = annualRevenue;
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

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Lead lead = (Lead) o;
    return id == lead.getId();
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }

  @Override
  public String toString() {
    return "Lead{" +
            "id=" + id +
            ", lastNamefirstName='" + lastNamefirstName + '\'' +
            ", annualRevenue=" + annualRevenue +
            ", phone='" + phone + '\'' +
            ", creationDate=" + creationDate +
            ", company='" + company + '\'' +
            ", state='" + state + '\'' +
            ", street='" + street + '\'' +
            ", postalCode='" + postalCode + '\'' +
            ", city='" + city + '\'' +
            ", country='" + country + '\'' +
            '}';
  }
}
