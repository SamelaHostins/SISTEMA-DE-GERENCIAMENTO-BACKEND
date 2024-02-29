package salao.online.domain.entities;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Entity
@Table(schema = "salao", name = "informacao")
public class Informacao {

    protected Informacao() {
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_informacao")
    @NotNull
    private @Getter UUID idInformacao;

    @NotEmpty
    @Size(max = 25, message = "O nome deve ter no máximo 25 caracteres")
    private @Getter String nome;

    @NotEmpty
    @Size(max = 30, message = "O sobrenome deve ter no máximo 30 caracteres")
    private @Getter String sobrenome;

    @NotEmpty
    private @Getter int idade;


    @NotEmpty
    @Email(message = "O e-mail deve ser válido")
    private @Getter String email;

    @NotEmpty
    @Size(max = 12, message = "O telefone deve ter no máximo 12 caracteres")
    private @Getter String telefone;

    @NotNull
    @Size(min = 8, max = 8, message = "A senha deve ter 8 caracteres")
    private @Getter String senha;

    public Informacao(String nome, String sobrenome, int idade, String email, String telefone, String senha) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.idade = idade;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
    }
}
