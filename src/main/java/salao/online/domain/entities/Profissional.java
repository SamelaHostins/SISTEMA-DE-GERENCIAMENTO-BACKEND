package salao.online.domain.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.smallrye.common.constraint.NotNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
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

    @NotEmpty
    private @Getter @Setter String rua;

    @NotEmpty
    private @Getter @Setter String bairro;

    @NotEmpty
    private @Getter @Setter String cidade;

    @NotNull
    private @Getter @Setter int numero;

    @NotNull
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP inválido. Formato esperado: 00000-000")
    private @Getter @Setter String cep;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "profissional")
    private @Getter List<Imagem> imagens = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "profissional")
    private @Getter List<Servico> servicos = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "profissional")
    private @Getter List<Estoque> estoques = new ArrayList<>();

    public Profissional(String instagram, String profissao, String nome, String sobrenome, short idade, String email,
            String telefone, String usuario, String senha, String rua, String bairro, String cidade,
            int numero, String cep, List<Imagem> imagens, List<Servico> servicos, List<Estoque> estoques) {
        super(nome, sobrenome, idade, email, telefone, usuario, senha);
        this.instagram = instagram;
        this.profissao = profissao;
        this.rua = rua;
        this.bairro = bairro;
        this.cidade = cidade;
        this.numero = numero;
        this.cep = cep;
        this.imagens = imagens;
        this.servicos = servicos;
        this.estoques = estoques;
    }

    public Profissional atualizarProfissional(String novoInstagram, String novaProfissao, String novoNome, String novoSobrenome,
            short novaIdade, String novoEmail, String novoTelefone, String novaSenha, String novaRua,
            String novoBairro, String novaCidade, int novoNumero, String novoCep) {
        setInstagram(novoInstagram);
        setProfissao(novaProfissao);
        setNome(novoNome);
        setSobrenome(novoSobrenome);
        setIdade(novaIdade);
        setEmail(novoEmail);
        setTelefone(novoTelefone);
        setSenha(novaSenha);
        setRua(novaRua);
        setBairro(novoBairro);
        setCidade(novaCidade);
        setNumero(novoNumero);
        setCep(novoCep);
        return this;
    }

}
