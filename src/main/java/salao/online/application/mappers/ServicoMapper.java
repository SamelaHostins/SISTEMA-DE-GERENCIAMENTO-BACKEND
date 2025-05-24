package salao.online.application.mappers;

import java.time.Duration;
import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import salao.online.application.dtos.dtosDoServico.AtualizarServicoDTO;
import salao.online.application.dtos.dtosDoServico.BuscarServicoDTO;
import salao.online.application.dtos.dtosDoServico.CriarServicoDTO;
import salao.online.application.dtos.dtosDoServico.ServicoDTO;
import salao.online.application.dtos.dtosParaPesquisar.PesquisaLocalDTO;
import salao.online.application.dtos.dtosParaPesquisar.PesquisaServicoDTO;
import salao.online.domain.entities.Servico;

@Mapper(componentModel = "cdi", uses = { AvaliacaoMapper.class, AgendamentoMapper.class })
public interface ServicoMapper {

    // ---------------- DTO PRINCIPAL ----------------
    @Mapping(source = "profissional.idProfissional", target = "idProfissional")
    @Mapping(source = "idServico", target = "idServico")
    @Mapping(source = "agendamentos", target = "agendamentos")
    @Mapping(source = "avaliacoes", target = "avaliacoes")
    @Mapping(source = "tempo", target = "tempo", qualifiedByName = "durationToString")
    @Named("fromEntityToDto")
    ServicoDTO fromEntityToDto(Servico entity);

    @InheritInverseConfiguration
    @Mapping(target = "tempo", source = "tempo", qualifiedByName = "stringToDuration")
    Servico fromDtoToEntity(ServicoDTO dto);

    // ---------------- DTO DE CRIAÇÃO ----------------
    @Mapping(source = "profissional.idProfissional", target = "idProfissional")
    @Mapping(source = "tempo", target = "tempo", qualifiedByName = "durationToString")
    CriarServicoDTO fromEntityToCriarDto(Servico entity);

    @InheritInverseConfiguration
    @Mapping(target = "tempo", source = "tempo", qualifiedByName = "stringToDuration")
    @Mapping(target = "agendamentos", ignore = true)
    @Mapping(target = "avaliacoes", ignore = true)
    Servico fromCriarDtoToEntity(CriarServicoDTO dto);

    // ---------------- DTO DE ATUALIZAÇÃO ----------------
    @Mapping(source = "profissional.idProfissional", target = "idProfissional")
    @Mapping(source = "tempo", target = "tempo", qualifiedByName = "durationToString")
    AtualizarServicoDTO fromEntityToAtualizarDto(Servico entity);

    @Mapping(source = "tempo", target = "tempo", qualifiedByName = "stringToDuration")
    Servico fromAtualizarDtoToEntity(AtualizarServicoDTO dto);

    // ---------------- LISTA ----------------
    @IterableMapping(qualifiedByName = "fromEntityToDto")
    @Named("fromEntityListToDtoList")
    List<ServicoDTO> fromEntityListToDtoList(List<Servico> servicos);

    // ---------------- PESQUISA ----------------
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

    @Mapping(source = "profissional.idProfissional", target = "idProfissional")
    @Mapping(source = "profissional.endereco.cidade", target = "cidade")
    @Mapping(source = "profissional.endereco.bairro", target = "bairro")
    @Mapping(source = "profissional", target = "nomeProfissional", qualifiedByName = "mapProfissionalNome")
    @Mapping(source = "profissional.profissao", target = "profissao")
    PesquisaLocalDTO fromEntityToPesquisaLocalDto(Servico entity);

    @Mapping(source = "idServico", target = "idServico")
    @Mapping(source = "profissional.idProfissional", target = "idProfissional")
    @Mapping(source = "tempo", target = "tempo", qualifiedByName = "durationToString")
    BuscarServicoDTO fromEntityToBuscarDto(Servico entity);

    // ---------------- CONVERSORES DE TEMPO ----------------
    @Named("durationToString")
    default String durationToString(Duration duration) {
        if (duration == null)
            return null;
        long hours = duration.toHours();
        long minutes = duration.minusHours(hours).toMinutes();
        return String.format("%02d:%02d", hours, minutes);
    }

    @Named("stringToDuration")
    default Duration stringToDuration(String tempo) {
        if (tempo == null || !tempo.matches("\\d{2}:\\d{2}"))
            return null;
        String[] parts = tempo.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        return Duration.ofHours(hours).plusMinutes(minutes);
    }
}
