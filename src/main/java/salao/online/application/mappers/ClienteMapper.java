package salao.online.application.mappers;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import salao.online.application.dtos.dtosDeCliente.BuscarClienteDTO;
import salao.online.application.dtos.dtosDeCliente.ClienteDTO;
import salao.online.application.dtos.dtosDeCliente.CriarClienteDTO;
import salao.online.domain.entities.Cliente;

@Mapper(componentModel = "cdi", uses = {AvaliacaoMapper.class, AgendamentoMapper.class})
public interface ClienteMapper {

    @InheritInverseConfiguration
    Cliente toEntity(ClienteDTO dto);

    ClienteDTO toDto(Cliente entity);

    @InheritInverseConfiguration
    Cliente criarDtoToEntity (CriarClienteDTO dto);

    CriarClienteDTO toDtoCriar (Cliente entity);

    BuscarClienteDTO toDtoBuscar (Cliente entity);

}
