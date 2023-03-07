package ada.agendamento.services;

import ada.agendamento.dto.AgendamentoDTO;
import ada.agendamento.entities.Agendamento;
import ada.agendamento.repositories.AgendamentoRepository;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgendamentoService {

        @Autowired
        private AgendamentoRepository repository;

        @Autowired
        private RabbitTemplate rabbitTemplate;

        private static final String EXCHANGE_NAME = "agendamento.exchange";
        private static final String ROUTING_KEY = "agendamento.created";

        public Agendamento criarAgendamento(AgendamentoDTO agendamentoDTO) {
            Corretor corretor = new Corretor();
            corretor.setId(AgendamentoDTO.getCorretorId());
            Cliente cliente = new Cliente();
            cliente.setId(agendamentoDTO.getClienteId());
            Imovel imovel = new Imovel();
            imovel.setId(agendamentoDTO.getImovelId());

            Agendamento agendamento = new Agendamento();
            agendamento.setCorretor(corretor);
            agendamento.setCliente(cliente);
            agendamento.setImovel(imovel);
            agendamento.setHorario(agendamentoDTO.getHorario());

            Agendamento agendamentoSalvo = repository.save(agendamento);
            rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, agendamentoSalvo.getId());
            return agendamentoSalvo;
        }

        public Agendamento atualizarAgendamento(Long id, AgendamentoDTO agendamentoDTO) {
            Optional<Agendamento> agendamentoOriginal = repository.findById(id);
            if (agendamentoOriginal.isPresent()) {
                Agendamento agendamento = agendamentoOriginal.get();
                agendamento.setHorario(agendamentoDTO.getHorario());
                agendamento.setNomeCorretor(agendamentoDTO.getNomeCorretor());
                agendamento.setNomeCliente(agendamentoDTO.getNomeCliente());

                Agendamento agendamentoAtualizado = repository.save(agendamento);
                rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, agendamentoAtualizado.getId());

                return agendamentoAtualizado;
            } else {
                throw new NotFoundException("Agendamento não encontrado com ID: " + id);
            }
        }

        public void deletarAgendamento(Long id) {
            Agendamento agendamento = repository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Agendamento não encontrado"));
            repository.delete(agendamento);
        }

        public Agendamento buscarAgendamento(Long id) {
            return repository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Agendamento não encontrado"));
        }

        public List<Agendamento> getAgendamentosPorCorretor(Long id) {
            return repository.findByCorretorId(id);
        }

        public List<Agendamento> getAgendamentosPorCliente(Long id) {
            return repository.findByClienteId(id);
        }
    }


}
