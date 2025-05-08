package salao.online.application.resources;

import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import salao.online.application.dtos.dtosParaSeAutenticar.LoginDTO;
import salao.online.application.dtos.dtosParaSeAutenticar.TokenDTO;
import salao.online.application.mappers.AuthMapper;
import salao.online.application.services.interfaces.AuthService;

@Path("/auth")
@Tag(name = "Endpoints da Autenticação")
public class AuthResource {

    @Inject
    AuthMapper authMapper;

    @Inject
    AuthService authService;

    @POST
    @Path("/login")
    @PermitAll
    public Response login(LoginDTO dto) {
        return authService.autenticar(dto.email, dto.senha)
                .map(auth -> {
                    TokenDTO tokenDTO = authMapper.mapToken(auth.token, auth.tipoUsuario);
                    return Response.ok(tokenDTO).build();
                })
                .orElse(Response.status(Response.Status.UNAUTHORIZED).build());
    }
}
