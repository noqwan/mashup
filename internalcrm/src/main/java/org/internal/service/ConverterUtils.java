package org.internal.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.internal.model.Lead;
import org.internal.model.exception.WrongDateFormatException;
import org.internal.thrift.InternalLeadDto;

public class ConverterUtils {

  public static Lead convertLeadDtoToLead(InternalLeadDto leadDto) throws WrongDateFormatException {
    return new Lead(
        leadDto.getId(),
        leadDto.getLastNamefirstName(),
        leadDto.getAnnualRevenue(),
        leadDto.getPhone(),
        toCalendarFromString(leadDto.getCreationDate()),
        leadDto.getCompany(),
        leadDto.getState(),
        leadDto.getStreet(),
        leadDto.getPostalCode(),
        leadDto.getCity(),
        leadDto.getCountry()
    );
  }

  public static InternalLeadDto convertLeadToLeadDto(Lead lead) {

    InternalLeadDto dto = new InternalLeadDto(
        lead.getId(),
        lead.getLastNamefirstName(),
        lead.getAnnualRevenue(),
        lead.getPhone(),
        toStringFromCalendar(lead.getCreationDate()),
        lead.getCompany(),
        lead.getState(),
        lead.getStreet(),
        lead.getPostalCode(),
        lead.getCity(),
        lead.getCountry()
    );

    return dto;
  }

  public static Calendar toCalendarFromString(String string) throws WrongDateFormatException {
    try {
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
      Date date = sdf.parse(string); // Parse string to Date
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      return calendar;
    } catch (Exception e) {
      throw new WrongDateFormatException(e.getMessage());
    }
  }

  public static String toStringFromCalendar(Calendar calendar) {

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    return sdf.format(calendar.getTime());
  }


}
