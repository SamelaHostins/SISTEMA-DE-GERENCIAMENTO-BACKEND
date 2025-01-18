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

    @Mapping(source = "estoque.idEstoque", target = "idEstoque")
    @Named("fromEntityToDto")
    ProdutoDTO fromEntityToDto(Produto entity);

    @InheritInverseConfiguration
    Produto fromDtoToEntity(ProdutoDTO dto);

    @Mapping(source = "estoque.idEstoque", target = "idEstoque")
    CriarProdutoDTO fromEntityToCriarDto(Produto entity);

    @InheritInverseConfiguration
    Produto fromCriarDtoToEntity(CriarProdutoDTO dto);

    @IterableMapping(qualifiedByName = "fromEntityToDto")
    @Named("fromEntityListToDtoList")
    List<ProdutoDTO> fromEntityListToDtoList(List<Produto> produtos);
}
