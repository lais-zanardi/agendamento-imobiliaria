package ada.agendamento.controllers;

import ada.agendamento.entities.Cliente;
import ada.agendamento.services.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService service;

    @PostMapping
    public void criarCliente(@RequestBody Cliente cliente) {
        service.criarCliente(cliente);
    }

    @GetMapping("/{id}")
    public Cliente getClientePorId(@PathVariable Long id) {
        return service.getClientePorId(id);
    }
}
