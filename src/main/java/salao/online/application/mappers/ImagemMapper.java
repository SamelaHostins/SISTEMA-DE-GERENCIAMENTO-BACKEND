package salao.online.application.mappers;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import salao.online.application.dtos.dtosDeImagem.ImagensDoClienteDTO;
import salao.online.application.dtos.dtosDeImagem.ImagensDoProfissionalDTO;
import salao.online.domain.entities.Imagem;

@Mapper(componentModel = "cdi")
public interface ImagemMapper {

    @Mapping(source = "profissional.idProfissional", target = "idProfissional")
    @Named("mapToDTO")
    ImagensDoProfissionalDTO toDtoImagemDoProfissional(Imagem entity);

    @IterableMapping(qualifiedByName = "mapToDTO")
    @Named("mapListToDtoList")
    List<ImagensDoProfissionalDTO> toDtoImagemDoProfissionalList(List<Imagem> Imagens);

    @Mapping(source = "cliente.idCliente", target = "idCliente")
    @Named("mapToDTO")
    ImagensDoClienteDTO toDtoImagemDoCliente(Imagem entity);

    @IterableMapping(qualifiedByName = "mapToDTO")
    @Named("mapListToDtoList")
    List<ImagensDoClienteDTO> toDtoImagemDoClienteList(List<Imagem> Imagens);
}
