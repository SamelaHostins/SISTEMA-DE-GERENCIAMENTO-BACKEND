package salao.online.application.services.impl;

import java.util.List;
import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import salao.online.application.dtos.PerguntaFrequenteDTO;
import salao.online.application.mappers.PerguntaFrequenteMapper;
import salao.online.application.services.interfaces.PerguntaFrequenteService;
import salao.online.domain.entities.PerguntaFrequente;
import salao.online.domain.entities.Profissional;
import salao.online.infra.repositories.PerguntaFrequenteRepository;
import salao.online.infra.repositories.ProfissionalRepository;

@ApplicationScoped
public class PerguntaFrequenteServiceImpl implements PerguntaFrequenteService {

    @Inject
    PerguntaFrequenteMapper perguntaFrequenteMapper;

    @Inject
    ProfissionalRepository profissionalRepository;

    @Inject
    PerguntaFrequenteRepository perguntaFrequenteRepository;

    @Override
    public PerguntaFrequenteDTO adicionarPergunta(UUID idProfissional, PerguntaFrequenteDTO dto) {
        Profissional profissional = profissionalRepository.findById(idProfissional);
        if (profissional == null) {
            throw new WebApplicationException("Profissional não encontrado", 404);
        }
        PerguntaFrequente entity = perguntaFrequenteMapper.fromDtoToEntity(dto);
        entity.setProfissional(profissional); // associa ao profissional

        perguntaFrequenteRepository.persist(entity);

        return perguntaFrequenteMapper.fromEntityToDto(entity);
    }

    @Override
    public PerguntaFrequenteDTO atualizarPergunta(UUID idPergunta, UUID idProfissional, PerguntaFrequenteDTO dto) {
        PerguntaFrequente pergunta = perguntaFrequenteRepository.findById(idPergunta);

        if (pergunta == null || !pergunta.getProfissional().getIdProfissional().equals(idProfissional)) {
            throw new WebApplicationException("Pergunta não encontrada ou não pertence a você", 403);
        }

        pergunta.setPergunta(dto.getPergunta());
        pergunta.setResposta(dto.getResposta());

        perguntaFrequenteRepository.persist(pergunta);
        return perguntaFrequenteMapper.fromEntityToDto(pergunta);
    }

    @Override
    public List<PerguntaFrequenteDTO> listarPerguntasPorProfissional(UUID idProfissional) {
        List<PerguntaFrequente> lista = perguntaFrequenteRepository.buscarPorProfissional(idProfissional);
        return perguntaFrequenteMapper.fromEntityListToDtoList(lista);
    }

    @Override
    public void deletarPergunta(UUID idPergunta, UUID idProfissional) {
        PerguntaFrequente pergunta = perguntaFrequenteRepository.findById(idPergunta);

        if (pergunta == null || !pergunta.getProfissional().getIdProfissional().equals(idProfissional)) {
            throw new WebApplicationException("Pergunta não encontrada ou acesso negado", 403);
        }

        perguntaFrequenteRepository.delete(pergunta);
    }

}
