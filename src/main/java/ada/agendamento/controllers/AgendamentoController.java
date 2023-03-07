package ada.agendamento.controllers;

import ada.agendamento.dto.AgendamentoDTO;
import ada.agendamento.entities.Agendamento;
import ada.agendamento.services.AgendamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@RestController
@RequiredArgsConstructor
@RequestMapping("/agendamento")
public class AgendamentoController {
    private final AgendamentoService service;
    private final RabbitTemplate rabbitTemplate;

    @PostMapping
    public ResponseEntity<Void> criarAgendamento(@RequestBody Agendamento agendamento) {
        service.criarAgendamento(agendamento);
        rabbitTemplate.convertAndSend("agendamento.exchange", "agendamento.criado", agendamento.getId());
        return ResponseEntity.created(URI.create("/agendamento" + agendamento.getId())).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarAgendamento(@PathVariable Long id, @RequestBody AgendamentoDTO agendamentoDTO) {
        service.atualizarAgendamento(id, agendamentoDTO);
        rabbitTemplate.convertAndSend("agendamento.exchange", "agendamento.updated", id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVisit(@PathVariable Long id) {
        service.deletarAgendamento(id);
        rabbitTemplate.convertAndSend("agendamento.exchange", "agendamento.deleted", id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Agendamento> getVisit(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarAgendamento(id));
    }

}




