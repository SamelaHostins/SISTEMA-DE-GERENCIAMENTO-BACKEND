package salao.online.application.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import salao.online.application.dtos.dtosDeCliente.AtualizarClienteDTO;
import salao.online.application.dtos.dtosDeCliente.BuscarClienteDTO;
import salao.online.application.dtos.dtosDeCliente.CriarClienteDTO;
import salao.online.domain.entities.Cliente;

@Mapper(componentModel = "cdi", uses = { AvaliacaoMapper.class, AgendamentoMapper.class, ImagemMapper.class })
public interface ClienteMapper {

    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    @Mapping(target = "idCliente", ignore = true)
    @Mapping(target = "dataNascimento", source = "dto.dataNascimento")
    @Mapping(source = "aceitouTermos", target = "aceitouTermos")
    Cliente fromCriarDtoToEntity(CriarClienteDTO dto);

    @Mapping(target = "dataNascimento", source = "entity.dataNascimento")
    CriarClienteDTO fromEntityToCriarDto(Cliente entity);

    AtualizarClienteDTO fromEntityToAtualizarDto(Cliente entity);

    @Mapping(target = "dataNascimento", source = "entity.dataNascimento")
    BuscarClienteDTO fromEntityToBuscarDto(Cliente entity);
}
