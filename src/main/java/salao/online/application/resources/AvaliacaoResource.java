package salao.online.application.resources;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import salao.online.application.dtos.AvaliacaoDTO;
import salao.online.application.services.interfaces.AvaliacaoService;
import salao.online.domain.exceptions.ValidacaoException;

@Path("/avaliacao")
@Tag(name = "Endpoints da Avaliacao")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AvaliacaoResource {

    @Inject
    AvaliacaoService avaliacaoService;

    @Inject
    JsonWebToken jwt;

    private static final org.jboss.logging.Logger LOG = org.jboss.logging.Logger.getLogger(ClienteResource.class);

    @Operation(summary = "Inserindo uma Avaliação")
    @APIResponse(responseCode = "200", description = "Avaliação inserida com sucesso!")
    @APIResponse(responseCode = "400", description = "Ocorreu um erro ao inserir a avaliação")
    @APIResponse(responseCode = "500", description = "Erro no servidor")
    @POST
    @Path("/inserir")
    @RolesAllowed("CLIENTE")
    public Response inserirAvaliacao(@RequestBody AvaliacaoDTO dto) {
        try {
            LOG.info("Requisição recebida - Inserir Avaliação");
            AvaliacaoDTO avaliacaoDTO = avaliacaoService.inserirAvaliacao(dto);
            return Response.status(200).entity(avaliacaoDTO).build();
        } catch (Exception e) {
            return Response.status(500).entity("Ocorreu um erro na requisição.").build();
        }
    }

    @Operation(summary = "Atualizando uma Avaliação")
    @APIResponse(responseCode = "200", description = "Avaliação atualizada com sucesso!")
    @APIResponse(responseCode = "400", description = "Ocorreu um erro ao atualizar a avaliação")
    @APIResponse(responseCode = "500", description = "Erro no servidor")
    @PUT
    @Path("/atualizar")
    @RolesAllowed("CLIENTE")
    public Response atualizarAvaliacao(@RequestBody AvaliacaoDTO dto) {
        try {
            LOG.info("Requisição recebida - Atualizar avaliação");
            AvaliacaoDTO avaliacaoDTO = avaliacaoService.atualizarAvaliacao(dto);
            return Response.status(200).entity(avaliacaoDTO).build();
        } catch (Exception e) {
            return Response.status(500).entity("Ocorreu um erro na requisição.").build();
        }
    }

    @Operation(summary = "Buscar Média de Avaliações de um Serviço")
    @APIResponse(responseCode = "200", description = "Busca realizada com sucesso!")
    @APIResponse(responseCode = "404", description = "Não foi possível calcular a média.")
    @APIResponse(responseCode = "500", description = "Erro no servidor")
    @GET
    @Path("/{idServico}")
    @PermitAll
    public Response buscarMediaAvaliacoesServico(@PathParam("idServico") UUID idServico) {
        try {
            LOG.info("Requisição recebida - Buscar média");
            BigDecimal media = avaliacaoService.buscarMediaAvaliacoesServico(idServico);
            return Response.status(200).entity(media).build();
        } catch (ValidacaoException e) {
            return Response.status(400).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(500).entity("Ocorreu um erro na requisição.").build();
        }
    }

    @Operation(summary = "Busca a Avaliação do Cliente no Servico")
    @APIResponse(responseCode = "200", description = "Busca realizada com sucesso!")
    @APIResponse(responseCode = "404", description = "Erro ao tentar buscar a avaliação")
    @APIResponse(responseCode = "500", description = "Erro no servidor")
    @GET
    @Path("/buscar/{idCliente}/{idServico}")
    @PermitAll
    public Response buscarAvaliacaoDoClienteNoServico(@PathParam("idCliente") UUID idCliente,
            @PathParam("idServico") UUID idServico) {
        try {
            LOG.info("Requisição recebida - Buscar avaliação de um cliente num serviço");
            AvaliacaoDTO avaliacaoDTO = avaliacaoService.buscarAvaliacaoClienteServico(idCliente, idServico);
            return Response.status(200).entity(avaliacaoDTO).build();
        } catch (ValidacaoException e) {
            return Response.status(400).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(500).entity("Ocorreu um erro na requisição.").build();
        }
    }

    @Operation(summary = "Todas as Avaliações do Cliente logado", description = "Busca ordenada por data de avaliação, somente as avaliações do cliente autenticado.")
    @APIResponse(responseCode = "200", description = "Busca realizada com sucesso!")
    @APIResponse(responseCode = "404", description = "Não foi encontrada nenhuma avaliação.")
    @APIResponse(responseCode = "500", description = "Erro no servidor")
    @GET
    @Path("/listar-avaliacoes")
    @RolesAllowed("CLIENTE")
    public Response buscaAvaliacoesOrdenadasDoCliente(@Context JsonWebToken jwt) {
        try {
            LOG.info("Requisição recebida - listar avaliações do cliente logado");

            // Obtendo o ID do cliente a partir do JWT
            String idClienteStr = jwt.getSubject(); 
            UUID idCliente = UUID.fromString(idClienteStr);

            List<AvaliacaoDTO> avaliacoes = avaliacaoService.buscarAvaliacoesDoServico(idCliente);
            return Response.status(200).entity(avaliacoes).build();
        } catch (Exception e) {
            return Response.status(500).entity("Ocorreu um erro na requisição.").build();
        }
    }

}
