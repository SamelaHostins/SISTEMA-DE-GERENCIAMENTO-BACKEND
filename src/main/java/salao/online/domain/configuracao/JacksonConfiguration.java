package salao.online.domain.configuracao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.inject.Singleton;
import io.quarkus.jackson.ObjectMapperCustomizer;

@Singleton
public class JacksonConfiguration implements ObjectMapperCustomizer {

    @Override
    public void customize(ObjectMapper mapper) {
        // Isso garante que @JsonValue seja respeitado
        mapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, false);
        mapper.configure(SerializationFeature.WRITE_ENUMS_USING_INDEX, false);
    }
}
