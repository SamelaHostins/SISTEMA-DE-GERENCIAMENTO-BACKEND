package salao.online.application.mappers;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import salao.online.application.dtos.dtosDoServico.AtualizarServicoDTO;
import salao.online.application.dtos.dtosDoServico.CriarServicoDTO;
import salao.online.application.dtos.dtosDoServico.ServicoDTO;
import salao.online.application.dtos.dtosParaPesquisar.PesquisaLocalDTO;
import salao.online.application.dtos.dtosParaPesquisar.PesquisaServicoDTO;
import salao.online.domain.entities.Servico;

@Mapper(componentModel = "cdi", uses = { AvaliacaoMapper.class, AgendamentoMapper.class })
public interface ServicoMapper {

    @Mapping(source = "profissional.idProfissional", target = "idProfissional")
    @Mapping(source = "idServico", target = "idServico")
    @Mapping(source = "agendamentos", target = "agendamentos")
    @Mapping(source = "avaliacoes", target = "avaliacoes")
    @Named("fromEntityToDto")
    ServicoDTO fromEntityToDto(Servico entity);

    @InheritInverseConfiguration
    Servico fromDtoToEntity(ServicoDTO dto);

    @Mapping(source = "profissional.idProfissional", target = "idProfissional")
    CriarServicoDTO fromEntityToCriarDto(Servico entity);

    @InheritInverseConfiguration
    @Mapping(target = "agendamentos", ignore = true)
    @Mapping(target = "avaliacoes", ignore = true)
    Servico fromCriarDtoToEntity(CriarServicoDTO dto);

    @Mapping(source = "profissional.idProfissional", target = "idProfissional")
    AtualizarServicoDTO fromEntityToAtualizarDto(Servico entity);

    @IterableMapping(qualifiedByName = "fromEntityToDto")
    @Named("fromEntityListToDtoList")
    List<ServicoDTO> fromEntityListToDtoList(List<Servico> servicos);

    @Named("mapProfissionalNome")
    default String mapProfissionalNome(salao.online.domain.entities.Profissional profissional) {
        String[] partes = profissional.getSobrenome().trim().split("\\s+");
        String ultimoSobrenome = partes[partes.length - 1];
        return profissional.getNome() + " " + ultimoSobrenome;
    }

    @Mapping(source = "idServico", target = "idServico") 
    @Mapping(source = "profissional.idProfissional", target = "idProfissional") 
    @Mapping(source = "nome", target = "nomeServico") 
    @Mapping(source = "profissional", target = "nomeProfissional", qualifiedByName = "mapProfissionalNome") 
    PesquisaServicoDTO fromEntityToPesquisaDto(Servico entity);

    List<PesquisaServicoDTO> fromEntityListToPesquisaDtoList(List<Servico> servicos);

}
