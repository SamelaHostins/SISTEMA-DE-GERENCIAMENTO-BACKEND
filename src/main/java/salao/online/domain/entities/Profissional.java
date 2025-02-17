package salao.online.domain.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.smallrye.common.constraint.NotNull;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = "salao", name = "profissional")
public class Profissional extends Informacao {

    protected Profissional() {
    }

    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.AUTO)
    @Column(name = "id_profissional")
    @NotNull
    private @Getter UUID idProfissional;

    private @Getter @Setter String instagram;

    @NotEmpty
    private @Getter @Setter String profissao;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_endereco", referencedColumnName = "id_endereco")
    private @Getter @Setter Endereco endereco;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "profissional")
    private @Getter List<Imagem> imagens = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "profissional")
    private @Getter List<Servico> servicos = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "profissional")
    private @Getter List<Estoque> estoques = new ArrayList<>();

    public Profissional(String nome, String sobrenome, LocalDate dataNascimento, String email, String telefone, 
            String usuario, String senha, String documento, String instagram, String profissao, Endereco endereco,
            List<Imagem> imagens, List<Servico> servicos, List<Estoque> estoques) {
        super(nome, sobrenome, dataNascimento, email, telefone, usuario, senha, documento);
        this.instagram = instagram;
        this.profissao = profissao;
        this.endereco = endereco;
        this.imagens = imagens;
        this.servicos = servicos;
        this.estoques = estoques;
    }

    public Profissional atualizarProfissional(String novoInstagram, String novaProfissao, String novoNome, 
            String novoSobrenome, LocalDate novaDataNascimento, String novoEmail, String novoTelefone, 
            String novaSenha, String novoDocumento, Endereco novoEndereco) {
        setInstagram(novoInstagram);
        setProfissao(novaProfissao);
        setNome(novoNome);
        setSobrenome(novoSobrenome);
        setDataNascimento(novaDataNascimento);
        setEmail(novoEmail);
        setTelefone(novoTelefone);
        setSenha(novaSenha);
        setDocumento(novoDocumento);
        setEndereco(novoEndereco);
        return this;
    }
}
