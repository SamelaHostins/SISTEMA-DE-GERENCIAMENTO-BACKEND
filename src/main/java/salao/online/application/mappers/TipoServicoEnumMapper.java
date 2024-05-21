package salao.online.application.mappers;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import salao.online.application.dtos.TipoServicoEnumDTO;
import salao.online.domain.enums.TipoServicoEnum;

@Mapper(componentModel = "cdi")
public interface TipoServicoEnumMapper {

    @InheritInverseConfiguration
    TipoServicoEnum toEntity(TipoServicoEnumDTO dto);

    TipoServicoEnumDTO toDto(TipoServicoEnum entity);

}
