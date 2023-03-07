package ada.agendamento.repositories;

import ada.agendamento.entities.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    Optional<Agendamento> findByHorarioAndEnderecoAndCorretor(LocalDateTime horario, String enderecoImovel , String nomeCorretor);

}
