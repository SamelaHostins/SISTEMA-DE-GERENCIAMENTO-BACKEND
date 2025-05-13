package salao.online.infra.repositoriesImpl;

import java.util.List;
import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;
import salao.online.domain.entities.PerguntaFrequente;
import salao.online.infra.repositories.PerguntaFrequenteRepository;

@ApplicationScoped
public class PerguntaFrequenteRepositoryImpl implements PerguntaFrequenteRepository {

    public List<PerguntaFrequente> buscarPorProfissional(UUID idProfissional) {
    return find("profissional.idProfissional", idProfissional).list();
}

}
