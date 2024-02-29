package salao.online.application.mappers;

import org.mapstruct.Mapper;

import salao.online.application.dtos.EnderecoDTO;
import salao.online.domain.entities.Endereco;

@Mapper(componentModel = "cdi")
public interface EnderecoMapper {
    
    Endereco toEntity(EnderecoDTO dto);

    EnderecoDTO toDto(Endereco entity);
}
