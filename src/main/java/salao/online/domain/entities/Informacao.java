package salao.online.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import io.smallrye.common.constraint.NotNull;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public abstract class Informacao {

    protected Informacao() {
    }

    @NotNull
    private @Getter @Setter String nome;

    @NotNull
    private @Getter @Setter String sobrenome;

    @Column(name = "nome_social")
    private @Getter @Setter String nomeSocial;

    @NotNull
    private @Getter @Setter short idade;

    @NotNull
    private @Getter @Setter String email;

    @NotNull
    private @Getter @Setter String telefone;

    @NotNull
    private @Getter @Setter String usuario;

    @NotNull
    private @Getter @Setter String senha;

    public Informacao(String nome, String sobrenome, String nomeSocial, short idade, String email, String telefone,
            String usuario,
            String senha) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.nomeSocial = nomeSocial;
        this.idade = idade;
        this.email = email;
        this.telefone = telefone;
        this.usuario = usuario;
        this.senha = senha;
    }
}
