package ada.agendamento.services;

import ada.agendamento.dto.AgendamentoDTO;
import ada.agendamento.entities.Agendamento;
import ada.agendamento.entities.Corretor;
import ada.agendamento.repositories.CorretorRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CorretorService {

        @Autowired
        private CorretorRepository repository;

        @Autowired
        private RabbitTemplate rabbitTemplate;

        private static final String EXCHANGE_NAME = "corretor.exchange";
        private static final String ROUTING_KEY = "corretor.created";

        public Corretor criarCorretor(Corretor corretor) {
            return repository.save(corretor);
        }

        public Corretor getCorretorPorId(Long id) {
            return repository.getById(id);
        }

}



