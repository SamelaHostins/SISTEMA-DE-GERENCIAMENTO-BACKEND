package salao.online.domain.entities;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.smallrye.common.constraint.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = "salao", name = "profissional")
public class Profissional extends Informacao {

    protected Profissional() {
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_profissional")
    @NotNull
    private @Getter UUID idProfissional;

    @NotEmpty
    private @Getter @Setter String rua;

    @NotEmpty
    private @Getter @Setter String bairro;

    @NotEmpty
    private @Getter @Setter String cidade;

    @NotEmpty
    private @Getter @Setter int numero;

    @NotNull
    private @Getter @Setter String cep;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "profissional")
    private @Getter List<Servico> servicos;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "profissional")
    private @Getter List<Estoque> estoques;

    public Profissional(String nome, String sobrenome, String nomeSocial, int idade, String email,
            String telefone, String usuario, String senha, String rua, String bairro, String cidade,
            int numero, String cep, List<Servico> servicos, List<Estoque> estoques) {
        super(nome, sobrenome, nomeSocial, idade, email, telefone, usuario, senha);
        this.rua = rua;
        this.bairro = bairro;
        this.cidade = cidade;
        this.numero = numero;
        this.cep = cep;
        this.servicos = servicos;
        this.estoques = estoques;
    }

    public Profissional atualizarCadastroProfissional(String novoNome, String novoSobrenome, String novoNomeSocial,
            int novaIdade, String novoEmail, String novoTelefone, String novaSenha, String novaRua,
            String novoBairro, String novaCidade, int novoNumero, String novoCep) {
        setNome(novoNome);
        setSobrenome(novoSobrenome);
        setNomeSocial(novoNomeSocial);
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
