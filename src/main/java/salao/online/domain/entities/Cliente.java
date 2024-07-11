package salao.online.domain.entities;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;



import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.smallrye.common.constraint.NotNull;
import lombok.Getter;
import lombok.Setter;

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

    private @Getter @Setter Boolean especial;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente")
    private @Getter List<Avaliacao> avaliacoes;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente")
    private @Getter List<Agendamento> agendamentos;

    public Cliente(String nome, String sobrenome, String nomeSocial, int idade, String email, String telefone,
            String usuario, String senha, Boolean especial, List<Avaliacao> avaliacoes,
            List<Agendamento> agendamentos) {
        super(nome, sobrenome, nomeSocial, idade, email, telefone, usuario, senha);
        this.especial = false;
        this.avaliacoes = avaliacoes;
        this.agendamentos = agendamentos;
    }

    public Cliente atualizarCadastroCliente(String novoNome, String novoSobrenome, String novoNomeSocial,
            int novaIdade, String novoEmail, String novoTelefone, String novaSenha) {
        setNome(novoNome);
        setSobrenome(novoSobrenome);
        setNomeSocial(novoNomeSocial);
        setIdade(novaIdade);
        setEmail(novoEmail);
        setTelefone(novoTelefone);
        setSenha(novaSenha);
        return this;
    }

    public void atualizarClienteEspecial() {
        this.especial = true;
    }

}