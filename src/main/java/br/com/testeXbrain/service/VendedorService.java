package br.com.testeXbrain.service;

import br.com.testeXbrain.model.Venda;
import br.com.testeXbrain.model.Vendedor;
import br.com.testeXbrain.repository.VendaRepository;
import br.com.testeXbrain.repository.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class VendedorService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private VendedorRepository vendedorRepository;

    public List<Map<String, Object>> listAllVendedores() {
        List<Vendedor> vendedores = vendedorRepository.findAll();
        List<Map<String, Object>> resultado = new ArrayList<>();

        for (Vendedor vendedor : vendedores) {
            List<Venda> vendas = vendaRepository.findVendaByVendedorId(vendedor.getId());

            Map<String, Object> mapVendedor = new HashMap<>();
            mapVendedor.put("id", vendedor.getId());
            mapVendedor.put("nome", vendedor.getName());
            mapVendedor.put("totalVendas", vendas.size());
            mapVendedor.put("vendas", vendas);

            resultado.add(mapVendedor);
        }
        return resultado;
    }

    public List<Map<String, Object>> getVendasByDateAndVendedor(Date dataInicio, Date dataFim) {
        List<Vendedor> vendedores = vendedorRepository.findAll();
        List<Map<String, Object>> resultado = new ArrayList<>();

        for (Vendedor vendedor : vendedores) {
            List<Venda> vendas = vendaRepository.findVendaByVendedorIdAndDataVendaBetween(vendedor.getId(),
                    dataInicio, dataFim);

            long diasInformados = TimeUnit.DAYS.convert(dataFim.getTime() - dataInicio.getTime(),
                    TimeUnit.MILLISECONDS) + 1;
            int totalVendas = vendas.size();
            double mediaVendas = (double) totalVendas / diasInformados;

            Map<String, Object> mapVendedor = new HashMap<>();
            mapVendedor.put("id", vendedor.getId());
            mapVendedor.put("nome", vendedor.getName());
            mapVendedor.put("totalVendas", totalVendas);
            mapVendedor.put("vendas", vendas);
            mapVendedor.put("mediaVendas", mediaVendas);

            resultado.add(mapVendedor);
        }
        return resultado;
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
        if (optionalVendedor.isPresent()) {
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
