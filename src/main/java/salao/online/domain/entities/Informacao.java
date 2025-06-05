package salao.online.domain.entities;

import jakarta.validation.constraints.NotNull;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;

@MappedSuperclass
public abstract class Informacao {

    protected Informacao() {
    }

    @NotNull
    private @Getter @Setter String nome;

    @NotNull
    private @Getter @Setter String sobrenome;

    @NotNull
    @Past(message = "A data de nascimento deve ser no passado")
    @Column(name = "data_nascimento", nullable = false)
    private @Getter @Setter LocalDate dataNascimento;

    @Column(unique = true, nullable = false)
    @NotNull
    private @Getter @Setter String usuario;

    @NotNull
    private @Getter @Setter String senha;

    @Column(unique = true, nullable = false)
    @NotNull
    private @Getter @Setter String telefone;

    @Column(unique = true, nullable = false)
    @NotNull
    private @Getter @Setter String email;

    @Column(unique = true, nullable = false)
    private @Getter @Setter String documento;

    @Column(name = "aceitou_termos", nullable = false)
    private @Getter @Setter Boolean aceitouTermos;

    public Informacao(String nome, String sobrenome, LocalDate dataNascimento, String email, String telefone, 
            String usuario, String senha, String documento, Boolean aceitouTermos) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.telefone = telefone;
        this.usuario = usuario;
        this.senha = senha;
        this.documento = documento;
        this.aceitouTermos = aceitouTermos;
    }

    // Calcula a idade
    public int getIdade() {
        return Period.between(this.dataNascimento, LocalDate.now()).getYears();
    }
}
