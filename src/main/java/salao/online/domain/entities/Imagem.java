package salao.online.domain.entities;

import java.util.UUID;

import io.smallrye.common.constraint.NotNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = "salao", name = "imagem")
public class Imagem {

    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.AUTO)
    @Column(name = "id_imagem")
    @NotNull
    private @Getter UUID idImagem;

    private @Getter @Setter String nomeArquivo;

    private @Getter @Setter String urlImagem;

    public Imagem(String urlImagem, String nomeArquivo) {
        this.urlImagem = urlImagem;
        this.nomeArquivo = nomeArquivo;
    }

    public Imagem() {}
}