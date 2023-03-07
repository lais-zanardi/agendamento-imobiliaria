package ada.agendamento.services;

import ada.agendamento.dto.AgendamentoDTO;
import ada.agendamento.entities.Agendamento;
import ada.agendamento.repositories.AgendamentoRepository;
import ada.agendamento.exceptions.NotFoundException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class AgendamentoService {

        @Autowired
        private AgendamentoRepository repository;

        @Autowired
        private RabbitTemplate rabbitTemplate;

        private static final String EXCHANGE_NAME = "agendamento.exchange";
        private static final String ROUTING_KEY = "agendamento.created";

        public Agendamento criarAgendamento(Agendamento agendamento) {

            Agendamento agendamentoSalvo = repository.save(agendamento);
            rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, agendamentoSalvo.getId());
            return repository.findByHorarioAndEnderecoAndCorretor(
                    agendamento.getHorario(),
                    agendamento.getEnderecoImovel(),
                    agendamento.getNomeCorretor())
                    .orElse(repository.save(agendamento));
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
    }
