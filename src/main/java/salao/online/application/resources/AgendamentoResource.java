package salao.online.application.resources;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import io.smallrye.jwt.auth.principal.JWTParser;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;
import salao.online.application.dtos.dtosDoAgendamento.AgendamentoDTO;
import salao.online.application.services.interfaces.AgendamentoService;

@Path("/agendamentos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AgendamentoResource {

    @Inject
    AgendamentoService agendamentoService;

    @Inject
    JWTParser jwtParser;

    @GET
    @Path("/cliente")
    @RolesAllowed("CLIENTE")
    public List<AgendamentoDTO> buscarAgendamentosDoCliente(
        @QueryParam("dataInicio") LocalDate dataInicio,
        @QueryParam("dataFim") LocalDate dataFim,
        @QueryParam("status") Integer status,
        @QueryParam("formaPagamento") Integer formaPagamento,
        @Context SecurityContext securityContext
    ) {
        var jwt = (io.smallrye.jwt.auth.principal.DefaultJWTCallerPrincipal) securityContext.getUserPrincipal();
        UUID idCliente = UUID.fromString(jwt.getClaim("sub"));
        return agendamentoService.buscarAgendamentosDoCliente(idCliente, dataInicio, dataFim, status, formaPagamento);
    }

    @GET
    @Path("/profissional")
    @RolesAllowed("PROFISSIONAL")
    public List<AgendamentoDTO> buscarAgendamentosDoProfissional(
        @QueryParam("dataInicio") LocalDate dataInicio,
        @QueryParam("dataFim") LocalDate dataFim,
        @QueryParam("status") Integer status,
        @QueryParam("formaPagamento") Integer formaPagamento,
        @Context SecurityContext securityContext
    ) {
        var jwt = (io.smallrye.jwt.auth.principal.DefaultJWTCallerPrincipal) securityContext.getUserPrincipal();
        UUID idProfissional = UUID.fromString(jwt.getClaim("sub"));
        return agendamentoService.buscarAgendamentosDoProfissional(idProfissional, dataInicio, dataFim, status, formaPagamento);
    }
}