package salao.online.application.resources;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestForm;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import salao.online.application.dtos.dtosDeImagem.ImagemDTO;
import salao.online.application.services.interfaces.ImagemService;

@Path("/imagem")
@Tag(name = "Endpoints do Cloudinary - Imagem")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ImagemResource {

    @Inject
    ImagemService imagemService;

    private static final org.jboss.logging.Logger LOG = org.jboss.logging.Logger.getLogger(ImagemResource.class);

    @POST
    @Transactional
    @Path("/upload/{tipoImagem}/{idUsuario}/{ehProfissional}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadImagem(
            @RestForm("imageBytes") InputStream imageBytes,
            @PathParam("tipoImagem") int tipoImagem,
            @PathParam("idUsuario") UUID idUsuario,
            @PathParam("ehProfissional") boolean ehProfissional) {
        try {
            if (imageBytes == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Parâmetro inválido: certifique-se de fornecer a imagem.")
                        .build();
            }

            // Chama o serviço para fazer o upload da imagem
            String url = imagemService.uploadImagem(imageBytes, tipoImagem, idUsuario, ehProfissional);

            if (url != null) {
                LOG.info("Upload realizado com sucesso. URL: {}" + url);
                return Response.ok(Map.of("url", url)).build();
            } else {
                LOG.error("Falha ao realizar o upload da imagem.");
                return Response.status(Response.Status.BAD_REQUEST).entity("Falha ao realizar o upload da imagem.")
                        .build();
            }
        } catch (IllegalArgumentException e) {
            LOG.error("Erro de validação: ", e);
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            LOG.error("Erro ao fazer upload da imagem: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao realizar o upload da imagem.")
                    .build();
        }
    }

    @Operation(summary = "Excluir imagem", description = "Exclui uma imagem do Cloudinary e do banco de dados.")
    @APIResponse(responseCode = "200", description = "Imagem excluída com sucesso.")
    @APIResponse(responseCode = "404", description = "Imagem não encontrada.")
    @APIResponse(responseCode = "500", description = "Erro ao processar a requisição.")
    @DELETE
    @Transactional
    @Path("/excluir/{idImagem}")
    public Response excluirImagem(@PathParam("idImagem") UUID idImagem) {
        try {
            LOG.info("Requisição recebida - Excluir imagem com ID: {}" + idImagem);
            imagemService.excluirImagem(idImagem);
            return Response.status(200).entity("Imagem excluída com sucesso.").build();
        } catch (IllegalArgumentException e) {
            LOG.error("Erro ao excluir imagem: {}" + e.getMessage());
            return Response.status(404).entity(e.getMessage()).build();
        } catch (Exception ex) {
            LOG.error("Erro inesperado ao excluir imagem: {}", ex.getMessage(), ex);
            return Response.status(500).entity("Erro ao excluir imagem.").build();
        }
    }

    @GET
    @Path("/perfil/{idUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarImagemDePerfil(@PathParam("idUsuario") UUID idUsuario) {
        ImagemDTO imagem = imagemService.buscarImagemDePerfil(idUsuario);

        if (imagem == null) {
            return Response.status(Response.Status.NO_CONTENT).build(); // Retorna 204 se não houver imagem
        }

        return Response.ok(imagem).build();
    }

    @GET
    @Path("/portfolio/{idUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarFotosDoPortfolio(@PathParam("idUsuario") UUID idUsuario) {
        List<ImagemDTO> imagens = imagemService.buscarFotosDoPortfolio(idUsuario);
        return Response.ok(imagens).build();
    }

}
