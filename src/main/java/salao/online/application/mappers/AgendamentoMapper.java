package salao.online.application.mappers;

import java.time.Duration;
import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import salao.online.application.dtos.dtosDoAgendamento.AgendamentoDTO;
import salao.online.domain.entities.Agendamento;
import salao.online.domain.enums.FormaPagamentoEnum;
import salao.online.domain.enums.StatusAgendamentoEnum;

@Mapper(componentModel = "cdi")
public interface AgendamentoMapper {

    @Mapping(source = "statusAgendamento", target = "status", qualifiedByName = "mapStatusEnumToInt")
    @Mapping(source = "formaPagamento", target = "formaPagamento", qualifiedByName = "mapFormaPagamentoToInt")
    @Mapping(source = "cliente.nome", target = "nomeCliente")
    @Mapping(source = "servico.profissional.nome", target = "nomeProfissional")
    @Mapping(source = "servico.nome", target = "nomeServico")
    @Mapping(source = "servico.valor", target = "valorServico")
    @Mapping(source = "servico.tempo", target = "tempoServico", qualifiedByName = "mapDurationToString")
    AgendamentoDTO fromEntityToDto(Agendamento entity);

    @InheritInverseConfiguration
    @Mapping(target = "statusAgendamento", source = "status", qualifiedByName = "mapIntToStatusEnum")
    @Mapping(target = "formaPagamento", source = "formaPagamento", qualifiedByName = "mapIntToFormaPagamentoEnum")
    @Mapping(target = "servico.tempo", source = "tempoServico", qualifiedByName = "mapStringToDuration")
    Agendamento fromDtoToEntity(AgendamentoDTO dto);

    List<AgendamentoDTO> fromEntityListToDtoList(List<Agendamento> agendamentos);

    // Enums: entidade -> Integer
    @Named("mapStatusEnumToInt")
    default Integer mapStatusEnumToInt(StatusAgendamentoEnum status) {
        return status != null ? status.ordinal() : null;
    }

    @Named("mapFormaPagamentoToInt")
    default Integer mapFormaPagamentoToInt(FormaPagamentoEnum forma) {
        return forma != null ? forma.ordinal() : null;
    }

    // Enums: Integer -> entidade
    @Named("mapIntToStatusEnum")
    default StatusAgendamentoEnum mapIntToStatusEnum(Integer valor) {
        return valor != null ? StatusAgendamentoEnum.values()[valor] : null;
    }

    @Named("mapIntToFormaPagamentoEnum")
    default FormaPagamentoEnum mapIntToFormaPagamentoEnum(Integer valor) {
        return valor != null ? FormaPagamentoEnum.values()[valor] : null;
    }

    // Tempo
    @Named("mapDurationToString")
    default String mapDurationToString(Duration duration) {
        if (duration == null) return null;

        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;

        return (hours > 0)
            ? String.format("%dh%02d", hours, minutes)
            : String.format("%dmin", minutes);
    }

    @Named("mapStringToDuration")
    default Duration mapStringToDuration(String tempoStr) {
        if (tempoStr == null || tempoStr.isBlank()) return null;

        tempoStr = tempoStr.toLowerCase().replace(" ", "");

        if (tempoStr.contains("h")) {
            String[] partes = tempoStr.split("h");
            long horas = Long.parseLong(partes[0]);
            long minutos = partes.length > 1 ? Long.parseLong(partes[1].replace("min", "")) : 0;
            return Duration.ofHours(horas).plusMinutes(minutos);
        } else if (tempoStr.contains("min")) {
            return Duration.ofMinutes(Long.parseLong(tempoStr.replace("min", "")));
        }

        return Duration.ZERO;
    }
}
