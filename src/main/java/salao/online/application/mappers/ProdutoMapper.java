package salao.online.application.mappers;

import salao.online.application.dtos.dtosDeProduto.CriarProdutoDTO;
import salao.online.application.dtos.dtosDeProduto.ProdutoDTO;
import salao.online.domain.entities.Produto;

import java.util.List;

import org.mapstruct.Named;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface ProdutoMapper {

    @InheritInverseConfiguration
    Produto toEntity(ProdutoDTO dto);

    @Mapping(source = "estoque.idEstoque", target = "idEstoque")
    @Named("mapToDTO")
    ProdutoDTO toDto(Produto entity);

    CriarProdutoDTO toCriarDto(Produto entity);

    Produto criarDtoToEntity(CriarProdutoDTO dto);

    @IterableMapping(qualifiedByName = "mapToDTO")
    @Named("mapListToDtoList")
    List<ProdutoDTO> toDtoList(List<Produto> produtos);
}
