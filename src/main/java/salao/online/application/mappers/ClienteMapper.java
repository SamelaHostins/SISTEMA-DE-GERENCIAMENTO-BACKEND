package salao.online.application.mappers;

import salao.online.application.dtos.ClienteDTO;
import salao.online.domain.entities.Cliente;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface ClienteMapper {

    Cliente toEntity(ClienteDTO dto);

    ClienteDTO toDto(Cliente entity);
}
