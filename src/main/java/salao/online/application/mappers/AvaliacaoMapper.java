package salao.online.application.mappers;

import salao.online.application.dtos.AvaliacaoDTO;
import salao.online.domain.entities.Avaliacao;

import java.util.List;

import org.mapstruct.Named;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface AvaliacaoMapper {

    @Mapping(source = "cliente.idCliente", target = "idCliente")
    @Mapping(source = "servico.idServico", target = "idServico")
    @Mapping(source = "idAvaliacao", target = "idAvaliacao")
    @Named("fromEntityToDto")
    AvaliacaoDTO fromEntityToDto(Avaliacao entity);

    @InheritInverseConfiguration
    @Mapping(target = "atualizarAvaliacao", ignore = true)
    Avaliacao fromDtoToEntity(AvaliacaoDTO dto);

    @IterableMapping(qualifiedByName = "fromEntityToDto")
    @Named("fromEntityListToDtoList")
    List<AvaliacaoDTO> fromEntityListToDtoList(List<Avaliacao> avaliacoes);
}
