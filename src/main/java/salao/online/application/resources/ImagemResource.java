package salao.online.application.resources;

import java.util.Map;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import salao.online.application.services.interfaces.ImagemService;
import salao.online.domain.configuracao.ImagemUploadForm;

@Path("/imagem")
@Tag(name = "Endpoints do Cloudinary - Imagem")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ImagemResource {

    @Inject
    ImagemService imagemService;

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadImagem(ImagemUploadForm uploadForm) {
        // Aqui você deve chamar seu serviço de imagem passando os dados recebidos
        String url = imagemService.uploadImagem(uploadForm.getImageBytes(), uploadForm.getNomeArquivo());

        if (url != null) {
            return Response.ok(Map.of("url", url)).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}