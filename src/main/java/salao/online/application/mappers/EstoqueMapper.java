package salao.online.application.mappers;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import salao.online.application.dtos.dtosDoEstoque.CriarEstoqueDTO;
import salao.online.application.dtos.dtosDoEstoque.EstoqueDTO;
import salao.online.domain.entities.Estoque;

@Mapper(componentModel = "cdi", uses = ProdutoMapper.class)
public interface EstoqueMapper {

    Estoque fromDtoToEntity(EstoqueDTO dto);

    @Mapping(source = "idEstoque", target = "idEstoque")
    @Mapping(source = "nome", target = "nome")
    @Mapping(source = "produtos", target = "produtos")
    @Mapping(source = "profissional.idProfissional", target = "idProfissional")
    @Mapping(expression = "java(entity.getProdutos() != null ? entity.getProdutos().size() : 0)", target = "qtdDeProdutos")
    @Named("fromEntityToDto")
    EstoqueDTO fromEntityToDto(Estoque entity);

    CriarEstoqueDTO fromEntityToCriarDto(Estoque entity);

    Estoque fromCriarDtoToEntity(CriarEstoqueDTO dto);

    @IterableMapping(qualifiedByName = "fromEntityToDto")
    @Named("fromEntityListToDtoList")
    List<EstoqueDTO> fromEntityListToDtoList(List<Estoque> estoques);
}

