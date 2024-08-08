package salao.online.application.resources;

import java.util.List;
import java.util.UUID;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import salao.online.application.dtos.dtosDoEstoque.CriarEstoqueDTO;
import salao.online.application.dtos.dtosDoEstoque.EstoqueDTO;
import salao.online.application.services.interfaces.EstoqueService;
import salao.online.domain.exceptions.ValidacaoException;

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

    @Operation(summary = "Buscando um estoque")
    @APIResponse(responseCode = "200", description = "Busca realizada com sucesso!")
    @APIResponse(responseCode = "404", description = "O estoque não foi encontrado")
    @GET
    @Path("/buscar/{id_estoque}")
    public Response buscarEstoquePorId(@PathParam("id_estoque") UUID idEstoque)
            throws ValidacaoException {
        try {
            LOG.info("Requisição recebida - Buscar o Estoque");
            EstoqueDTO clienteDTO = estoqueService.buscarEstoquePorId(idEstoque);
            return Response.status(200).entity(clienteDTO).build();
        } catch (ValidacaoException ex) {
            return Response.status(404).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            return Response.status(500).entity("Ocorreu um erro na requisição.").build();
        }
    }

    @Operation(summary = "Buscando todos os estoques de um profissional")
    @APIResponse(responseCode = "200", description = "Busca realizada com sucesso!")
    @APIResponse(responseCode = "500", description = "Erro no servidor")
    @GET
    @Path("/listar-estoques/{id_profissional}")
    public Response buscarEstoquesDoProfissional(@PathParam("id_profissional") UUID idProfissional) {
        try {
            LOG.info("Requisição recebida - listar todos os serviços de um profissional");
            List<EstoqueDTO> estoques = estoqueService.buscarEstoquesDoProfissional(idProfissional);
            return Response.status(200).entity(estoques).build();
        } catch (Exception ex) {
            return Response.status(500).entity("Ocorreu um erro na requisição.").build();
        }
    }
}
