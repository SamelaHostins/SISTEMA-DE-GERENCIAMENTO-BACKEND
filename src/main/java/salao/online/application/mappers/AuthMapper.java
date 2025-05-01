package salao.online.application.mappers;

import org.mapstruct.Mapper;

import salao.online.application.dtos.dtosParaSeAutenticar.TipoUsuarioEnumDTO;
import salao.online.application.dtos.dtosParaSeAutenticar.TokenDTO;
import salao.online.domain.enums.TipoUsuarioEnum;

@Mapper(componentModel = "cdi")
public interface AuthMapper {

    // Mapeia o enum de domínio para o enum DTO (CLIENTE/PROFISSIONAL)
    TipoUsuarioEnumDTO toTipoUsuarioEnumDTO(TipoUsuarioEnum tipo);

    // Método auxiliar manual para construir o TokenDTO a partir do token e tipo do
    // usuário
    default TokenDTO mapToken(String token, TipoUsuarioEnum tipoUsuario) {
        TokenDTO dto = new TokenDTO();
        dto.token = token;
        dto.tipoUsuario = toTipoUsuarioEnumDTO(tipoUsuario);
        return dto;
    }
}
