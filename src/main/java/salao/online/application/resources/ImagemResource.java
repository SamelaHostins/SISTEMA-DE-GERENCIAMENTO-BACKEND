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
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import salao.online.application.dtos.ImagemDTO;
import salao.online.application.services.interfaces.ImagemService;
import salao.online.domain.entities.Imagem;

@Path("/imagem")
@Tag(name = "Endpoints do Cloudinary - Imagem")
@Produces(MediaType.APPLICATION_JSON)
public class ImagemResource {

    @Inject
    ImagemService imagemService;

    private static final org.jboss.logging.Logger LOG = org.jboss.logging.Logger.getLogger(ProfissionalResource.class);

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadImagem(@RestForm("imageBytes") InputStream imageBytes,
            @RestForm("nomeArquivo") String nomeArquivo) {
        String url = null;
        try {
            url = imagemService.uploadImagem(imageBytes, nomeArquivo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (url != null) {
            return Response.ok(Map.of("url", url)).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @Operation(summary = "Salvar uma imagem")
    @APIResponse(responseCode = "200", description = "Imagem salva com sucesso!")
    @APIResponse(responseCode = "500", description = "Ocorreu um erro na requisição.")
    @POST
    @Transactional
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("/salvar")
    public Response salvarImagem(@RestForm("imageBytes") InputStream imageBytes,
            @RestForm("nomeArquivo") String nomeArquivo) {
        try {
            LOG.info("Requisição recebida - Salvar Imagem");
            Imagem imagem = imagemService.salvarImagem(imageBytes, nomeArquivo);
            return Response.ok(Map.of("url", imagem.getUrlImagem())).build();
        } catch (Exception ex) {
            return Response.status(500).entity("Ocorreu um erro na requisição.").build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getImagem(@PathParam("id") UUID id) {
        try {
            Imagem imagem = imagemService.buscarImagemPorId(id);
            if (imagem != null) {
                return Response.ok(Map.of("url", imagem.getUrlImagem())).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Imagem não encontrada.")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar a imagem.")
                    .build();
        }
    }

}