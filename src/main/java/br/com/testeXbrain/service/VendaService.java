package br.com.testeXbrain.service;

import br.com.testeXbrain.model.Venda;
import br.com.testeXbrain.model.Vendedor;
import br.com.testeXbrain.repository.VendaRepository;
import br.com.testeXbrain.repository.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private VendedorRepository vendedorRepository;

    public List<Venda> listAllVendas() {
        return vendaRepository.findAll();
    }

    public Venda listVendaById(Long id) {
        Optional<Venda> venda = vendaRepository.findById(id);
        return venda.orElse(null);
    }

    public Venda createVenda(Venda venda) {
        Optional<Vendedor> vendedorExiste = vendedorRepository.findById(venda.getVendedor().getId());
        if (vendedorExiste.isPresent()) {
            venda.setVendedor(vendedorExiste.get());
            return vendaRepository.save(venda);
        }
        throw new RuntimeException("Vendedor de ID " + venda.getVendedor().getId() + " não encontrado.");
    }

    public Venda updateVenda(Long id, Venda vendaDetails) {
        Optional<Venda> optionalVenda = vendaRepository.findById(id);
        if (optionalVenda.isPresent()) {
            Venda venda = optionalVenda.get();
            venda.setVendedor(vendaDetails.getVendedor());
            return vendaRepository.save(venda);
        }
        return null;
    }

    public void deleteVenda(Long id) {
        vendaRepository.deleteById(id);
    }

    public List<Venda> listVendasPeloVendedorId(Long vendedorId) {
        return vendaRepository.findVendaByVendedorId(vendedorId);
    }
}
