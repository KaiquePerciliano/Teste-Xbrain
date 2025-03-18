package br.com.testeXbrain.controller;

import br.com.testeXbrain.model.Venda;
import br.com.testeXbrain.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/venda")
public class VendaController {

    @Autowired
    private VendaService vendaService;

    @GetMapping("/")
    public List<Venda> getAllVendas() {
        return vendaService.listAllVendas();
    }

    @GetMapping("/{id}")
    public Venda getVendaById(@PathVariable Long id) {
        return vendaService.listVendaById(id);
    }

    @PostMapping("/")
    public Venda createVenda(@RequestBody Venda venda) {
        return vendaService.createVenda(venda);
    }

    @PutMapping("/{id}")
    public Venda updateVenda (@PathVariable Long id, @RequestBody Venda venda) {
        return vendaService.updateVenda(id, venda);
    }

    @DeleteMapping("/{id}")
    public void deleteVenda (@PathVariable Long id) {
        vendaService.deleteVenda(id);
    }

    @GetMapping("/vendedor/{vendedorId}")
    public List<Venda> getVendasByVendedorId(@PathVariable Long vendedorId) {
        return vendaService.listVendasPeloVendedorId(vendedorId);
    }

}
