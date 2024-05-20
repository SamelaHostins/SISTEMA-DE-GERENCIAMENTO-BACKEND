package salao.online.application.resources;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.*;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.oracle.svm.core.annotate.Inject;

import salao.online.application.dtos.dtosDoServico.ServicoDTO;
import salao.online.application.services.interfaces.ServicoService;

@Path("/servico")
@Tag(name = "Endpoints do Servico")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ServicoResource {

    @Inject
    ServicoService servicoService;

    private static final org.jboss.logging.Logger LOG = org.jboss.logging.Logger.getLogger(ServicoResource.class);


    @Operation(summary = "Listando todos os serviços de um profissional")
    @APIResponse(responseCode = "200", description = "Busca realizada com sucesso!")
    @APIResponse(responseCode = "500", description = "Erro no servidor")
    @GET
    @Path("/listar-servicos/{id_profissional}")
    public Response listarServicosDoProfissional(@PathParam("id_profissional") UUID idProfissional) {
        try {
            LOG.info("Requisição recebida - listar todos os serviços de um profissional");
            List<ServicoDTO> Servicos = servicoService.listarServicosDoProfissional(idProfissional);
            return Response.status(200).entity(Servicos).build();
        }  catch (Exception ex) {
            return Response.status(500).entity("Ocorreu um erro na requisição.").build();
        }
    }
}
