package salao.online.application.resources;

import java.util.List;
import java.util.UUID;

import org.eclipse.microprofile.jwt.JsonWebToken;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import salao.online.application.dtos.PerguntaFrequenteDTO;
import salao.online.application.services.interfaces.PerguntaFrequenteService;

@Path("/faq")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PerguntaFrequenteResource {

    @Inject
    PerguntaFrequenteService perguntaService;

    @Inject
    JsonWebToken jwt;

    @POST
    @Path("/criar/")
    @RolesAllowed("PROFISSIONAL")
    @Transactional
    public Response adicionarPergunta(PerguntaFrequenteDTO dto) {
        UUID idProfissional = UUID.fromString(jwt.getSubject());
        PerguntaFrequenteDTO criada = perguntaService.adicionarPergunta(idProfissional, dto);
        return Response.status(Response.Status.CREATED).entity(criada).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("PROFISSIONAL")
    @Transactional
    public Response atualizarPergunta(
            @PathParam("id") UUID idPergunta,
            PerguntaFrequenteDTO dto) {

        UUID idProfissional = UUID.fromString(jwt.getSubject());
        PerguntaFrequenteDTO atualizada = perguntaService.atualizarPergunta(idPergunta, idProfissional, dto);
        return Response.ok(atualizada).build();
    }

    @GET
    @Path("/profissional/{id}")
    @PermitAll
    public Response listarPerguntasPorProfissional(@PathParam("id") UUID idProfissional) {
        List<PerguntaFrequenteDTO> lista = perguntaService.listarPerguntasPorProfissional(idProfissional);
        return Response.ok(lista).build();
    }

    @GET
    @Path("/profissional")
    @RolesAllowed("PROFISSIONAL")
    public Response listarPerguntasDoProfissionalAutenticado() {
        UUID idProfissional = UUID.fromString(jwt.getSubject());
        List<PerguntaFrequenteDTO> lista = perguntaService.listarPerguntasPorProfissional(idProfissional);
        return Response.ok(lista).build();
    }

    @DELETE
    @Path("/{idPergunta}")
    @RolesAllowed("PROFISSIONAL")
    @Transactional
    public Response deletarPergunta(
            @PathParam("idPergunta") UUID idPergunta) {

        UUID idProfissional = UUID.fromString(jwt.getSubject());
        perguntaService.deletarPergunta(idPergunta, idProfissional);
        return Response.noContent().build(); 
    }

}
