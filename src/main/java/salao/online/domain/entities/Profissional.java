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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.smallrye.common.constraint.NotNull;
import lombok.Getter;

@Entity
@Table(schema = "salao", name = "profissional", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "id_endereco" }) })
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
    private @Getter String rua;

    @NotEmpty
    private @Getter String bairro;

    @NotEmpty
    private @Getter String cidade;

    @NotEmpty
    private @Getter int numero;

    @NotNull
    private @Getter String cep;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "profissional")
    private @Getter List<Servico> servicos;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "profissional")
    private @Getter List<Estoque> estoques;

    public Profissional(String nome, String sobrenome, int idade, String email, 
    String telefone, String usuario, String senha, String rua, String bairro, String cidade,
    int numero, String cep, List<Servico> servicos, List<Estoque> estoques) {
        super(nome, sobrenome, idade, email, telefone, usuario, senha);
        this.rua = rua;
        this.bairro = bairro;
        this.cidade = cidade;
        this.numero = numero;
        this.cep = cep;
        this.servicos = servicos;
        this.estoques = estoques;
    }

}
