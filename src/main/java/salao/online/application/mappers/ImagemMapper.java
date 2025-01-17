package salao.online.application.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import salao.online.application.dtos.SalvarImagemDTO;
import salao.online.application.dtos.dtosDeImagem.ImagensDoClienteDTO;
import salao.online.application.dtos.dtosDeImagem.ImagensDoProfissionalDTO;
import salao.online.domain.entities.Imagem;

@Mapper(componentModel = "cdi")
public interface ImagemMapper {

    @Mapping(target = "profissional", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    Imagem toEntity(SalvarImagemDTO dto);

    // Se profissional não for nulo, o ID do profissional será usado.
    // Caso contrário, o ID do cliente será usado
    @Mapping(target = "ehProfissional", expression = "java(entity.getProfissional() != null)") // Atualizado
    @Mapping(target = "idUsuario", expression = "java(entity.getProfissional() != null ? entity.getProfissional().getProfissional() : entity.getCliente().getCliente())")
    @Mapping(target = "tipoImagem", ignore = true)
    SalvarImagemDTO toDto(Imagem entity);

    @Mapping(target = "idProfissional", source = "profissional.idProfissional")
    ImagensDoProfissionalDTO toProfissionalDto(Imagem entity);

    List<ImagensDoProfissionalDTO> toProfissionalDtoList(List<Imagem> imagens);

    @Mapping(target = "idCliente", source = "cliente.idCliente")
    ImagensDoClienteDTO toClienteDto(Imagem entity);

    List<ImagensDoClienteDTO> toClienteDtoList(List<Imagem> imagens);
}
