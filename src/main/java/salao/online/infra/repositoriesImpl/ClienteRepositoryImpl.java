package salao.online.infra.repositoriesImpl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;

import salao.online.domain.entities.Avaliacao;
import salao.online.domain.entities.Cliente;
import salao.online.infra.repositories.ClienteRepository;

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

    @Override
    public Optional<Cliente> deletarCadastroDeCliente(UUID idCliente) {
        Optional<Cliente> clienteOptional = findByIdOptional(idCliente);
        if (clienteOptional.isPresent()) {
            deleteById(idCliente);
        }
        return clienteOptional;
    }

    @Override
    public Optional<Cliente> buscarClientePorId(UUID idCliente) {
        return find("id_cliente", idCliente).firstResultOptional();
    }

    @Override
    public List<Cliente> buscarClientes() {
        List<Cliente> clientes = listAll();
        clientes.sort(Comparator.comparing(Cliente::getNome));
        return clientes;
    }

    @Override
    public Optional<Cliente> buscarPeloEmail(String email) {
        // evita que letras maiusculas ou minusculas atrapalhem na busca e trata espaços
        // extras
        return find("LOWER(email)", email.trim().toLowerCase()).firstResultOptional();

    }

    public boolean existePorEmail(String email) {
        return find("email", email).firstResultOptional().isPresent();
    }

    public boolean existePorDocumento(String documento) {
        return find("documento", documento).firstResultOptional().isPresent();
    }

}
