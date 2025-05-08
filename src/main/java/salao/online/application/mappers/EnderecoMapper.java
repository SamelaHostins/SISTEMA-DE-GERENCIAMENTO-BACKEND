package salao.online.application.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import salao.online.application.dtos.dtosDeEndereco.EnderecoDTO;
import salao.online.domain.entities.Endereco;

@Mapper(componentModel = "cdi")  
public interface EnderecoMapper {
    
    @Mapping(target = "idEndereco", ignore = true) 
    Endereco fromDtoToEntity(EnderecoDTO dto);

    EnderecoDTO fromEntityToDto(Endereco entity);
}
