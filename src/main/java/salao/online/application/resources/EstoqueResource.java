package salao.online.application.resources;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import salao.online.application.dtos.dtosDoEstoque.CriarEstoqueDTO;
import salao.online.application.services.interfaces.EstoqueService;

@Path("/estoque")
@Tag(name = "Endpoints do estoque")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EstoqueResource {

    @Inject
    EstoqueService estoqueService;

    private static final org.jboss.logging.Logger LOG = org.jboss.logging.Logger.getLogger(EstoqueResource.class);

    @Operation(summary = "Cadastrando um Estoque")
    @APIResponse(responseCode = "200", description = "Estoque criado com sucesso!")
    @APIResponse(responseCode = "500", description = "Ocorreu um erro na requisição.")
    @POST
    @Transactional
    @Path("/cadastrar")
    public Response cadastrarEstoque(@Valid @RequestBody CriarEstoqueDTO dto) {
        try {
            LOG.info("Requisição recebida - Cadastrar Profissional");
            CriarEstoqueDTO estoqueDTO = estoqueService.cadastrarEstoque(dto);
            return Response.status(200).entity(estoqueDTO).build();
        } catch (Exception ex) {
            return Response.status(500).entity("Ocorreu um erro na requisição.").build();
        }
    }
}
