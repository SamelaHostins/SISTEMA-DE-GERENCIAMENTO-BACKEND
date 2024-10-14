package salao.online.application.services.impl;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import jakarta.enterprise.context.ApplicationScoped;
import salao.online.application.services.interfaces.ImagemService;

@ApplicationScoped
public class ImagemServiceImpl implements ImagemService {

    private Cloudinary cloudinary;

    public ImagemServiceImpl() {
        // Configurar Cloudinary com suas credenciais
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dx2jocpeo",
            "api_key", "745685656596579",
            "api_secret", "ZRJ_kcRwt27XR7XPbWg9tgl1dAM"
        ));
    }

    public String uploadImagem(InputStream imageBytes, String nomeArquivo) {
        try {
            Map<String, Object> options = new HashMap<>();
            options.put("public_id", nomeArquivo);

            @SuppressWarnings("unchecked")
            Map<String, Object> uploadedFile = cloudinary.uploader().upload(imageBytes, options);
            return (String) uploadedFile.get("secure_url");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}