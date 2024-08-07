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
import salao.online.application.dtos.dtosDeProduto.CriarProdutoDTO;
import salao.online.application.dtos.dtosDeProduto.ProdutoDTO;
import salao.online.application.services.interfaces.ProdutoService;
import salao.online.domain.exceptions.ValidacaoException;

@Path("/produto")
@Tag(name = "Endpoints do produto")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProdutoResource {

    @Inject
    ProdutoService produtoService;

    private static final org.jboss.logging.Logger LOG = org.jboss.logging.Logger.getLogger(ProdutoResource.class);

    @Operation(summary = "Cadastrando um Produto")
    @APIResponse(responseCode = "200", description = "Produto criado com sucesso!")
    @APIResponse(responseCode = "500", description = "Ocorreu um erro na requisição.")
    @POST
    @Transactional
    @Path("/cadastrar")
    public Response cadastrarProduto(@Valid @RequestBody CriarProdutoDTO dto) {
        try {
            LOG.info("Requisição recebida - Cadastrar Profissional");
            CriarProdutoDTO produtoDTO = produtoService.cadastrarProduto(dto);
            return Response.status(200).entity(produtoDTO).build();
        } catch (Exception ex) {
            return Response.status(500).entity("Ocorreu um erro na requisição.").build();
        }
    }

    @Operation(summary = "Buscando um produto")
    @APIResponse(responseCode = "200", description = "Busca realizada com sucesso!")
    @APIResponse(responseCode = "404", description = "O produto não foi encontrado")
    @GET
    @Path("/buscar/{id_produto}")
    public Response buscarProdutoPorId(@PathParam("id_produto") UUID idProduto)
            throws ValidacaoException {
        try {
            LOG.info("Requisição recebida - Buscar o Produto");
            ProdutoDTO produtoDTO = produtoService.buscarProdutoPorId(idProduto);
            return Response.status(200).entity(produtoDTO).build();
        } catch (ValidacaoException ex) {
            return Response.status(404).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            return Response.status(500).entity("Ocorreu um erro na requisição.").build();
        }
    }

    @Operation(summary = "Listando os produtos de um estoque")
    @APIResponse(responseCode = "200", description = "Busca realizada com sucesso!")
    @APIResponse(responseCode = "500", description = "Erro no servidor")
    @GET
    @Path("/listar-produtos/{id_estoque}")
    public Response listarProdutosDoEstoque(@PathParam("id_estoque") UUID idEstoque) {
        try {
            LOG.info("Requisição recebida - listar os produtos de um estoque");
            List<ProdutoDTO> produtos = produtoService.listarProdutosDoEstoque(idEstoque);
            return Response.status(200).entity(produtos).build();
        } catch (Exception ex) {
            return Response.status(500).entity("Ocorreu um erro na requisição.").build();
        }
    }

}
