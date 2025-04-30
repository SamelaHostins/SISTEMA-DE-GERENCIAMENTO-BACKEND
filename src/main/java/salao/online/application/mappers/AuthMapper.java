package salao.online.application.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import salao.online.application.dtos.dtosParaSeAutenticar.TipoUsuarioEnumDTO;
import salao.online.application.dtos.dtosParaSeAutenticar.TokenDTO;
import salao.online.domain.enums.TipoUsuarioEnum;

@Mapper(componentModel = "cdi")
public interface AuthMapper {
    TipoUsuarioEnumDTO toTipoUsuarioEnumDTO(TipoUsuarioEnum tipo);

    @Mapping(target = "tipoUsuario", expression = "java(mapper.toTipoUsuarioEnumDTO(tipoUsuario))")
    TokenDTO toTokenDTO(String token, TipoUsuarioEnum tipoUsuario);

    // É necessário declarar o mapper no expression acima para evitar referência
    // circular
    default TokenDTO mapToken(String token, TipoUsuarioEnum tipoUsuario) {
        TokenDTO dto = new TokenDTO();
        dto.token = token;
        dto.tipoUsuario = toTipoUsuarioEnumDTO(tipoUsuario);
        return dto;
    }
}
