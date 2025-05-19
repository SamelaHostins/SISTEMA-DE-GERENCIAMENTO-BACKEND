package salao.online.application.resources;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import io.smallrye.jwt.auth.principal.DefaultJWTCallerPrincipal;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import salao.online.application.dtos.dtosHorario.AtualizarHorariosTrabalhoDTO;
import salao.online.application.dtos.dtosHorario.HorarioTrabalhoDTO;
import salao.online.application.services.interfaces.HorarioTrabalhoService;
import salao.online.application.services.interfaces.ProfissionalService;
import salao.online.domain.exceptions.ValidacaoException;

@Path("/horario")
@Tag(name = "Endpoints de Horario Trabalho")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class HorarioTrabalhoResource {

    @Inject
    HorarioTrabalhoService horarioTrabalhoService;

    @Inject
    ProfissionalService profissionalService;

    @Inject
    JsonWebToken jwt;

    @GET
    @Path("/{id}/horarios-trabalho")
    @PermitAll
    @Operation(summary = "Listar os horários de trabalho de um profissional")
    @APIResponse(responseCode = "200", description = "Horários de trabalho retornados com sucesso")
    @APIResponse(responseCode = "204", description = "Profissional não possui horários cadastrados")
    @APIResponse(responseCode = "500", description = "Erro interno")
    public Response listarHorariosTrabalho(@PathParam("id") UUID idProfissional) {
        try {
            List<HorarioTrabalhoDTO> horarios = horarioTrabalhoService.listarHorariosDoProfissional(idProfissional);

            if (horarios == null || horarios.isEmpty()) {
                return Response.status(Response.Status.NO_CONTENT).build();
            }

            return Response.ok(horarios).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar horários de trabalho do profissional.")
                    .build();
        }
    }

    @PUT
    @Path("/atualizar")
    @RolesAllowed("PROFISSIONAL")
    @Transactional
    @Operation(summary = "Atualizar os horários de trabalho do profissional logado")
    public Response atualizarHorariosTrabalho(@Valid AtualizarHorariosTrabalhoDTO dto) {
        try {
            UUID idProfissional = UUID.fromString(jwt.getSubject());
            horarioTrabalhoService.atualizarHorariosTrabalho(idProfissional, dto.getHorarios());
            return Response.noContent().build();
        } catch (ValidacaoException ex) {
            return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao atualizar horários de trabalho.").build();
        }
    }

    @GET
    @Path("/{id}/horarios-disponiveis")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    @Operation(summary = "Buscar horários disponíveis para um profissional em uma data")
    @APIResponse(responseCode = "200", description = "Lista de horários disponíveis retornada com sucesso")
    @APIResponse(responseCode = "204", description = "O profissional não trabalha neste dia")
    @APIResponse(responseCode = "400", description = "Data não informada")
    @APIResponse(responseCode = "404", description = "Profissional não encontrado")
    @APIResponse(responseCode = "500", description = "Erro interno ao buscar horários disponíveis")
    public Response buscarHorariosDisponiveis(
            @PathParam("id") UUID idProfissional,
            @QueryParam("data") LocalDate data) throws ValidacaoException {

        // Validação: data obrigatória
        if (data == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Parâmetro 'data' é obrigatório.").build();
        }

        try {
            // O serviço já valida o profissional e lança exceção se não encontrado
            List<LocalTime> horariosDisponiveis = horarioTrabalhoService
                    .buscarHorariosDisponiveis(idProfissional, data);

            if (horariosDisponiveis == null) {
                // Retorno explícito do service indicando que o profissional não trabalha no dia
                return Response.status(Response.Status.NO_CONTENT).build(); // 204
            }

            return Response.ok(horariosDisponiveis).build();

        } catch (ValidacaoException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar horários disponíveis.").build();
        }
    }

    @Operation(summary = "Deletando Horario Trabalho")
    @DELETE
    @Path("deletar-horario/{idHorario}")
    @RolesAllowed("PROFISSIONAL")
    @Transactional
    public Response deletarHorarioDeTrabalho(@PathParam("idHorario") UUID idHorario,
            @Context SecurityContext securityContext) {
        UUID idProfissional = UUID
                .fromString(((DefaultJWTCallerPrincipal) securityContext.getUserPrincipal()).getName());
        horarioTrabalhoService.deletarHorarioDeTrabalho(idHorario, idProfissional);
        return Response.noContent().build();
    }
}
