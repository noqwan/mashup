package org.virtual.service.crm;

import java.util.Calendar;
import java.util.List;
import org.virtual.dto.ConvertibleDTO;
import org.virtual.dto.VirtualLeadDTO;
import org.virtual.dto.converter.DtoConverter;
import org.virtual.service.exceptions.NoSuchLeadException;
import org.virtual.service.exceptions.WrongDateFormatException;
import org.virtual.service.exceptions.WrongOrderForDate;
import org.virtual.service.exceptions.WrongOrderForRevenue;
import org.virtual.service.exceptions.WrongState;

public abstract class CRMClient<T extends ConvertibleDTO> {

  protected DtoConverter<T> converter;

  public List<VirtualLeadDTO> findLeads(double lowAnnualRevenue, double highAnnualRevenue,
      String state) throws WrongOrderForRevenue, WrongState {

    List<T> specificList = findLeadsSpecific(lowAnnualRevenue, highAnnualRevenue,
        state);

    return specificList.stream()
        .map(this::convertToVirtual)
        .toList();
  }

  public List<VirtualLeadDTO> findLeadsByDate(Calendar startDate, Calendar endDate)
      throws WrongOrderForDate {
    List<T> specificList = findLeadsByDateSpecific(startDate, endDate);
    return specificList.stream()
        .map(this::convertToVirtual)
        .toList();
  }

  public void add(VirtualLeadDTO virtualLead) throws WrongDateFormatException {
    T specificLead = convertFromVirtual(virtualLead);
    addSpecific(specificLead);
  }

  public void delete(VirtualLeadDTO virtualLead)
      throws WrongDateFormatException, NoSuchLeadException {
    T specificLead = convertFromVirtual(virtualLead);
    deleteSpecific(specificLead);
  }

  protected abstract List<T> findLeadsSpecific(double lowAnnualRevenue, double highAnnualRevenue,
      String state) throws WrongOrderForRevenue, WrongState;

  protected abstract List<T> findLeadsByDateSpecific(Calendar startDate, Calendar endDate)
      throws WrongOrderForDate;

  protected abstract void addSpecific(T specificLead) throws WrongDateFormatException;

  protected abstract void deleteSpecific(T specificLead)
      throws WrongDateFormatException, NoSuchLeadException;

  protected abstract VirtualLeadDTO convertToVirtual(T specificLead);

  protected abstract T convertFromVirtual(VirtualLeadDTO virtualLead);
}
