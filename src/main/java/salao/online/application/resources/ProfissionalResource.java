package salao.online.application.resources;

import java.util.List;
import java.util.UUID;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.annotation.security.PermitAll;
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
import salao.online.application.dtos.dtosDeEndereco.AtualizarEnderecoDTO;
import salao.online.application.dtos.dtosDeEndereco.BuscarEnderecoDoProfissionalDTO;
import salao.online.application.dtos.dtosDoProfissional.AtualizarProfissionalDTO;
import salao.online.application.dtos.dtosDoProfissional.BuscarProfissionalAutenticadoDTO;
import salao.online.application.dtos.dtosDoProfissional.BuscarProfissionalDTO;
import salao.online.application.dtos.dtosDoProfissional.CriarProfissionalDTO;
import salao.online.application.dtos.dtosDoProfissional.ListarProfissionalDTO;
import salao.online.application.dtos.dtosDoProfissional.ListarProfissionalEmDestaqueDTO;
import salao.online.application.dtos.dtosParaPesquisar.PesquisaProfissionalDTO;
import salao.online.application.services.interfaces.ProfissionalService;
import salao.online.domain.exceptions.ValidacaoException;

@Path("/profissional")
@Tag(name = "Endpoints do Profissional")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProfissionalResource {

    @Inject
    ProfissionalService profissionalService;

    @Inject
    JsonWebToken jwt;

    private static final org.jboss.logging.Logger LOG = org.jboss.logging.Logger.getLogger(ProfissionalResource.class);

    @POST
    @Transactional
    @Path("/cadastrar")
    @PermitAll
    @Operation(summary = "Cadastrar Profissional")
    public Response cadastrarProfissional(@Valid @RequestBody CriarProfissionalDTO dto) {
        try {
            LOG.info("Requisição recebida - Cadastrar Profissional");
            CriarProfissionalDTO profissionalDTO = profissionalService.cadastrarProfissional(dto);
            return Response.ok(profissionalDTO).build();
        } catch (Exception ex) {
            LOG.error("Erro ao cadastrar profissional: {}", ex.getMessage(), ex);
            return Response.status(500).entity("Erro ao cadastrar profissional: " + ex.getMessage()).build();
        }
    }

    @GET
    @Path("/buscar/{id_profissional}")
    @PermitAll
    @Operation(summary = "Buscar dados públicos de um Profissional")
    public Response buscarProfissionalPorId(@PathParam("id_profissional") UUID idProfissional)
            throws ValidacaoException {
        try {
            LOG.info("Buscar Profissional por ID público");
            BuscarProfissionalDTO dto = profissionalService.buscarProfissionalPorId(idProfissional);
            return Response.ok(dto).build();
        } catch (ValidacaoException ex) {
            return Response.status(404).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            return Response.status(500).entity("Erro interno ao buscar profissional.").build();
        }
    }

    @GET
    @Path("/listar")
    @PermitAll
    @Operation(summary = "Listar todos os profissionais")
    public Response listarProfissionais() {
        try {
            LOG.info("Listando todos os profissionais");
            List<ListarProfissionalDTO> lista = profissionalService.listarTodosProfissionais();
            return Response.ok(lista).build();
        } catch (Exception ex) {
            return Response.status(500).entity("Erro ao listar profissionais.").build();
        }
    }

    @GET
    @Path("/pesquisar")
    @PermitAll
    @Operation(summary = "Pesquisar profissionais")
    public Response pesquisarProfissionais() {
        try {
            LOG.info("Pesquisando profissionais");
            List<PesquisaProfissionalDTO> lista = profissionalService.pesquisarTodosProfissionais();
            return Response.ok(lista).build();
        } catch (Exception ex) {
            return Response.status(500).entity("Erro ao pesquisar profissionais.").build();
        }
    }

    @GET
    @Path("/listar-dados")
    @RolesAllowed("PROFISSIONAL")
    @Operation(summary = "Listar dados do profissional logado")
    public Response listarProfissionalPorId() {
        try {
            UUID idProfissional = UUID.fromString(jwt.getSubject());
            ListarProfissionalDTO dto = profissionalService.listarProfissionalPorId(idProfissional);
            return Response.ok(dto).build();
        } catch (ValidacaoException ex) {
            return Response.status(404).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            return Response.status(500).entity("Erro ao listar profissional.").build();
        }
    }

    @GET
    @Path("/endereco")
    @RolesAllowed("PROFISSIONAL")
    @Operation(summary = "Buscar endereço do profissional logado")
    public Response BuscarEnderecoDoProfissionalDTO() {
        try {
            UUID idProfissional = UUID.fromString(jwt.getSubject());
            BuscarEnderecoDoProfissionalDTO dto = profissionalService.BuscarEnderecoDoProfissionalDTO(idProfissional);
            return Response.ok(dto).build();
        } catch (Exception ex) {
            return Response.status(500).entity("Erro ao buscar endereço.").build();
        }
    }

    @DELETE
    @Path("/deletar")
    @RolesAllowed("PROFISSIONAL")
    @Transactional
    @Operation(summary = "Deletar cadastro do profissional logado")
    public Response deletarProfissional() {
        try {
            UUID idProfissional = UUID.fromString(jwt.getSubject());
            profissionalService.deletarProfissional(idProfissional);
            return Response.noContent().build();
        } catch (ValidacaoException ex) {
            return Response.status(404).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            return Response.status(500).entity("Erro ao deletar profissional.").build();
        }
    }

    @PUT
    @Path("/atualizar")
    @RolesAllowed("PROFISSIONAL")
    @Transactional
    @Operation(summary = "Atualizar cadastro do profissional logado")
    public Response atualizarProfissional(@RequestBody AtualizarProfissionalDTO dto) {
        try {
            UUID idProfissional = UUID.fromString(jwt.getSubject());
            dto.setIdProfissional(idProfissional);
            AtualizarProfissionalDTO atualizado = profissionalService.atualizarProfissional(dto);
            return Response.ok(atualizado).build();
        } catch (ValidacaoException ex) {
            return Response.status(404).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            return Response.status(500).entity("Erro ao atualizar profissional.").build();
        }
    }

    @PUT
    @Path("/endereco/atualizar")
    @RolesAllowed("PROFISSIONAL")
    @Transactional
    @Operation(summary = "Atualizar endereço do profissional logado")
    public Response atualizarEnderecoProfissional(@RequestBody AtualizarEnderecoDTO dto) {
        try {
            UUID idProfissional = UUID.fromString(jwt.getSubject());
            profissionalService.atualizarEndereco(idProfissional, dto);
            return Response.noContent().build();
        } catch (ValidacaoException ex) {
            return Response.status(404).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            return Response.status(500).entity("Erro ao atualizar endereço.").build();
        }
    }

    @GET
    @Path("/autenticado")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("PROFISSIONAL")
    public Response buscarAutenticado(@Context JsonWebToken jwt) {
        try {
            // extrai o UUID do profissional do subject do JWT
            UUID idProfissional = UUID.fromString(jwt.getSubject());

            // chama o serviço já adaptado para receber o UUID
            BuscarProfissionalAutenticadoDTO dto = profissionalService.buscarProfissionalAutenticado(idProfissional);

            return Response.ok(dto).build();
        } catch (ValidacaoException ex) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(ex.getMessage())
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Token inválido.")
                    .build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar profissional.")
                    .build();
        }
    }

    @GET
    @PermitAll
    public Response listarProfissionaisEmDestaque() {
        List<ListarProfissionalEmDestaqueDTO> profissionais = profissionalService.listarProfissionaisEmDestaque();
        return Response.ok(profissionais).build();
    }

}
