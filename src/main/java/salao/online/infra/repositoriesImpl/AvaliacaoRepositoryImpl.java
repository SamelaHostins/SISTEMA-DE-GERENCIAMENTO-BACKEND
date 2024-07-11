package salao.online.infra.repositoriesImpl;

import java.util.Optional;
import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;

import salao.online.domain.entities.Avaliacao;
import salao.online.infra.repositories.AvaliacaoRepository;

@ApplicationScoped
public class AvaliacaoRepositoryImpl implements AvaliacaoRepository {

    @Override
    public Optional<Avaliacao> buscarAvaliacaoClienteServico(UUID idCliente, UUID idServico) {
        return find("cliente.idCliente = ?1 and servico.idServico = ?2", idCliente, idServico).firstResultOptional();
    }
}
