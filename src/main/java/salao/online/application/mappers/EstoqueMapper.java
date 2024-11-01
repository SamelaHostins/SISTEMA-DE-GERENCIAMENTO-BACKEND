package salao.online.application.mappers;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import salao.online.application.dtos.dtosDoEstoque.CriarEstoqueDTO;
import salao.online.application.dtos.dtosDoEstoque.EstoqueDTO;
import salao.online.domain.entities.Estoque;

@Mapper(componentModel = "cdi", uses = ProdutoMapper.class)
public interface EstoqueMapper {

    @InheritInverseConfiguration
    Estoque toEntity(EstoqueDTO dto);

    @Mapping(source = "profissional.idProfissional", target = "idProfissional")
    @Named("mapToDTO")
    EstoqueDTO toDto(Estoque entity);

    CriarEstoqueDTO toCriarDto(Estoque dto);

    Estoque criarDtoToEntity (CriarEstoqueDTO dto);

    @IterableMapping(qualifiedByName = "mapToDTO")
    @Named("mapListToDtoList")
    List<EstoqueDTO> toDtoList(List<Estoque> estoques);
}
