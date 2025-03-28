package br.com.testeXbrain.repository;

import br.com.testeXbrain.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;


public interface VendaRepository extends JpaRepository<Venda, Long> {
    List<Venda> findVendaByVendedorId(Long vendedorId);

    List<Venda> findVendaByVendedorIdAndDataVendaBetween(Long vendedorId, Date dataInicio, Date dataFim);
}
