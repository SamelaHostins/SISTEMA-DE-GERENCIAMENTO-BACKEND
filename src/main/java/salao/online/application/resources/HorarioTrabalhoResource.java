package salao.online.application.resources;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import io.smallrye.jwt.auth.principal.DefaultJWTCallerPrincipal;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import salao.online.application.services.interfaces.HorarioTrabalhoService;

@Path("/horario")
@Tag(name = "Endpoints de Horario Trabalho")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class HorarioTrabalhoResource {

    @Inject
    HorarioTrabalhoService horarioTrabalhoService;

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
            List<LocalTime> horariosDisponiveis = horarioTrabalhoService.buscarHorariosDisponiveis(idProfissional,
                    data);
            return Response.ok(horariosDisponiveis).build();
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
