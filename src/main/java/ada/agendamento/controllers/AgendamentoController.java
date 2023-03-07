package ada.agendamento.controllers;

import ada.agendamento.dto.AgendamentoDTO;
import ada.agendamento.entities.Agendamento;
import ada.agendamento.services.AgendamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/agendamento")
public class AgendamentoController {
    private final AgendamentoService service;
    private final RabbitTemplate rabbitTemplate;

    @PostMapping
    public ResponseEntity<Void> criarAgendamento(@RequestBody AgendamentoDTO agendamentoDTO) {
        Agendamento agendamento = service.criarAgendamento(agendamentoDTO);
        rabbitTemplate.convertAndSend("agendamento.exchange", "agendamento.criado", agendamento.getId());
        return ResponseEntity.created(URI.create("/visits/" + agendamento.getId())).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarAgendamento(@PathVariable Long id, @RequestBody AgendamentoDTO agendamentoDTO) {
        service.atualizarAgendamento(id, agendamentoDTO);
        rabbitTemplate.convertAndSend("visit.exchange", "visit.updated", id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVisit(@PathVariable Long id) {
        service.deletarAgendamento(id);
        rabbitTemplate.convertAndSend("visit.exchange", "visit.deleted", id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoDTO> getVisit(@PathVariable Long id) {
        Agendamento agendamento = service.buscarAgendamento(id);
        AgendamentoDTO agendamentoDTO = new AgendamentoDTO(agendamento);
        return ResponseEntity.ok(agendamentoDTO);
    }

    @GetMapping("/corretor/{id}")
    public ResponseEntity<List<AgendamentoDTO>> getAgendamentosPorCorretor(@PathVariable Long id) {
        List<Agendamento> agendamentos = service.getAgendamentoPorCorretor(id);
        List<AgendamentoDTO> agendamentosDTO = agendamentos.stream().map(AgendamentoDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(agendamentosDTO);
    }

    @GetMapping("/cliente/{id}")
    public ResponseEntity<List<AgendamentoDTO>> getAgendamentosPorCliente(@PathVariable Long id) {
        List<Agendamento> agendamentos = service.getAgendamentosPorCliente(id);
        List<AgendamentoDTO> agendamentosDTO = agendamentos.stream().map(AgendamentoDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(agendamentosDTO);
    }
}
}



