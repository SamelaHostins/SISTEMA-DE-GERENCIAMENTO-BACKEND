package salao.online.application.mappers;

import org.mapstruct.Mapper;

import salao.online.application.dtos.dtosDeCliente.AtualizarClienteDTO;
import salao.online.application.dtos.dtosDeCliente.BuscarClienteDTO;
import salao.online.application.dtos.dtosDeCliente.CriarClienteDTO;
import salao.online.domain.entities.Cliente;

@Mapper(componentModel = "cdi", uses = { AvaliacaoMapper.class, AgendamentoMapper.class, ImagemMapper.class })
public interface ClienteMapper {

    Cliente fromCriarDtoToEntity(CriarClienteDTO dto);

    CriarClienteDTO fromEntityToCriarDto(Cliente entity);

    AtualizarClienteDTO fromEntityToAtualizarDto(Cliente entity);

    BuscarClienteDTO fromEntityToBuscarDto(Cliente entity);
}
