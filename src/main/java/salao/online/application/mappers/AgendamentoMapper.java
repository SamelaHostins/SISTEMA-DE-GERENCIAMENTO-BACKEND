package salao.online.application.mappers;

import salao.online.application.dtos.AgendamentoDTO;
import salao.online.domain.entities.Agendamento;

import java.util.List;

import org.mapstruct.Named;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface AgendamentoMapper {

    @InheritInverseConfiguration
    Agendamento fromDtoToEntity(AgendamentoDTO dto);

    @Mapping(source = "cliente.idCliente", target = "idCliente")
    @Mapping(source = "servico.idServico", target = "idServico")
    @Named("fromEntityToDto")
    AgendamentoDTO fromEntityToDto(Agendamento entity);

    @IterableMapping(qualifiedByName = "fromEntityToDto")
    @Named("fromEntityListToDtoList")
    List<AgendamentoDTO> fromEntityListToDtoList(List<Agendamento> agendamentos);
}
