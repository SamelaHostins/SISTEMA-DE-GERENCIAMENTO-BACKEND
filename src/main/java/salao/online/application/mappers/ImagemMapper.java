package salao.online.application.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import salao.online.application.dtos.SalvarImagemDTO;
import salao.online.domain.entities.Imagem;

@Mapper(componentModel = "cdi")
public interface ImagemMapper {

    // Mapear SalvarImagemDTO para Imagem (DTO -> Entidade)
    @Mapping(source = "tipoImagem.tipoImagem", target = "tipoImagem")
    Imagem toEntity(SalvarImagemDTO dto);

    // Mapear Imagem para SalvarImagemDTO (Entidade -> DTO)
    @Mapping(source = "tipoImagem", target = "tipoImagem.tipoImagem")
    SalvarImagemDTO toDto(Imagem entity);
}
