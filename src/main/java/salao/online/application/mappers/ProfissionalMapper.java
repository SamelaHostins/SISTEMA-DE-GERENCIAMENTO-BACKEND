package salao.online.application.mappers;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import salao.online.application.dtos.dtosDoProfissional.BuscarProfissionalDTO;
import salao.online.application.dtos.dtosDoProfissional.CriarProfissionalDTO;
import salao.online.application.dtos.dtosDoProfissional.ListarProfissionalDTO;
import salao.online.application.dtos.dtosDoProfissional.ProfissionalDTO;
import salao.online.domain.entities.Profissional;

@Mapper(componentModel = "cdi", uses = { EstoqueMapper.class, ServicoMapper.class, ImagemMapper.class })
public interface ProfissionalMapper {

    @Mapping(target = "nome", source = "nome")
    @Mapping(target = "sobrenome", source = "sobrenome")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "telefone", source = "telefone")
    @Mapping(source = "imagens", target = "imagens") 
    ProfissionalDTO fromEntityToDto(Profissional entity);

    @Mapping(target = "nome", source = "nome")
    @Mapping(target = "sobrenome", source = "sobrenome")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "telefone", source = "telefone")
    @Mapping(source = "imagens", target = "imagens") 
    Profissional fromDtoToEntity(ProfissionalDTO dto);

    CriarProfissionalDTO fromEntityToCriarDto(Profissional entity);

    Profissional fromCriarDtoToEntity(CriarProfissionalDTO dto);

    BuscarProfissionalDTO fromEntityToBuscarDto(Profissional entity);

    ListarProfissionalDTO fromEntityToListarDto(Profissional entity);
}
