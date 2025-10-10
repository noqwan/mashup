package fr.univangers.movies.service.crm;

import fr.univangers.movies.dto.ConvertibleDTO;
import fr.univangers.movies.dto.VirtualLeadDTO;
import fr.univangers.movies.dto.converter.DtoConverter;
import java.util.List;

public abstract class CRMClient<T extends ConvertibleDTO> {

  protected DtoConverter<T> converter;

  public List<VirtualLeadDTO> findLeads() {
    List<T> specificList = findLeadsSpecific();
    return specificList.stream()
        .map(this::convertToVirtual)
        .toList();
  }

  public List<VirtualLeadDTO> findLeadsByDate() {
    List<T> specificList = findLeadsByDateSpecific();
    return specificList.stream()
        .map(this::convertToVirtual)
        .toList();
  }

  public void add(VirtualLeadDTO virtualLead) {
    T specificLead = convertFromVirtual(virtualLead);
    addSpecific(specificLead);
  }

  public void delete(VirtualLeadDTO virtualLead) {
    T specificLead = convertFromVirtual(virtualLead);
    deleteSpecific(specificLead);
  }

  protected abstract List<T> findLeadsSpecific();

  protected abstract List<T> findLeadsByDateSpecific();

  protected abstract void addSpecific(T specificLead);

  protected abstract void deleteSpecific(T specificLead);

  protected abstract VirtualLeadDTO convertToVirtual(T specificLead);

  protected abstract T convertFromVirtual(VirtualLeadDTO virtualLead);
}
