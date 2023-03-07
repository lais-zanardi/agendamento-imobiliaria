package ada.agendamento.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Agendamento {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "corretor")
    private String nomeCorretor;

    @Column(name = "endereco_imovel")
    private String enderecoImovel;

    @Column(name = "cliente")
    private String nomeCliente;

    @Column(name = "horario")
    private LocalDateTime horario;

//    public void setNomeCorretor(CorretorDTO corretorDTO) {
//        this.nomeCorretor = corretorDTO;
//    }
//
//    public void setEnderecoImovel(ImovelDTO imovelDTO) {
//        this.enderecoImovel = imovelDTO;
//    }
//
//    public void setNomeCliente(ClienteDTO clienteDTO) {
//        this.nomeCliente = clienteDTO;
//    }
}
