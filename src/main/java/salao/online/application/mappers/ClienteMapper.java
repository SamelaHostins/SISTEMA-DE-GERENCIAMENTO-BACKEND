package salao.online.application.mappers;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import salao.online.application.dtos.ClienteDTO;
import salao.online.domain.entities.Cliente;

@Mapper(componentModel = "cdi", uses = {AvaliacaoMapper.class, AgendamentoMapper.class})
public interface ClienteMapper {

    @InheritInverseConfiguration
    Cliente toEntity(ClienteDTO dto);

    ClienteDTO toDto(Cliente entity);
}
