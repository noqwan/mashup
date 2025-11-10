package org.virtual.dto.converter;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.virtual.dto.VirtualLeadDTO;
import org.virtual.thrift.InternalLeadDto;

public class InternalDtoConverter implements DtoConverter<InternalLeadDto> {

  @Override
  public VirtualLeadDTO convertToVirtual(InternalLeadDto dto) {
    String firstName = dto.getLastNamefirstName().split(",")[1].strip();
    String lastName = dto.getLastNamefirstName().split(",")[0].strip();

    try {
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
      Date date = sdf.parse(dto.getCreationDate());
      return new VirtualLeadDTO("I" + dto.getId(), firstName, lastName,
          dto.getAnnualRevenue(),
          dto.getPhone(), date, dto.getCompany(), dto.getState(), dto.getStreet(),
          dto.getPostalCode(), dto.getCity(), dto.getCountry());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public InternalLeadDto convertFromVirtual(VirtualLeadDTO dto) {

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
    String creationDate = sdf.format(dto.getCreationDate());

    InternalLeadDto ret = new InternalLeadDto();
    ret.setId(Integer.parseInt(dto.getId()));
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

    return ret;
  }
}
