package salao.online.infra;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;

import salao.online.domain.entities.Avaliacao;
import salao.online.domain.entities.Cliente;
import salao.online.domain.repositories.ClienteRepository;

@ApplicationScoped
public class ClienteRepositoryImpl implements ClienteRepository {

    @Override
    public List<Avaliacao> buscarTodasAvaliacoesDoCliente(UUID idCliente) {
        Cliente cliente = findByIdOptional(idCliente).orElse(null);
        if (cliente != null) {
            return cliente.getAvaliacoes().stream()
            .sorted(Comparator.comparing(Avaliacao::getDataAvaliacao).reversed())
            .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }
}
