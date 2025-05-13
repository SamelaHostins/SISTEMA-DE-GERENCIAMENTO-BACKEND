package salao.online.application.resources;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import io.smallrye.jwt.auth.principal.DefaultJWTCallerPrincipal;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import salao.online.application.dtos.dtosDoAgendamento.AgendamentoDTO;
import salao.online.application.dtos.dtosDoAgendamento.CriarAgendamentoPeloClienteDTO;
import salao.online.application.dtos.dtosDoAgendamento.CriarAgendamentoPeloProfissionalDTO;
import salao.online.application.services.interfaces.AgendamentoService;
import salao.online.domain.exceptions.ValidacaoException;

@Path("/agendamentos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AgendamentoResource {

    @Inject
    AgendamentoService agendamentoService;

    @Operation(summary = "Criar agendamento para o cliente logado")
    @APIResponse(responseCode = "201", description = "Agendamento criado com sucesso")
    @APIResponse(responseCode = "400", description = "Dados inválidos ou conflito de horário")
    @POST
    @Transactional
    @RolesAllowed("CLIENTE")
    public Response criarAgendamentoPeloCliente(
            @Valid CriarAgendamentoPeloClienteDTO dto,
            @Context JsonWebToken jwt) {

        try {
            UUID idCliente = UUID.fromString(jwt.getSubject());
            var ag = agendamentoService.agendarComoCliente(idCliente, dto);
            return Response.status(Response.Status.CREATED)
                    .entity(ag).build();
        } catch (ValidacaoException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(ex.getMessage()).build();
        }
    }

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

    @Operation(summary = "Criar agendamento pelo profissional logado")
    @APIResponse(responseCode = "201", description = "Agendamento criado com sucesso")
    @APIResponse(responseCode = "400", description = "Dados inválidos ou conflito de horário")
    @POST
    @Path("/profissional")
    @Transactional
    @RolesAllowed("PROFISSIONAL")
    public Response criarAgendamentoPeloProfissional(
            @Valid CriarAgendamentoPeloProfissionalDTO dto,
            @Context JsonWebToken jwt) {

        try {
            UUID idProfissional = UUID.fromString(jwt.getSubject());
            var ag = agendamentoService.agendarComoProfissional(idProfissional, dto);
            return Response.status(Response.Status.CREATED)
                    .entity(ag).build();
        } catch (ValidacaoException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(ex.getMessage()).build();
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
