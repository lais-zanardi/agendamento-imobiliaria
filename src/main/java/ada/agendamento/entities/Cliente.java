package ada.agendamento.entities;

import ada.agendamento.enums.TipoCliente;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Data
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    private String cpf;

    @JsonProperty("data_nascimento")
    private LocalDate dataNascimento;

    private String endereco;

    @JsonProperty("dados_bancarios")
    private String dadosBancarios;

    @JsonProperty("tipo_cliente")
    private TipoCliente tipoCliente;
}
