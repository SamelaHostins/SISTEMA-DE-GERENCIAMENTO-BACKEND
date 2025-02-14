package salao.online.application.services.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import salao.online.application.dtos.AvaliacaoDTO;
import salao.online.application.mappers.AvaliacaoMapper;
import salao.online.application.mappers.ClienteMapper;
import salao.online.application.mappers.ServicoMapper;
import salao.online.application.services.interfaces.AvaliacaoService;
import salao.online.domain.entities.Avaliacao;
import salao.online.domain.entities.Cliente;
import salao.online.domain.entities.Servico;
import salao.online.domain.enums.MensagemErroValidacaoEnum;
import salao.online.domain.exceptions.ValidacaoException;
import salao.online.infra.repositories.AvaliacaoRepository;
import salao.online.infra.repositories.ClienteRepository;
import salao.online.infra.repositories.ServicoRepository;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;

@ApplicationScoped
public class AvaliacaoServiceImpl implements AvaliacaoService {

        @Inject
        AvaliacaoRepository avaliacaoRepository;

        @Inject
        ServicoRepository servicoRepository;

        @Inject
        ClienteRepository clienteRepository;

        @Inject
        AvaliacaoMapper avaliacaoMapper;

        @Inject
        ServicoMapper servicoMapper;

        @Inject
        ClienteMapper clienteMapper;

        private static Logger logger = LoggerFactory.getLogger(LoggerFactory.class);

        private Cliente validarCliente(UUID idCliente) throws ValidacaoException {
                logger.info("Validando se o Cliente existe");
                return clienteRepository.findByIdOptional(idCliente)
                                .orElseThrow(() -> new ValidacaoException(
                                                MensagemErroValidacaoEnum.CLIENTE_NAO_ENCONTRADO.getMensagemErro()));
        }

        private Servico validarServico(UUID idServico) throws ValidacaoException {
                logger.info("Validando se o Serviço existe");
                return servicoRepository.findByIdOptional(idServico)
                                .orElseThrow(() -> new ValidacaoException(
                                                MensagemErroValidacaoEnum.SERVICO_NAO_ENCONTRADO.getMensagemErro()));
        }

        private Avaliacao validarAvaliacao(Cliente cliente, Servico servico) {
                logger.info("Validando se a avaliação existe");
                return avaliacaoRepository
                                .buscarAvaliacaoClienteServico(cliente.getIdCliente(), servico.getIdServico())
                                .orElse(null);
        }

        @Override
        @Transactional
        public AvaliacaoDTO inserirAvaliacao(AvaliacaoDTO avaliacaoDTO) throws ValidacaoException {
                Cliente cliente = validarCliente(avaliacaoDTO.getIdCliente());
                Servico servico = validarServico(avaliacaoDTO.getIdServico());
                Avaliacao avaliacao = new Avaliacao(avaliacaoDTO.getNota(), cliente, servico);
                avaliacaoRepository.persistAndFlush(avaliacao);
                return getAvaliacaoDto(avaliacao);
        }

        @Override
        @Transactional
        public AvaliacaoDTO atualizarAvaliacao(AvaliacaoDTO avaliacaoDTO) throws ValidacaoException {
                Cliente cliente = this.validarCliente(avaliacaoDTO.getIdCliente());
                Servico servico = this.validarServico(avaliacaoDTO.getIdServico());
                Avaliacao avaliacao = this.validarAvaliacao(cliente, servico);

                logger.info("Atualizando a avaliação");
                avaliacao.atualizarAvaliacao(avaliacaoDTO.getNota());

                logger.info("Atualizando registro");
                avaliacaoRepository.persistAndFlush(avaliacao);
                return getAvaliacaoDto(avaliacao);
        }

        @Override
        public BigDecimal buscarMediaAvaliacoesServico(UUID idServico) throws ValidacaoException {
                Servico servico = validarServico(idServico);

                logger.info("Pegando todas as notas");
                List<Integer> notas = servico.getAvaliacoes().stream().map(x -> x.getNota())
                                .collect(Collectors.toList());

                if (notas.size() >= 3) {
                        logger.info("Somando todas as notas do Servico");
                        Integer soma = notas.stream()
                                        .collect(Collectors.summingInt(Integer::intValue));

                        logger.info("Retornando a média das avaliações do Servico");
                        return new BigDecimal(soma)
                                        .divide(new BigDecimal(notas.size()), 1,
                                                        RoundingMode.UP);
                }
                return null;
        }

        private AvaliacaoDTO getAvaliacaoDto(Avaliacao avaliacao) {
                AvaliacaoDTO avaliacaoDTO = avaliacaoMapper.fromEntityToDto(avaliacao);
                avaliacaoDTO.setIdServico(servicoMapper.fromEntityToDto(avaliacao.getServico()).getIdServico());
                avaliacaoDTO.setIdCliente(clienteMapper.fromEntityToBuscarDto(avaliacao.getCliente()).getIdCliente());
                return avaliacaoDTO;
        }

        @Override
        public AvaliacaoDTO buscarAvaliacaoClienteServico(UUID idCliente, UUID idServico) throws ValidacaoException {
                Cliente cliente = this.validarCliente(idCliente);
                Servico servico = this.validarServico(idServico);
                Avaliacao avaliacao = this.validarAvaliacao(cliente, servico);
                AvaliacaoDTO avaliacaoDTO = (avaliacao != null) ? getAvaliacaoDto(avaliacao) : null;
                return avaliacaoDTO;
        }

        @Override
        public List<AvaliacaoDTO> buscarAvaliacoesDoServico(UUID idServico) {
                List<Avaliacao> avaliacoes = servicoRepository.buscarAvaliacoesDoServico(idServico);
                logger.info("Validando se há avaliações desse aluno");
                if (avaliacoes.isEmpty()) {
                        return Collections.emptyList();
                } else {
                        return avaliacoes.stream()
                                        .map(this::getAvaliacaoDto)
                                        .collect(Collectors.toList());
                }
        }

        @Override
        public List<AvaliacaoDTO> buscarAvaliacoesDoCliente(UUID idCliente) throws ValidacaoException {
                List<Avaliacao> avaliacoes = clienteRepository.buscarTodasAvaliacoesDoCliente(idCliente);

                logger.info("Validando se há avaliações desse aluno");
                if (avaliacoes.isEmpty()) {
                        return Collections.emptyList();
                } else {
                        return avaliacoes.stream()
                                        .map(this::getAvaliacaoDto)
                                        .collect(Collectors.toList());
                }
        }
}
