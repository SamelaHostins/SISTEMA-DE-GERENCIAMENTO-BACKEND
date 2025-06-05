package salao.online.domain.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.smallrye.common.constraint.NotNull;
import lombok.Getter;

@Entity
@Table(schema = "salao", name = "cliente")
public class Cliente extends Informacao {

    protected Cliente() {
    }

    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.AUTO)
    @Column(name = "id_cliente")
    @NotNull
    private @Getter UUID idCliente;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente")
    private @Getter List<Avaliacao> avaliacoes;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente")
    private @Getter List<Agendamento> agendamentos;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente")
    private @Getter List<Imagem> imagens;

    public Cliente(String nome, String sobrenome, LocalDate dataNascimento, String email, String telefone,
            String usuario, String senha, String documento, Boolean aceitouTermos, List<Avaliacao> avaliacoes,
            List<Agendamento> agendamentos, List<Imagem> imagens) {
        super(nome, sobrenome, dataNascimento, email, telefone, usuario, senha, documento, aceitouTermos);
        this.avaliacoes = avaliacoes;
        this.agendamentos = agendamentos;
        this.imagens = imagens;
    }

    public Cliente atualizarCliente(String novoNome, String novoUsuario, String novoSobrenome, String novoEmail, String novoTelefone) {
        setNome(novoNome);
        setUsuario(novoUsuario);
        setSobrenome(novoSobrenome);
        setEmail(novoEmail);
        setTelefone(novoTelefone);
        return this;
    }
}
