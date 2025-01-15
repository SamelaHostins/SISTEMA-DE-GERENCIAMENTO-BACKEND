package salao.online.application.mappers;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import salao.online.application.dtos.dtosDoProfissional.BuscarProfissionalDTO;
import salao.online.application.dtos.dtosDoProfissional.CriarProfissionalDTO;
import salao.online.application.dtos.dtosDoProfissional.ListarProfissionalDTO;
import salao.online.application.dtos.dtosDoProfissional.ProfissionalDTO;
import salao.online.domain.entities.Profissional;

@Mapper(componentModel = "cdi", uses = { EstoqueMapper.class, ServicoMapper.class, ImagemMapper.class })
public interface ProfissionalMapper {

    @InheritInverseConfiguration
    Profissional toEntity(ProfissionalDTO dto);

    ProfissionalDTO toDto(Profissional entity);

    CriarProfissionalDTO toDtoCriar(Profissional entity);

    Profissional criarDtoToEntity(CriarProfissionalDTO dto);

    BuscarProfissionalDTO toDtoBuscar (Profissional entity);

    ListarProfissionalDTO toDtoListar (Profissional entity);
}
