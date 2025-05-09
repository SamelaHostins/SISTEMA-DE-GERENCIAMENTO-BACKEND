package salao.online.application.resources;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import io.smallrye.jwt.auth.principal.DefaultJWTCallerPrincipal;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import salao.online.application.dtos.dtosDoAgendamento.AgendamentoDTO;
import salao.online.application.services.interfaces.AgendamentoService;
import salao.online.domain.exceptions.ValidacaoException;

@Path("/agendamentos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AgendamentoResource {

    @Inject
    AgendamentoService agendamentoService;

    @GET
    @Path("/cliente")
    @RolesAllowed("CLIENTE")
    public Response buscarAgendamentosDoCliente(
            @QueryParam("dataInicio") LocalDate dataInicio,
            @QueryParam("dataFim") LocalDate dataFim,
            @QueryParam("status") Integer status,
            @QueryParam("formaPagamento") Integer formaPagamento,
            @Context SecurityContext securityContext) {
        try {
            UUID idCliente = resolveClienteId(securityContext);
            List<AgendamentoDTO> lista = agendamentoService.buscarAgendamentosDoCliente(
                    idCliente, dataInicio, dataFim, status, formaPagamento);
            return Response.ok(lista).build();
        } catch (ValidacaoException ex) {
            return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro ao buscar agendamentos do cliente.")
                           .build();
        }
    }

    @GET
    @Path("/profissional")
    @RolesAllowed("PROFISSIONAL")
    public Response buscarAgendamentosDoProfissional(
            @QueryParam("dataInicio") LocalDate dataInicio,
            @QueryParam("dataFim") LocalDate dataFim,
            @QueryParam("status") Integer status,
            @QueryParam("formaPagamento") Integer formaPagamento,
            @Context SecurityContext securityContext) {
        try {
            UUID idProfissional = resolveProfissionalId(securityContext);
            List<AgendamentoDTO> lista = agendamentoService.buscarAgendamentosDoProfissional(
                    idProfissional, dataInicio, dataFim, status, formaPagamento);
            return Response.ok(lista).build();
        } catch (ValidacaoException ex) {
            return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro ao buscar agendamentos do profissional.")
                           .build();
        }
    }

    private UUID resolveClienteId(SecurityContext securityContext) throws ValidacaoException {
        var principal = (DefaultJWTCallerPrincipal) securityContext.getUserPrincipal();
        String sub = principal.getName(); // retorna o subject do token, que agora é o UUID
        try {
            return UUID.fromString(sub);
        } catch (IllegalArgumentException e) {
            throw new ValidacaoException("Token inválido: não foi possível extrair o ID do cliente.");
        }
    }

    private UUID resolveProfissionalId(SecurityContext securityContext) throws ValidacaoException {
        var principal = (DefaultJWTCallerPrincipal) securityContext.getUserPrincipal();
        String sub = principal.getName();
        try {
            return UUID.fromString(sub);
        } catch (IllegalArgumentException e) {
            throw new ValidacaoException("Token inválido: não foi possível extrair o ID do profissional.");
        }
    }
}
