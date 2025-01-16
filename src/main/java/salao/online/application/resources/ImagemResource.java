package salao.online.application.resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.UUID;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestForm;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import salao.online.application.dtos.SalvarImagemDTO;
import salao.online.application.services.interfaces.ImagemService;
import salao.online.domain.entities.Imagem;
import salao.online.domain.enums.TipoImagemEnum;

@Path("/imagem")
@Tag(name = "Endpoints do Cloudinary - Imagem")
@Produces(MediaType.APPLICATION_JSON)
public class ImagemResource {

    @Inject
    ImagemService imagemService;

    private static final org.jboss.logging.Logger LOG = org.jboss.logging.Logger.getLogger(ImagemResource.class);

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadImagem(@RestForm("imageBytes") InputStream imageBytes,
            @RestForm("nomeArquivo") String nomeArquivo) {
        try {
            if (imageBytes == null || nomeArquivo == null || nomeArquivo.isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Parâmetros inválidos: certifique-se de fornecer a imagem e o nome do arquivo.")
                        .build();
            }

            String url = imagemService.uploadImagem(imageBytes, nomeArquivo);

            if (url != null) {
                LOG.info("Upload realizado com sucesso. URL: " + url);
                return Response.ok(Map.of("url", url)).build();
            } else {
                LOG.error("Falha ao realizar o upload da imagem.");
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        } catch (Exception e) {
            LOG.error("Erro ao fazer upload da imagem: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao realizar o upload da imagem.")
                    .build();
        }
    }

    @Operation(summary = "Salvar uma imagem no banco de dados")
    @APIResponse(responseCode = "200", description = "Imagem salva com sucesso!")
    @APIResponse(responseCode = "500", description = "Erro ao salvar a imagem.")
    @POST
    @Path("/salvar")
    @Transactional
    public Response salvarImagem(SalvarImagemDTO payload) {
        try {
            LOG.info("Requisição recebida - Salvar Imagem");
    
            // Chama o serviço para salvar a imagem
            SalvarImagemDTO resposta = imagemService.salvarImagem(
                    payload.getUrlImagem(),
                    payload.getNomeArquivo(),
                    TipoImagemEnum.valueOf(payload.getTipoImagem().getTipoImagem()),
                    payload.getUsuarioId(),
                    payload.isProfissional()
            );
    
            // Retorna o DTO como resposta
            return Response.ok(resposta).build();
        } catch (Exception ex) {
            LOG.error("Erro ao salvar imagem no banco de dados", ex);
            return Response.status(500).entity("Erro ao salvar a imagem.").build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getImagem(@PathParam("id") UUID id) {
        try {
            Imagem imagem = imagemService.buscarImagemPorId(id);

            if (imagem != null) {
                LOG.info("Imagem encontrada. URL: " + imagem.getUrlImagem());
                return Response.ok(Map.of("url", imagem.getUrlImagem())).build();
            } else {
                LOG.warn("Imagem não encontrada. ID: " + id);
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Imagem não encontrada.")
                        .build();
            }
        } catch (Exception e) {
            LOG.error("Erro ao buscar a imagem: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar a imagem.")
                    .build();
        }
    }
}
