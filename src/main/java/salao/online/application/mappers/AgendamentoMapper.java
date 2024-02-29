package salao.online.application.mappers;

import salao.online.application.dtos.AgendamentoDTO;
import salao.online.domain.entities.Agendamento;

import java.util.List;

import org.mapstruct.Named;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface AgendamentoMapper {

    Agendamento toEntity(AgendamentoDTO dto);

    @Mapping(source = "idAgendamento", target = "idAgendamento")
    @Mapping(source = "cliente.idCliente", target = "idCliente")
    @Mapping(source = "servico.idServico", target = "idServico")
    @Named("mapToDTO")
    AgendamentoDTO toDto(Agendamento entity);

    @IterableMapping(qualifiedByName = "mapToDTO")
    @Named("mapListToDtoList")
    List<AgendamentoDTO> toDtoList(List<Agendamento> agendamentos);
}
