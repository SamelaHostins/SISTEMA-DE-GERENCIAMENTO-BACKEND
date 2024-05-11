package salao.online.application.mappers;

import salao.online.application.dtos.ProdutoDTO;
import salao.online.domain.entities.Produto;

import java.util.List;

import org.mapstruct.Named;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface ProdutoMapper {

    Produto toEntity(ProdutoDTO dto);

    @Mapping(source = "estoque.idEstoque", target = "idEstoque")
    @Named("mapToDTO")
    ProdutoDTO toDto(Produto entity);

    @IterableMapping(qualifiedByName = "mapToDTO")
    @Named("mapListToDtoList")
    List<ProdutoDTO> toDtoList(List<Produto> produtos);
}
