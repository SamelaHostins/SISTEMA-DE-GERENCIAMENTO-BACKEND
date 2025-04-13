package salao.online;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import salao.online.application.dtos.dtosDoAgendamento.FormaPagamentoEnumDTO;
import salao.online.application.dtos.dtosDoAgendamento.StatusAgendamentoEnumDTO;

public class StatusAgendamentoEnumDTOTest {

    @Test
    public void deveSerializarEnumComoNumero() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(StatusAgendamentoEnumDTO.AGENDADO);

        // Espera que o enum seja serializado como número 1
        assertEquals("1", json);
    }

    @Test
    public void deveSerializarFormaPagamentoComoNumero() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(FormaPagamentoEnumDTO.CARTAO_CREDITO);

        assertEquals("1", json);
    }

}
