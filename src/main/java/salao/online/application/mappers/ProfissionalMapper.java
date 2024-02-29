package salao.online.application.mappers;

import salao.online.application.dtos.ProfissionalDTO;
import salao.online.domain.entities.Profissional;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface ProfissionalMapper {

    Profissional toEntity(ProfissionalDTO dto);

    ProfissionalDTO toDto(Profissional entity);
}
