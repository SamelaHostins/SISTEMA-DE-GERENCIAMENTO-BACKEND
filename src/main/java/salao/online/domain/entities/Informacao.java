package salao.online.domain.entities;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import io.smallrye.common.constraint.NotNull;
import lombok.Getter;

@MappedSuperclass
public abstract class Informacao {

    protected Informacao() {
    }

    @NotBlank
    @Size(min = 3, max = 25, message = "O nome deve ter entre 3 e 25 caracteres")    
    private @Getter String nome;

    @NotBlank
    @Size(min = 3, max = 25, message = "O sobrenome deve ter entre 3 e 25 caracteres")    
    private @Getter String sobrenome;

    @NotEmpty
    @Positive
    private @Getter int idade;

    @NotEmpty
    @Email(message = "O e-mail deve ser válido")
    @Size(max = 30, message = "O e-mail deve ter no máximo 30 caracteres")    
    private @Getter String email;

    @NotEmpty
    @Pattern(regexp = "^\\d+$", message = "O telefone deve conter apenas dígitos de 0 a 9")
    @Size(max = 12, message = "O telefone deve ter no máximo 12 caracteres")
    private @Getter String telefone;

    @NotNull
    @Size(min = 3, max = 25, message = "O usuário deve conter no máximo 25 caracteres")
    private @Getter String usuario;

    @NotNull
    @Pattern(regexp = "^(?=.*[0-9]).{8}$", message = "A senha deve conter pelo menos um número")    
    private @Getter String senha;

    public Informacao(String nome, String sobrenome, int idade, String email, String telefone, String usuario, String senha) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.idade = idade;
        this.email = email;
        this.telefone = telefone;
        this.usuario = usuario;
        this.senha = senha;
    }
}
