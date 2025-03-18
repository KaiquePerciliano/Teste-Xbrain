package br.com.testeXbrain.service;

import br.com.testeXbrain.model.Vendedor;
import br.com.testeXbrain.repository.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VendedorService {

    @Autowired
    private VendedorRepository vendedorRepository;

    public List<Vendedor> getAllVendedores() {
        return vendedorRepository.findAll();
    }

    public Vendedor getVendedorById(Long id) {
        Optional<Vendedor> vendedor = vendedorRepository.findById(id);
        return vendedor.orElse(null);
    }

    public Vendedor createVendedor(Vendedor vendedor) {
        return vendedorRepository.save(vendedor);
    }

    public Vendedor updateVendedor(Long id, Vendedor vendedorDetails) {
        Optional<Vendedor> optionalVendedor = vendedorRepository.findById(id);
        if(optionalVendedor.isPresent()) {
            Vendedor vendedor = optionalVendedor.get();
            vendedor.setName(vendedorDetails.getName());
            return vendedorRepository.save(vendedor);
        }
        return null;
    }

    public void deleteVendedor(Long id) {
        vendedorRepository.deleteById(id);
    }

}
