package ada.agendamento.dto;

import ada.agendamento.entities.Agendamento;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class AgendamentoDTO {
    private Long id;
    private LocalDateTime horario;
    private ClienteDTO nomeCliente;
    private ImovelDTO enderecoImovel;
    private CorretorDTO nomeCorretor;
    private CorretorDTO telefoneCorretor;

    public AgendamentoDTO() {
    }

    public AgendamentoDTO(Agendamento agendamento) {
        this.id = agendamento.getId();
        this.horario = agendamento.getHorario();
        this.nomeCorretor = new CorretorDTO(agendamento.getCorretor().getNomeCorretor);
        this.enderecoImovel = new ImovelDTO(agendamento.getImovel().getEndereco());
        this.nomeCliente = new ClienteDTO(agendamento.getCliente().getNomeCliente());
        this.nomeCliente = new ClienteDTO(agendamento.getCliente().getTelefoneCliente());
    }

}
