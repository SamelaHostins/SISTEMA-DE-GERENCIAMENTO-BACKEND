package salao.online.application.resources;

import java.io.InputStream;
import java.util.Map;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import salao.online.application.services.interfaces.ImagemService;


@Path("/imagem")
@Tag(name = "Endpoints do Cloudinary - Imagem")
@Produces(MediaType.APPLICATION_JSON)
public class ImagemResource {

    @Inject
    ImagemService imagemService;

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadImagem(@FormDataParam("imageBytes") InputStream imageBytes,
            @FormDataParam("nomeArquivo") String nomeArquivo) {
        String url = imagemService.uploadImagem(imageBytes, nomeArquivo);

        if (url != null) {
            return Response.ok(Map.of("url", url)).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

}