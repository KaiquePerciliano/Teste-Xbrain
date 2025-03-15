package com.testeXbrain.controller;

import com.testeXbrain.model.Vendedor;
import com.testeXbrain.repository.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vendedor")
public class VendedorController {

    @Autowired
    private VendedorRepository vendedorRepository;

    @PostMapping("/")
    public Vendedor vendedor(@RequestBody Vendedor vendedor) {
        return this.vendedorRepository.save(vendedor);
    }
}
