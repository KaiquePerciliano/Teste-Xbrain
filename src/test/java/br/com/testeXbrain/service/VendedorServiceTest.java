package br.com.testeXbrain.service;

import br.com.testeXbrain.model.Venda;
import br.com.testeXbrain.model.Vendedor;
import br.com.testeXbrain.repository.VendaRepository;
import br.com.testeXbrain.repository.VendedorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VendedorServiceTest {

    @Mock
    private VendaRepository vendaRepository;

    @Mock
    private VendedorRepository vendedorRepository;

    @InjectMocks
    private VendedorService vendedorService;

    private Vendedor vendedor;
    private List<Venda> vendas;

    @BeforeEach
    void padrao() {
        vendedor = new Vendedor(1L, "test");

        Venda venda = new Venda();
        venda.setId(1L);
        venda.setVendedor(vendedor);

        vendas = Collections.singletonList(venda);
    }

    @Test
    void listAllVendedoresTest() {
        when(vendedorRepository.findAll()).thenReturn(Collections.singletonList(vendedor));
        when(vendaRepository.findVendaByVendedorId(1L)).thenReturn(vendas);

        List<Map<String, Object>> resultado = vendedorService.listAllVendedores();
        assertEquals(1, resultado.size());
        assertEquals("test", resultado.get(0).get("nome"));
        assertEquals(1, resultado.get(0).get("totalVendas"));

        verify(vendedorRepository, times(1)).findAll();
        verify(vendaRepository, times(1)).findVendaByVendedorId(1L);
    }

    @Test
    void getVendedorByIdTest() {
        when(vendedorRepository.findById(1L)).thenReturn(Optional.of(vendedor));

        Vendedor resultado = vendedorService.getVendedorById(1L);
        assertNotNull(resultado);
        assertEquals("test", resultado.getName());

        verify(vendedorRepository, times(1)).findById(1L);
    }

    @Test
    void createVendedorTest() {
        when(vendedorService.createVendedor(vendedor)).thenReturn(vendedor);

        Vendedor resultado = vendedorService.createVendedor(vendedor);
        assertNotNull(resultado);
        assertEquals("test", resultado.getName());

        verify(vendedorRepository, times(1)).save(vendedor);
    }

    @Test
    void updateVendedorTest() {
        Vendedor vendedorUpdate = new Vendedor();
        vendedorUpdate.setName("kaique");

        when(vendedorRepository.findById(1L)).thenReturn(Optional.of(vendedor));
        when(vendedorRepository.save(any(Vendedor.class))).thenReturn(vendedorUpdate);

        Vendedor resultado = vendedorService.updateVendedor(1L, vendedorUpdate);

        assertNotNull(resultado);
        assertEquals("kaique", resultado.getName());

        verify(vendedorRepository, times(1)).findById(1L);
        verify(vendedorRepository, times(1)).save(any(Vendedor.class));
    }

    @Test
    void deleteVendedorTest() {
        doNothing().when(vendedorRepository).deleteById(1L);
        vendedorService.deleteVendedor(1L);
        verify(vendedorRepository, times(1)).deleteById(1L);
    }
}
