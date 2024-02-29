package salao.online.domain.entities;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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
    @Size(max = 20, message = "A rua deve ter no máximo 20 caracteres")
    private @Getter String rua;

    @NotEmpty
    @Size(max = 20, message = "O bairro deve ter no máximo 20 caracteres")
    private @Getter String bairro;

    @NotEmpty
    @Size(max = 20, message = "A cidade deve ter no máximo 20 caracteres")
    private @Getter String cidade;

    @NotEmpty
    private @Getter int numero;

    @NotEmpty
    private @Getter String cep;

    public Endereco(String rua, String bairro, String cidade, int numero, String cep) {
        this.rua = rua;
        this.bairro = bairro;
        this.cidade = cidade;
        this.numero = numero;
        this.cep = cep;
    }
}
