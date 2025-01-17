package salao.online.application.resources;

import java.io.InputStream;
import java.util.Map;
import java.util.UUID;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
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
import salao.online.application.dtos.TipoImagemEnumDTO;
import salao.online.application.services.interfaces.ImagemService;
import salao.online.domain.entities.Imagem;
import salao.online.domain.enums.TipoImagemEnum;

@Path("/imagem")
@Tag(name = "Endpoints do Cloudinary - Imagem")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ImagemResource {

    @Inject
    ImagemService imagemService;

    private static final org.jboss.logging.Logger LOG = org.jboss.logging.Logger.getLogger(ImagemResource.class);

    // @Operation(summary = "Faz o upload de uma imagem para o Cloudinary")
    // @POST
    // @Path("/upload")
    // @Consumes(MediaType.MULTIPART_FORM_DATA)
    // public Response uploadImagem(@RestForm("imageBytes") InputStream imageBytes,
    // @RestForm("nomeArquivo") String nomeArquivo) {
    // try {
    // if (imageBytes == null || nomeArquivo == null || nomeArquivo.isEmpty()) {
    // return Response.status(Response.Status.BAD_REQUEST)
    // .entity("Parâmetros inválidos: certifique-se de fornecer a imagem e o nome do
    // arquivo.")
    // .build();
    // }

    // String url = imagemService.uploadImagem(imageBytes, nomeArquivo);

    // if (url != null) {
    // LOG.info("Upload realizado com sucesso. URL: " + url);
    // return Response.ok(Map.of("url", url)).build();
    // } else {
    // LOG.error("Falha ao realizar o upload da imagem.");
    // return Response.status(Response.Status.BAD_REQUEST).build();
    // }
    // } catch (Exception e) {
    // LOG.error("Erro ao fazer upload da imagem: ", e);
    // return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
    // .entity("Erro ao realizar o upload da imagem.")
    // .build();
    // }
    // }

    @Operation(summary = "Faz o upload de uma imagem para o Cloudinary e salva no banco de dados")
    @POST
    @Transactional
    @Path("/upload/{tipoImagem}/{idUsuario}/{ehProfissional}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadImagem(
            @RestForm("imageBytes") InputStream imageBytes,
            @RestForm("nomeArquivo") String nomeArquivo,
            @PathParam("tipoImagem") int tipoImagem,
            @PathParam("idUsuario") UUID idUsuario,
            @PathParam("ehProfissional") boolean ehProfissional) {
        try {
            // Validação dos parâmetros
            if (imageBytes == null || nomeArquivo == null || nomeArquivo.isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Parâmetros inválidos: certifique-se de fornecer a imagem e o nome do arquivo.")
                        .build();
            } // Determinar o tipo de imagem com base no enum
            TipoImagemEnum tipoImagemEnum = TipoImagemEnum.fromTipoImagem(tipoImagem);

            // Chamada ao serviço para realizar o upload e salvar no banco
            String url = imagemService.uploadImagem(imageBytes, nomeArquivo, tipoImagemEnum, idUsuario, ehProfissional);

            if (url != null) {
                LOG.info("Upload realizado com sucesso. URL: " + url);
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

    // @Operation(summary = "Salvar uma imagem no banco de dados")
    // @APIResponse(responseCode = "200", description = "Imagem salva com sucesso!")
    // @APIResponse(responseCode = "500", description = "Erro ao salvar a imagem.")
    // @POST
    // @Path("/salvar")
    // @Transactional
    // public Response salvarImagem(@RequestBody SalvarImagemDTO dto) {
    //     try {
    //         LOG.info("Requisição recebida - Salvar Imagem");
    //         SalvarImagemDTO resposta = imagemService.salvarImagem(dto);
    //         return Response.ok(resposta).build();
    //     } catch (IllegalArgumentException e) {
    //         return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
    //     } catch (Exception e) {
    //         return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao salvar imagem.").build();
    //     }
    // }

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
