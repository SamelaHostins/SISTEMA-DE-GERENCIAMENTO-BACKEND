package salao.online.application.dtos.dtosDeCliente;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CriarClienteDTO {

    private String nome;
    private String sobrenome;
    private LocalDate dataNascimento; 
    private String email;
    private String telefone;
    private String usuario;
    private String senha;
    private String documento; 
}
