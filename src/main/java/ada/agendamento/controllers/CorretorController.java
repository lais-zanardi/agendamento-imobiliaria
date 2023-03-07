package ada.agendamento.controllers;

import ada.agendamento.entities.Corretor;
import ada.agendamento.services.CorretorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/corretor")
@RequiredArgsConstructor
public class CorretorController {

    private final CorretorService corretorService;

    @PostMapping
    public void criarCorretor(@RequestBody Corretor corretor) {
        corretorService.criarCorretor(corretor);
    }

    @GetMapping("/{id}")
    public Corretor getCorretorPorId(@PathVariable Long id) {
        return corretorService.getCorretorPorId(id);
    }
}
