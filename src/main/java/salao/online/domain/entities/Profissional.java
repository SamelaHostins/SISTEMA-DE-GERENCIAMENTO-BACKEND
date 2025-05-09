package salao.online.domain.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.smallrye.common.constraint.NotNull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import salao.online.domain.enums.ProfissaoEsteticaEnum;

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

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private @Getter @Setter ProfissaoEsteticaEnum profissao;

    @Column(name = "descricao_prof")
    private @Getter @Setter String descricaoDaProfissao;

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
            String usuario, String senha, String documento, String instagram, ProfissaoEsteticaEnum profissao,
            String descricaoDaProfissao, Endereco endereco, List<Imagem> imagens, List<Servico> servicos,
            List<Estoque> estoques) {
        super(nome, sobrenome, dataNascimento, email, telefone, usuario, senha, documento);
        this.instagram = instagram;
        this.profissao = profissao;
        this.descricaoDaProfissao = descricaoDaProfissao;
        this.endereco = endereco;
        this.imagens = imagens;
        this.servicos = servicos;
        this.estoques = estoques;
    }

    public Profissional atualizarProfissional(String novoInstagram, ProfissaoEsteticaEnum novaProfissao,
            String novaDescricaoDaProfissao, String novoNome, String novoUsuario, String novoSobrenome,
            String novoEmail, String novoTelefone, String novaSenha) {
        setInstagram(novoInstagram);
        setProfissao(novaProfissao);
        setDescricaoDaProfissao(novaDescricaoDaProfissao);
        setNome(novoNome);
        setUsuario(novoUsuario);
        setSobrenome(novoSobrenome);
        setEmail(novoEmail);
        setTelefone(novoTelefone);
        setSenha(novaSenha);
        return this;
    }

    public void atualizarEndereco(String rua, int numero, String bairro, String cidade, String estado, String cep,
            String complemento) {
        if (this.endereco == null) {
            this.endereco = new Endereco();
        }
        this.endereco.setRua(rua);
        this.endereco.setNumero(numero);
        this.endereco.setBairro(bairro);
        this.endereco.setCidade(cidade);
        this.endereco.setEstado(estado);
        this.endereco.setCep(cep);
        this.endereco.setComplemento(complemento);
    }

}
