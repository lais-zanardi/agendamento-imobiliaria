package ada.agendamento.dto;

import ada.agendamento.entities.Agendamento;
import ada.agendamento.entities.Cliente;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class AgendamentoDTO {
    private Long id;
    private LocalDateTime horario;
    private String nomeCliente;
    private String enderecoImovel;
    private String nomeCorretor;
    private String telefoneCorretor;

    public AgendamentoDTO() {
    }


}
