package ada.agendamento.repositories;

import ada.agendamento.entities.Corretor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CorretorRepository extends JpaRepository<Corretor, Long> {

}