package salao.online.application.mappers;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import salao.online.application.dtos.ImagemDTO;
import salao.online.domain.entities.Imagem;

@Mapper(componentModel = "cdi")
public interface ImagemMapper {

    @InheritInverseConfiguration
    Imagem toEntity(ImagemDTO dto);

    @Named("mapToDTO")
    ImagemDTO toDto(Imagem entity);

    @IterableMapping(qualifiedByName = "mapToDTO")
    @Named("mapListToDtoList")
    List<ImagemDTO> toDtoList(List<Imagem> Imagens);
}
