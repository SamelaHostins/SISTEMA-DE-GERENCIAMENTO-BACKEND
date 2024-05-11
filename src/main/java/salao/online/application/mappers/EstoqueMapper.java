package salao.online.application.mappers;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import salao.online.application.dtos.EstoqueDTO;
import salao.online.domain.entities.Estoque;

@Mapper(componentModel = "cdi")
public interface EstoqueMapper {

    Estoque toEntity(EstoqueDTO dto);

    @Mapping(source = "profissional.idProfissional", target = "idProfissional")
    @Named("mapToDTO")
    EstoqueDTO toDto(Estoque entity);

    @IterableMapping(qualifiedByName = "mapToDTO")
    @Named("mapListToDtoList")
    List<EstoqueDTO> toDtoList(List<Estoque> estoques);
}
