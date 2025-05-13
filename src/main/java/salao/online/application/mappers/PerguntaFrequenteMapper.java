package salao.online.application.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import salao.online.application.dtos.PerguntaFrequenteDTO;
import salao.online.domain.entities.PerguntaFrequente;

@Mapper(componentModel = "cdi")
public interface PerguntaFrequenteMapper {

    @Mapping(target = "profissional", ignore = true)
    PerguntaFrequente fromDtoToEntity(PerguntaFrequenteDTO dto);

    PerguntaFrequenteDTO fromEntityToDto(PerguntaFrequente entity);

    List<PerguntaFrequenteDTO> fromEntityListToDtoList(List<PerguntaFrequente> entities);
}
