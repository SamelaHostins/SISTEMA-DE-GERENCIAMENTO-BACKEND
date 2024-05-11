package salao.online.application.mappers;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import salao.online.application.dtos.ProfissionalDTO;
import salao.online.domain.entities.Profissional;

@Mapper(componentModel = "cdi", uses = {EstoqueMapper.class, ServicoMapper.class})
public interface ProfissionalMapper {

    @InheritInverseConfiguration
    Profissional toEntity(ProfissionalDTO dto);

    @Mapping(source = "endereco.idEndereco", target = "idEndereco")
    ProfissionalDTO toDto(Profissional entity);
}
