package salao.online.application.mappers;

import java.time.Duration;
import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import salao.online.application.dtos.dtosDoAgendamento.AgendamentoDTO;
import salao.online.application.dtos.dtosDoAgendamento.FormaPagamentoEnumDTO;
import salao.online.application.dtos.dtosDoAgendamento.StatusAgendamentoEnumDTO;
import salao.online.domain.entities.Agendamento;
import salao.online.domain.enums.FormaPagamentoEnum;
import salao.online.domain.enums.StatusAgendamentoEnum;

@Mapper(componentModel = "cdi")
public interface AgendamentoMapper {

    @Mapping(source = "statusAgendamento", target = "status", qualifiedByName = "mapStatusEnumToDTO")
    @Mapping(source = "formaPagamento", target = "formaPagamento", qualifiedByName = "mapFormaPagamentoToDTO")
    @Mapping(source = "cliente.nome", target = "nomeCliente")
    @Mapping(source = "servico.profissional.nome", target = "nomeProfissional")
    @Mapping(source = "servico.nome", target = "nomeServico")
    @Mapping(source = "servico.valor", target = "valorServico")
    @Mapping(source = "servico.tempo", target = "tempoServico", qualifiedByName = "mapDurationToString")
    AgendamentoDTO fromEntityToDto(Agendamento entity);

    @InheritInverseConfiguration
    @Mapping(target = "statusAgendamento", source = "status", qualifiedByName = "mapStatusEnumFromDTO")
    @Mapping(target = "formaPagamento", source = "formaPagamento", qualifiedByName = "mapFormaPagamentoFromDTO")
    @Mapping(target = "servico.tempo", source = "tempoServico", qualifiedByName = "mapStringToDuration")
    Agendamento fromDtoToEntity(AgendamentoDTO dto);

    List<AgendamentoDTO> fromEntityListToDtoList(List<Agendamento> agendamentos);

     // ---------- Enum (entidade → DTO) ----------
     @Named("mapStatusEnumToDTO")
     default StatusAgendamentoEnumDTO mapStatusEnumToDTO(StatusAgendamentoEnum status) {
         if (status == null) return null;
         return StatusAgendamentoEnumDTO.fromStatusAgendamento(status.ordinal());
     }
 
     @Named("mapFormaPagamentoToDTO")
     default FormaPagamentoEnumDTO mapFormaPagamentoToDTO(FormaPagamentoEnum forma) {
         if (forma == null) return null;
         return FormaPagamentoEnumDTO.fromFormaPagamento(forma.ordinal());
     }
 
     // ---------- Enum (DTO → entidade) ----------
     @Named("mapStatusEnumFromDTO")
     default StatusAgendamentoEnum mapStatusEnumFromDTO(StatusAgendamentoEnumDTO statusDTO) {
         if (statusDTO == null) return null;
         return StatusAgendamentoEnum.fromStatusAgendamento(statusDTO.getStatusAgendamento());
     }
 
     @Named("mapFormaPagamentoFromDTO")
     default FormaPagamentoEnum mapFormaPagamentoFromDTO(FormaPagamentoEnumDTO formaDTO) {
         if (formaDTO == null) return null;
         return FormaPagamentoEnum.fromFormaPagamento(formaDTO.getFormaPagamento());
     }
 
     // ---------- Tempo ----------
     @Named("mapDurationToString")
     default String mapDurationToString(Duration duration) {
         if (duration == null)
             return null;
 
         long hours = duration.toHours();
         long minutes = duration.toMinutes() % 60;
 
         if (hours > 0) {
             return String.format("%dh%02d", hours, minutes);
         } else {
             return String.format("%dmin", minutes);
         }
     }
 
     @Named("mapStringToDuration")
     default Duration mapStringToDuration(String tempoStr) {
         if (tempoStr == null || tempoStr.isBlank())
             return null;
 
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
 
         return Duration.ZERO;
     }
 }