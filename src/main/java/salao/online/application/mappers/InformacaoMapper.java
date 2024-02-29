package salao.online.application.mappers;

import org.mapstruct.Mapper;

import salao.online.application.dtos.InformacaoDTO;
import salao.online.domain.entities.Informacao;

@Mapper(componentModel = "cdi")
public interface InformacaoMapper {
    
    Informacao toEntity(InformacaoDTO dto);

    InformacaoDTO toDto(Informacao entity);
}
