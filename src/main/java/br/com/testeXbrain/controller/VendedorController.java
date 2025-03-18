package br.com.testeXbrain.controller;

import br.com.testeXbrain.model.Vendedor;
import br.com.testeXbrain.service.VendedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendedor")
public class VendedorController {

    @Autowired
    private VendedorService vendedorService;

    @GetMapping("/")
    public List<Vendedor> getAllVendedores() {
        return vendedorService.getAllVendedores();
    }

    @GetMapping("/{id}")
    public Vendedor getVendedorById(@PathVariable Long id) {
        return vendedorService.getVendedorById(id);
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
