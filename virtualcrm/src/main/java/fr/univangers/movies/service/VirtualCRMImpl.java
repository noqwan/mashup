package fr.univangers.movies.service;

import fr.univangers.movies.dto.VirtualLeadDTO;
import fr.univangers.movies.service.crm.CRMClient;
import fr.univangers.movies.service.crm.InternalClient;
import fr.univangers.movies.service.crm.SalesForceClient;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VirtualCRMImpl implements VirtualCRMService {

  private final List<CRMClient<?>> clients = new ArrayList<>();
  
  public VirtualCRMImpl() {
    clients.add(new SalesForceClient());
    clients.add(new InternalClient());
  }

  @Override
  @GetMapping("/findLeads")
  @ResponseStatus(HttpStatus.OK)
  public List<VirtualLeadDTO> findLeads(double lowAnnualRevenue, double highAnnualRevenue,
      String state) {
    List<VirtualLeadDTO> result = new ArrayList<>();
    for (CRMClient<?> client : clients) {
      result.addAll(client.findLeads());
    }
    // Ici tu peux filtrer selon lowAnnualRevenue, highAnnualRevenue et state si besoin
    return result;
  }

  @Override
  @GetMapping("/findLeadsByDate")
  @ResponseStatus(HttpStatus.OK)
  public List<VirtualLeadDTO> findLeadsByDate(Calendar startDate, Calendar endDate) {
    List<VirtualLeadDTO> result = new ArrayList<>();
    for (CRMClient<?> client : clients) {
      result.addAll(client.findLeadsByDate());
    }
    // Ici tu peux filtrer selon startDate / endDate si nécessaire
    return result;
  }

  @Override
  @DeleteMapping("/delete")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteLead(@RequestBody VirtualLeadDTO lead) {
    for (CRMClient<?> client : clients) {
      client.delete(lead);
    }
  }

  @Override
  @PostMapping("/add")
  @ResponseStatus(HttpStatus.CREATED)
  public void addLead(@RequestBody VirtualLeadDTO lead) {
    for (CRMClient<?> client : clients) {
      client.add(lead);
    }
  }
}
