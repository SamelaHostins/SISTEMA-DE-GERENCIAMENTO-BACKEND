package salao.online.domain.entities;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.GenericGenerator;

import io.smallrye.common.constraint.NotNull;
import lombok.Getter;

@Entity
@Table(schema = "salao", name = "endereco")
public class Endereco {

    protected Endereco() {
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_endereco")
    @NotNull
    private @Getter UUID idEndereco;

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

    public Endereco(String rua, String bairro, String cidade, int numero, String cep) {
        this.rua = rua;
        this.bairro = bairro;
        this.cidade = cidade;
        this.numero = numero;
        this.cep = cep;
    }
}
