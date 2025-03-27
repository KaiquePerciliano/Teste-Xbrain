package br.com.testeXbrain.controller;

import br.com.testeXbrain.model.Vendedor;
import br.com.testeXbrain.service.VendedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/vendedor")
public class VendedorController {

    @Autowired
    private VendedorService vendedorService;

    @GetMapping("/")
    public List<Map<String, Object>> getAllVendedores() {
        return vendedorService.listAllVendedores();
    }

    @GetMapping("/{id}")
    public Vendedor getVendedorById(@PathVariable Long id) {
        return vendedorService.getVendedorById(id);
    }

    @GetMapping("/periodo-vendas")
    public List<Map<String, Object>> getVendedorVendasPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            Date dataInicio, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dataFim) {
        return vendedorService.getVendasByDateAndVendedor(dataInicio, dataFim);
    }

    @PostMapping("/")
    public Vendedor createVendedor(@RequestBody Vendedor vendedor) {
        return vendedorService.createVendedor(vendedor);
    }

    @PutMapping("/{id}")
    public Vendedor updateVendedor(@PathVariable Long id, @RequestBody Vendedor vendedor) {
        return vendedorService.updateVendedor(id, vendedor);
    }

    @DeleteMapping("/{id}")
    public void deleteVendedor(@PathVariable Long id) {
        vendedorService.deleteVendedor(id);
    }

}
