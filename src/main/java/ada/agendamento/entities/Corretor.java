package ada.agendamento.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Corretor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonProperty("nome_corretor")
    private String nomeCorretor;

    @JsonProperty("telefone_corretor")
    private String telefoneCorretor;
}
