package salao.online.application.mappers;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import salao.online.application.dtos.dtosDoAgendamento.AgendamentoDTO;
import salao.online.domain.entities.Agendamento;

@Mapper(componentModel = "cdi")
public interface AgendamentoMapper {

    @Mapping(source = "statusAgendamento", target = "status")
    @Mapping(source = "cliente.nome", target = "nomeCliente")
    @Mapping(source = "servico.profissional.nome", target = "nomeProfissional")
    @Mapping(source = "servico.nome", target = "nomeServico")
    @Mapping(source = "servico.valor", target = "valorServico")
    @Mapping(source = "servico.tempo", target = "tempoServico")
    AgendamentoDTO fromEntityToDto(Agendamento entity);

    @InheritInverseConfiguration
    Agendamento fromDtoToEntity(AgendamentoDTO dto);

    List<AgendamentoDTO> fromEntityListToDtoList(List<Agendamento> agendamentos);
}
