package salao.online.application.resources;

import java.util.List;
import java.util.UUID;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
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

    @POST
    @Transactional
    @Path("/cadastrar")
    @RolesAllowed("PROFISSIONAL")
    public Response cadastrarEstoque(@Valid @RequestBody CriarEstoqueDTO dto,
            @Context SecurityContext securityContext) {
        try {
            LOG.info("Requisição recebida - Cadastrar Estoque");

            UUID idProfissional = UUID.fromString(securityContext.getUserPrincipal().getName());
            dto.setIdProfissional(idProfissional); 

            CriarEstoqueDTO estoqueDTO = estoqueService.cadastrarEstoque(dto);
            return Response.ok(estoqueDTO).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(500).entity("Ocorreu um erro na requisição.").build();
        }
    }

    @Operation(summary = "Editar um estoque")
    @APIResponse(responseCode = "200", description = "Estoque atualizado com sucesso")
    @APIResponse(responseCode = "404", description = "Estoque não encontrado")
    @APIResponse(responseCode = "500", description = "Erro interno do servidor")
    @PUT
    @Path("/editar")
    @Transactional
    @RolesAllowed("PROFISSIONAL")
    public Response editarEstoque(@Valid @RequestBody EstoqueDTO dto, @Context SecurityContext securityContext) {
        try {
            UUID idProfissional = UUID.fromString(securityContext.getUserPrincipal().getName());
            EstoqueDTO estoqueAtualizado = estoqueService.atualizarEstoque(dto, idProfissional);
            return Response.ok(estoqueAtualizado).build();
        } catch (ValidacaoException ex) {
            return Response.status(404).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            return Response.status(500).entity("Erro interno ao atualizar o estoque.").build();
        }
    }

    @Operation(summary = "Buscar estoque por ID")
    @APIResponse(responseCode = "200", description = "Estoque encontrado")
    @APIResponse(responseCode = "404", description = "Estoque não encontrado")
    @GET
    @RolesAllowed("PROFISSIONAL")
    @Path("/buscar/{id_estoque}")
    public Response buscarEstoquePorId(@PathParam("id_estoque") UUID idEstoque)
            throws ValidacaoException {
        try {
            LOG.info("Requisição recebida - Buscar Estoque por ID");
            EstoqueDTO estoqueDTO = estoqueService.buscarEstoquePorId(idEstoque);
            return Response.ok(estoqueDTO).build();
        } catch (ValidacaoException ex) {
            return Response.status(404).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            return Response.status(500).entity("Erro interno no servidor.").build();
        }
    }

    @Operation(summary = "Listar estoques do profissional autenticado")
    @APIResponse(responseCode = "200", description = "Estoques retornados com sucesso")
    @GET
    @RolesAllowed("PROFISSIONAL")
    @Path("/listar-estoques")
    public Response buscarEstoquesDoProfissional(@Context SecurityContext securityContext) {
        try {
            UUID idProfissional = UUID.fromString(securityContext.getUserPrincipal().getName());
            List<EstoqueDTO> estoques = estoqueService.buscarEstoquesDoProfissional(idProfissional);
            return Response.ok(estoques).build();
        } catch (Exception ex) {
            return Response.status(500).entity("Erro interno ao buscar estoques.").build();
        }
    }

    @DELETE
    @Path("/deletar/{id_estoque}")
    @RolesAllowed("PROFISSIONAL")
    @Transactional
    @Operation(summary = "Deletar um estoque e seus produtos")
    @APIResponse(responseCode = "204", description = "Estoque deletado com sucesso")
    @APIResponse(responseCode = "403", description = "Acesso negado")
    @APIResponse(responseCode = "404", description = "Estoque não encontrado")
    public Response deletarEstoque(@PathParam("id_estoque") UUID idEstoque, @Context SecurityContext securityContext) {
        UUID idProfissional = UUID.fromString(securityContext.getUserPrincipal().getName());
        estoqueService.deletarEstoque(idEstoque, idProfissional);
        return Response.noContent().build();
    }

}
