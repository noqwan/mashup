package org.virtual.dto.converter;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.virtual.dto.InternalLeadDtoWrapper;
import org.virtual.dto.VirtualLeadDTO;
import org.virtual.thrift.InternalLeadDto;

public class InternalDtoConverter implements DtoConverter<InternalLeadDtoWrapper> {

  @Override
  public VirtualLeadDTO convertToVirtual(InternalLeadDtoWrapper wrapper) {

    InternalLeadDto dto = wrapper.getInternalLeadDto();

    try {
      String firstName = dto.getLastNamefirstName().split(",")[1].strip();
      String lastName = dto.getLastNamefirstName().split(",")[0].strip();
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
      Date date = sdf.parse(dto.getCreationDate());
      VirtualLeadDTO virtualDto = new VirtualLeadDTO("I" + dto.getId(), firstName, lastName,
          dto.getAnnualRevenue(),
          dto.getPhone(), date, dto.getCompany(), dto.getState(), dto.getStreet(),
          dto.getPostalCode(), dto.getCity(), dto.getCountry());
      return virtualDto;
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  @Override
  public InternalLeadDtoWrapper convertFromVirtual(VirtualLeadDTO dto) {

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    String creationDate = sdf.format(dto.getCreationDate());

    InternalLeadDto ret = new InternalLeadDto();
    try {
      // Remove the 'I' prefix before parsing
      String idStr = dto.getId();
      if (idStr != null) {
        if (idStr.startsWith("I")) {
          idStr = idStr.substring(1);
        }
        ret.setId(Integer.parseInt(idStr));
      } else {
        ret.setId(-1);
      }
    } catch (NumberFormatException e) {
      // Lead id will be set by the CRM himself anyway
      ret.setId(-1);
    }
    ret.setLastNamefirstName(dto.getLastName() + ", " + dto.getFirstName());
    ret.setAnnualRevenue(dto.getAnnualRevenue());
    ret.setPhone(dto.getPhone());
    ret.setCreationDate(creationDate);
    ret.setCompany(dto.getCompany());
    ret.setState(dto.getState());
    ret.setStreet(dto.getStreet());
    ret.setPostalCode(dto.getPostalCode());
    ret.setCity(dto.getCity());
    ret.setCountry(dto.getCountry());

    return new InternalLeadDtoWrapper(ret);
  }
}
