package salao.online.application.dtos.dtosDoProfissional;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AtualizarProfissionalDTO {

    private UUID idCliente;
    private String profissao;
    private String nome;
    private String sobrenome;
    private short idade;
    private String email;
    private String telefone;
    private String senha;
    private String rua;
    private String bairro;
    private String cidade;
    private int numero;
    private String cep;
}
