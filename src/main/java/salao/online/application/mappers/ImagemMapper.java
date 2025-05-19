package salao.online.application.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import salao.online.application.dtos.dtosDeImagem.ImagemDTO;
import salao.online.domain.entities.Imagem;

@Mapper(componentModel = "cdi")
public interface ImagemMapper {

    @Mapping(target = "idImagem", source = "idImagem")
    @Mapping(target = "profissional", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    Imagem fromDtoToEntity(ImagemDTO dto);

    @Mapping(target = "idImagem", source = "idImagem")
    @Mapping(target = "ehProfissional", expression = "java(entity.getProfissional() != null)")
    @Mapping(target = "idUsuario", expression = "java(entity.getProfissional() != null ? entity.getProfissional().getIdProfissional() : entity.getCliente().getIdCliente())")
    @Mapping(target = "tipoImagem", ignore = true)
    ImagemDTO fromEntityToDto(Imagem entity);

}
