package salao.online.infra.repositoriesImpl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;

import salao.online.domain.entities.Avaliacao;
import salao.online.domain.entities.Servico;
import salao.online.infra.repositories.ServicoRepository;

@ApplicationScoped
public class ServicoRepositoryImpl implements ServicoRepository {

    @Override
    public List<Avaliacao> buscarAvaliacoesDoServico(UUID idServico) {
        Servico servico = findByIdOptional(idServico).orElse(null);
        if (servico != null) {
            return servico.getAvaliacoes().stream()
                    .sorted(Comparator.comparing(Avaliacao::getDataAvaliacao).reversed())
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Servico> deletarServico(UUID idServico) {
        Optional<Servico> servicoOptional = findByIdOptional(idServico);
        if (servicoOptional.isPresent()) {
            deleteById(idServico);
        }
        return servicoOptional;
    }

}
