package ada.agendamento.services;

import ada.agendamento.entities.Cliente;
import ada.agendamento.entities.Corretor;
import ada.agendamento.repositories.ClienteRepository;
import ada.agendamento.repositories.CorretorRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

        @Autowired
        private ClienteRepository repository;

        @Autowired
        private RabbitTemplate rabbitTemplate;

        private static final String EXCHANGE_NAME = "cliente.exchange";
        private static final String ROUTING_KEY = "cliente.created";

        public Cliente criarCliente(Cliente cliente) {
            return repository.save(cliente);
        }

        public Cliente getClientePorId(Long id) {
            return repository.getById(id);
        }

}



