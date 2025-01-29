package salao.online.application.mappers;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import salao.online.application.dtos.dtosDoProfissional.BuscarProfissionalDTO;
import salao.online.application.dtos.dtosDoProfissional.CriarProfissionalDTO;
import salao.online.application.dtos.dtosDoProfissional.ListarProfissionalDTO;
import salao.online.application.dtos.dtosDoProfissional.ProfissionalDTO;
import salao.online.application.dtos.dtosParaPesquisar.PesquisaProfissionalDTO;
import salao.online.domain.entities.Profissional;

@Mapper(componentModel = "cdi", uses = { EstoqueMapper.class, ServicoMapper.class, ImagemMapper.class,
        AgendamentoMapper.class, AvaliacaoMapper.class })
public interface ProfissionalMapper {

    @Mapping(target = "nome", source = "nome")
    @Mapping(target = "sobrenome", source = "sobrenome")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "telefone", source = "telefone")
    @Mapping(source = "estoques", target = "estoques")
    @Mapping(source = "servicos", target = "servicos")
    @Mapping(source = "imagens", target = "imagens")
    ProfissionalDTO fromEntityToDto(Profissional entity);

    @InheritInverseConfiguration
    Profissional fromDtoToEntity(ProfissionalDTO dto);

    CriarProfissionalDTO fromEntityToCriarDto(Profissional entity);

    Profissional fromCriarDtoToEntity(CriarProfissionalDTO dto);

    BuscarProfissionalDTO fromEntityToBuscarDto(Profissional entity);

    @Mapping(target = "idProfissional", source = "idProfissional")
    @Mapping(target = "usuario", source = "usuario")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "telefone", source = "telefone")
    @Mapping(source = "servicos", target = "servicos")
    @Mapping(source = "imagens", target = "imagens")
    ListarProfissionalDTO fromEntityToListarDto(Profissional entity);

    @Named("mapNomeCompleto") 
    default String mapNomeCompleto(Profissional profissional) {
        return profissional.getNome() + " " + profissional.getSobrenome();
    }

    @Mapping(source = "idProfissional", target = "idProfissional")
    @Mapping(source = "usuario", target = "usuario")
    @Mapping(source = ".", target = "nome", qualifiedByName = "mapNomeCompleto") 
    PesquisaProfissionalDTO fromEntityToPesquisaDto(Profissional entity);
}
