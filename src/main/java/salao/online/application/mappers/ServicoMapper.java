package salao.online.application.mappers;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import salao.online.application.dtos.ServicoDTO;
import salao.online.domain.entities.Servico;

@Mapper(componentModel = "cdi")
public interface ServicoMapper {

    Servico toEntity(ServicoDTO dto);

    @Mapping(source = "profissional.idProfissional", target = "idProfissional")
    @Named("mapToDTO")
    ServicoDTO toDto(Servico entity);

    @IterableMapping(qualifiedByName = "mapToDTO")
    @Named("mapListToDtoList")
    List<ServicoDTO> toDtoList(List<Servico> servicos);
}
