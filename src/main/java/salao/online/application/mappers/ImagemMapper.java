package salao.online.application.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import salao.online.application.dtos.dtosDeImagem.ImagemDTO;
import salao.online.application.dtos.dtosDeImagem.ImagensDoClienteDTO;
import salao.online.application.dtos.dtosDeImagem.ImagensDoProfissionalDTO;
import salao.online.domain.entities.Imagem;

@Mapper(componentModel = "cdi")
public interface ImagemMapper {

    @Mapping(target = "profissional", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    Imagem fromDtoToEntity(ImagemDTO dto);

    @Mapping(target = "ehProfissional", expression = "java(entity.getProfissional() != null)")
    @Mapping(target = "idUsuario", expression = "java(entity.getProfissional() != null ? entity.getProfissional().getIdProfissional() : entity.getCliente().getIdCliente())")
    @Mapping(target = "tipoImagem", ignore = true)
    ImagemDTO fromEntityToDto(Imagem entity);

    ImagensDoProfissionalDTO fromEntityToProfissionalDto(Imagem entity);

    List<ImagensDoProfissionalDTO> fromEntityListToProfissionalDtoList(List<Imagem> imagens);

    ImagensDoClienteDTO fromEntityToClienteDto(Imagem entity);

    List<ImagensDoClienteDTO> fromEntityListToClienteDtoList(List<Imagem> imagens);
}
