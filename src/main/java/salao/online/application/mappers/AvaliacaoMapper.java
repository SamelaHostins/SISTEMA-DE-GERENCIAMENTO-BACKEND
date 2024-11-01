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

    @InheritInverseConfiguration
    Avaliacao toEntity(AvaliacaoDTO dto);

    @Mapping(source = "cliente.idCliente", target = "idCliente")
    @Mapping(source = "servico.idServico", target = "idServico")
    @Named("mapToDTO")
    AvaliacaoDTO toDto(Avaliacao entity);

    @IterableMapping(qualifiedByName = "mapToDTO")
    @Named("mapListToDtoList")
    List<AvaliacaoDTO> toDtoList(List<Avaliacao> avaliacoes);
}
