package salao.online.application.mappers;

import java.time.Duration;
import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import salao.online.application.dtos.dtosDoAgendamento.AgendamentoDTO;
import salao.online.domain.entities.Agendamento;

@Mapper(componentModel = "cdi")
public interface AgendamentoMapper {

    @Mapping(source = "statusAgendamento", target = "status")
    @Mapping(source = "cliente.nome", target = "nomeCliente")
    @Mapping(source = "servico.profissional.nome", target = "nomeProfissional")
    @Mapping(source = "servico.nome", target = "nomeServico")
    @Mapping(source = "servico.valor", target = "valorServico")
    @Mapping(source = "servico.tempo", target = "tempoServico", qualifiedByName = "mapDurationToString")
    AgendamentoDTO fromEntityToDto(Agendamento entity);

    @InheritInverseConfiguration
    @Mapping(target = "servico.tempo", source = "tempoServico", qualifiedByName = "mapStringToDuration")
    Agendamento fromDtoToEntity(AgendamentoDTO dto);

    List<AgendamentoDTO> fromEntityListToDtoList(List<Agendamento> agendamentos);

    // ---------- Métodos auxiliares para tempo ----------
    @Named("mapDurationToString")
    default String mapDurationToString(Duration duration) {
        if (duration == null)
            return null;

        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;

        if (hours > 0) {
            return String.format("%dh%02d", hours, minutes); // exemplo: 1h30
        } else {
            return String.format("%dmin", minutes); // exemplo: 45min
        }
    }

    @Named("mapStringToDuration")
    default Duration mapStringToDuration(String tempoStr) {
        if (tempoStr == null || tempoStr.isBlank())
            return null;

        // Aceita formatos tipo "1h30", "90min"
        tempoStr = tempoStr.toLowerCase().replace(" ", "");

        if (tempoStr.contains("h")) {
            String[] partes = tempoStr.split("h");
            long horas = Long.parseLong(partes[0]);
            long minutos = partes.length > 1 ? Long.parseLong(partes[1].replace("min", "")) : 0;
            return Duration.ofHours(horas).plusMinutes(minutos);
        } else if (tempoStr.contains("min")) {
            long minutos = Long.parseLong(tempoStr.replace("min", ""));
            return Duration.ofMinutes(minutos);
        }

        // fallback
        return Duration.ZERO;
    }
}
