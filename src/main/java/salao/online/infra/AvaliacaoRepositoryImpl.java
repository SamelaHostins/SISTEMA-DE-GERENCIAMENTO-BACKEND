package salao.online.infra;

import java.util.Optional;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;

import salao.online.domain.entities.Avaliacao;
import salao.online.domain.repositories.AvaliacaoRepository;

@ApplicationScoped
public class AvaliacaoRepositoryImpl implements AvaliacaoRepository {

    @Override
    public Optional<Avaliacao> buscarAvaliacaoClienteServico(UUID idCliente, UUID idServico) {
        return find("cliente.idCliente = ?1 and servico.idServico = ?2", idCliente, idServico).firstResultOptional();
    }
}
