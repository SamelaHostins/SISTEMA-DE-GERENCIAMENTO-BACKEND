package salao.online.application.resources;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import io.smallrye.jwt.auth.principal.DefaultJWTCallerPrincipal;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.WebApplicationException;
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

    @POST
    @Path("/cliente/agendar")
    @Operation(summary = "Criar agendamento para o cliente logado")
    @APIResponse(responseCode = "201", description = "Agendamento criado com sucesso")
    @APIResponse(responseCode = "400", description = "Dados inválidos ou conflito de horário")
    @Transactional
    @RolesAllowed("CLIENTE")
    public Response agendarPeloCliente(CriarAgendamentoPeloClienteDTO dto) {
        try {
            CriarAgendamentoPeloClienteDTO criado = agendamentoService.agendarPeloCliente(dto);
            return Response.status(Response.Status.CREATED).entity(criado).build();
        } catch (IllegalArgumentException e) {
            // Conflito por sobreposição
            return Response.status(Response.Status.CONFLICT)
                    .entity("Horário indisponível: " + e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao processar o agendamento.")
                    .build();
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
    @Path("/profissional/agendar")
    @Transactional
    @RolesAllowed("PROFISSIONAL")
    public Response criarAgendamentoPeloProfissional(@PathParam("idProfissional") UUID idProfissional,
            CriarAgendamentoPeloProfissionalDTO dto) {
        try {
            CriarAgendamentoPeloProfissionalDTO criado = agendamentoService.agendarComoProfissional(idProfissional,
                    dto);
            return Response.status(Response.Status.CREATED).entity(criado).build();
        } catch (ValidacaoException e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        } catch (WebApplicationException e) {
            return Response.status(e.getResponse().getStatus()).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno ao criar o agendamento.").build();
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

    @GET
    @Path("/profissional/{id}/horarios-disponiveis")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    @Operation(summary = "Buscar horários disponíveis para um profissional em uma data")
    @APIResponse(responseCode = "200", description = "Lista de horários disponíveis retornada com sucesso")
    @APIResponse(responseCode = "400", description = "Data não informada")
    @APIResponse(responseCode = "500", description = "Erro interno ao buscar horários disponíveis")
    public Response buscarHorariosDisponiveis(
            @PathParam("id") UUID idProfissional,
            @QueryParam("data") LocalDate data) {

        if (data == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Parâmetro 'data' é obrigatório.")
                    .build();
        }

        try {
            List<LocalTime> horariosDisponiveis = agendamentoService.buscarHorariosDisponiveis(idProfissional, data);
            return Response.ok(horariosDisponiveis).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar horários disponíveis.").build();
        }
    }

    @DELETE
    @Path("deletar/{id}")
    @RolesAllowed({ "CLIENTE", "PROFISSIONAL" })
    @Transactional
    public Response cancelarAgendamento(@PathParam("id") UUID idAgendamento,
            @Context SecurityContext securityContext) {
        UUID idUsuario = UUID.fromString(((DefaultJWTCallerPrincipal) securityContext.getUserPrincipal()).getName());
        boolean isProfissional = securityContext.isUserInRole("PROFISSIONAL");

        agendamentoService.cancelarAgendamento(idAgendamento, idUsuario, isProfissional);
        return Response.noContent().build(); // HTTP 204
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
