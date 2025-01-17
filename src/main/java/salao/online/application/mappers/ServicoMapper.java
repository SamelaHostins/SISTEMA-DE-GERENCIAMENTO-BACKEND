package salao.online.application.mappers;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import salao.online.application.dtos.dtosDoServico.AtualizarServicoDTO;
import salao.online.application.dtos.dtosDoServico.CriarServicoDTO;
import salao.online.application.dtos.dtosDoServico.ServicoDTO;
import salao.online.domain.entities.Servico;

@Mapper(componentModel = "cdi", uses = AvaliacaoMapper.class)
public interface ServicoMapper {

    @Mapping(target = "agendamentos", source = "agendamentos")
    Servico fromDtoToEntity(ServicoDTO dto);

    @Mapping(source = "profissional.idProfissional", target = "idProfissional")
    @Named("fromEntityToDto")
    ServicoDTO fromEntityToDto(Servico entity);

    CriarServicoDTO fromEntityToCriarDto(Servico entity);

    Servico fromCriarDtoToEntity(CriarServicoDTO dto);

    AtualizarServicoDTO fromEntityToAtualizarDto(Servico entity);

    @IterableMapping(qualifiedByName = "fromEntityToDto")
    @Named("fromEntityListToDtoList")
    List<ServicoDTO> fromEntityListToDtoList(List<Servico> servicos);
}
