package salao.online.application.resources;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.*;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.oracle.svm.core.annotate.Inject;

import salao.online.application.dtos.TipoServicoEnumDTO;
import salao.online.application.dtos.dtosDoServico.ServicoDTO;
import salao.online.application.services.interfaces.ServicoService;
import salao.online.domain.exceptions.ValidacaoException;

@Path("/servico")
@Tag(name = "Endpoints do Servico")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ServicoResource {

    @Inject
    ServicoService servicoService;

    private static final org.jboss.logging.Logger LOG = org.jboss.logging.Logger.getLogger(ServicoResource.class);

    @Operation(summary = "Listando os serviços de um profissional", description = "Lista os serviços normais se não indicado nenhum tipo de serviço ou indicado "
            +
            "o tipo de serviço normal. Se indicado o tipo de serviço especial, listará os serviços especiais.")
    @APIResponse(responseCode = "200", description = "Busca realizada com sucesso!")
    @APIResponse(responseCode = "500", description = "Erro no servidor")
    @GET
    @Path("/listar-servicos/{id_profissional}/{tipo_servico}")
    public Response listarServicosDoProfissional(
            @PathParam("id_profissional") UUID idProfissional,
            @QueryParam("tipo_servico") TipoServicoEnumDTO tipoServico) {

        try {
            LOG.info("Requisição recebida - listar os serviços de um profissional");
            List<ServicoDTO> servicos = servicoService.listarServicosDoProfissional(idProfissional, tipoServico);
            return Response.status(200).entity(servicos).build();
        } catch (Exception ex) {
            return Response.status(500).entity("Ocorreu um erro na requisição.").build();
        }
    }

    @Operation(summary = "Listando todos os serviços de um profissional", description = "Lista tanto os serviços especiais quanto os normais.")
    @APIResponse(responseCode = "200", description = "Busca realizada com sucesso!")
    @APIResponse(responseCode = "500", description = "Erro no servidor")
    @GET
    @Path("/todos-os-servicos/{id_profissional}")
    public Response listarTodosOsServicosDoProfissional(
            @PathParam("id_profissional") UUID idProfissional) {

        try {
            LOG.info("Requisição recebida - listar todos os serviços de um profissional");
            List<ServicoDTO> servicos = servicoService.listarTodosOsServicosDoProfissional(idProfissional);
            return Response.status(200).entity(servicos).build();
        } catch (Exception ex) {
            return Response.status(500).entity("Ocorreu um erro na requisição.").build();
        }
    }

    @Operation(summary = "Deletando o cadastro de um servico")
    @APIResponse(responseCode = "200", description = "Cadastro deletado com sucesso!")
    @APIResponse(responseCode = "404", description = "O servico não foi encontrado")
    @DELETE
    @Transactional
    @Path("/deletar/{id_servico}")
    public Response deletarCadastroservico(@PathParam("id_servico") UUID idservico)
            throws ValidacaoException {
        try {
            LOG.info("Requisição recebida - Deletar o cadastro do servico");
            servicoService.deletarCadastroServico(idservico);
            return Response.status(200).build();
        } catch (ValidacaoException ex) {
            return Response.status(404).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            return Response.status(500).entity("Ocorreu um erro na requisição.").build();
        }
    }
}
