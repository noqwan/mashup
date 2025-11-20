package org.virtual.dto;

import org.virtual.thrift.InternalLeadDto;

public class InternalLeadDtoWrapper implements ConvertibleDTO {

  private final InternalLeadDto internalLeadDto;

  public InternalLeadDtoWrapper(InternalLeadDto internalLeadDto) {
    if (internalLeadDto == null) {
      throw new IllegalArgumentException("InternalLeadDto cannot be null");
    }
    this.internalLeadDto = internalLeadDto;
  }

  public InternalLeadDto getInternalLeadDto() {
    return internalLeadDto;
  }

  public int getId() {
    return internalLeadDto.getId();
  }

  public void setId(int id) {
    internalLeadDto.setId(id);
  }

  public String getLastNamefirstName() {
    return internalLeadDto.getLastNamefirstName();
  }

  public void setLastNamefirstName(String lastNamefirstName) {
    internalLeadDto.setLastNamefirstName(lastNamefirstName);
  }

  public double getAnnualRevenue() {
    return internalLeadDto.getAnnualRevenue();
  }

  public void setAnnualRevenue(double annualRevenue) {
    internalLeadDto.setAnnualRevenue(annualRevenue);
  }

  public String getPhone() {
    return internalLeadDto.getPhone();
  }

  public void setPhone(String phone) {
    internalLeadDto.setPhone(phone);
  }

  public String getCreationDate() {
    return internalLeadDto.getCreationDate();
  }

  public void setCreationDate(String creationDate) {
    internalLeadDto.setCreationDate(creationDate);
  }

  public String getCompany() {
    return internalLeadDto.getCompany();
  }

  public void setCompany(String company) {
    internalLeadDto.setCompany(company);
  }

  public String getState() {
    return internalLeadDto.getState();
  }

  public void setState(String state) {
    internalLeadDto.setState(state);
  }

  public String getStreet() {
    return internalLeadDto.getStreet();
  }

  public void setStreet(String street) {
    internalLeadDto.setStreet(street);
  }

  public String getPostalCode() {
    return internalLeadDto.getPostalCode();
  }

  public void setPostalCode(String postalCode) {
    internalLeadDto.setPostalCode(postalCode);
  }

  public String getCity() {
    return internalLeadDto.getCity();
  }

  public void setCity(String city) {
    internalLeadDto.setCity(city);
  }

  public String getCountry() {
    return internalLeadDto.getCountry();
  }

  public void setCountry(String country) {
    internalLeadDto.setCountry(country);
  }

  @Override
  public String toString() {
    return internalLeadDto.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof InternalLeadDtoWrapper)) {
      return false;
    }
    InternalLeadDtoWrapper that = (InternalLeadDtoWrapper) o;
    return internalLeadDto.equals(that.internalLeadDto);
  }

  @Override
  public int hashCode() {
    return internalLeadDto.hashCode();
  }
}
