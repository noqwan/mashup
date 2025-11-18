package org.virtual.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.virtual.dto.PositionDTO;
import org.virtual.dto.VirtualLeadDTO;
import org.virtual.service.crm.CRMClient;
import org.virtual.service.crm.InternalClient;
import org.virtual.service.crm.SalesForceClient;
import org.virtual.service.exceptions.NoSuchLeadException;
import org.virtual.service.exceptions.WrongDateFormatException;
import org.virtual.service.exceptions.WrongOrderForDate;
import org.virtual.service.exceptions.WrongOrderForRevenue;
import org.virtual.service.positionClient.PositionClient;
import org.virtual.service.positionClient.PositionClientImpl;

@RestController
public class VirtualCRMImpl implements VirtualCRMService {

  private final List<CRMClient<?>> clients = new ArrayList<>();

  private final PositionClient positionClient = new PositionClientImpl();

  public VirtualCRMImpl() {
    clients.add(new SalesForceClient());
    clients.add(new InternalClient());
  }

  @Override
  @GetMapping("/findLeads")
  @ResponseStatus(HttpStatus.OK)
  public List<VirtualLeadDTO> findLeads(double lowAnnualRevenue, double highAnnualRevenue,
      String state) throws WrongOrderForRevenue {

    List<VirtualLeadDTO> result = new ArrayList<>();
    for (CRMClient<?> client : clients) {
      result.addAll(client.findLeads(lowAnnualRevenue, highAnnualRevenue, state));
    }

    for (VirtualLeadDTO dto : result) {
      PositionDTO pos = positionClient.getPositionDTO(dto.getStreet(), dto.getPostalCode(),
          dto.getCity(),
          dto.getCountry());
      dto.setPos(pos);
    }

    return result;
  }

  @Override
  @GetMapping("/findLeadsByDate")
  @ResponseStatus(HttpStatus.OK)
  public List<VirtualLeadDTO> findLeadsByDate(String startDate,
      String endDate)
      throws WrongOrderForDate, WrongDateFormatException {

    Calendar calStart = ConverterUtils.toCalendarFromString(startDate);
    Calendar calEnd = ConverterUtils.toCalendarFromString(endDate);

    List<VirtualLeadDTO> result = new ArrayList<>();
    for (CRMClient<?> client : clients) {
      result.addAll(client.findLeadsByDate(calStart, calEnd));
    }

    for (VirtualLeadDTO dto : result) {
      PositionDTO pos = positionClient.getPositionDTO(dto.getStreet(), dto.getPostalCode(),
          dto.getCity(),
          dto.getCountry());
      dto.setPos(pos);
    }

    return result;
  }

  @Override
  @PostMapping("/delete")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteLead(@RequestBody VirtualLeadDTO lead)
      throws WrongDateFormatException, NoSuchLeadException {

    if (lead.getId().startsWith("I")) {
      lead.setId(lead.getId().substring(1));
      clients.get(1).delete(lead);
    } else if (lead.getId().startsWith("S")) {
      lead.setId(lead.getId().substring(1));
      clients.get(0).delete(lead);
    }
  }

  @Override
  @PostMapping("/add")
  @ResponseStatus(HttpStatus.CREATED)
  public void addLead(@RequestBody VirtualLeadDTO lead) throws WrongDateFormatException {
    // Ajout uniquement au client de InternalCRM
    clients.get(1).add(lead);
  }

}
