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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VendaServiceTest {

    @Mock
    VendedorRepository vendedorRepository;
    @Mock
    private VendaRepository vendaRepository;
    @InjectMocks
    private VendaService vendaService;

    private Vendedor vendedor;
    private Venda venda;

    @BeforeEach
    void padrao() {
        vendedor = new Vendedor(1L, "test");

        venda = new Venda();
        venda.setId(1L);
        venda.setVendedor(vendedor);
    }

    @Test
    void listAllVendasTest() {
        when(vendaRepository.findAll()).thenReturn(Collections.singletonList(venda));

        List<Venda> resultado = vendaService.listAllVendas();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());

        verify(vendaRepository, times(1)).findAll();
    }

    @Test
    void listVendaByIdTest() {
        when(vendaRepository.findById(1L)).thenReturn(Optional.of(venda));

        Venda resultado = vendaService.listVendaById(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());

        verify(vendaRepository, times(1)).findById(1L);
    }

    @Test
    void createVendaTest() {
        when(vendedorRepository.findById(1L)).thenReturn(Optional.of(vendedor));
        when(vendaRepository.save(any(Venda.class))).thenReturn(venda);

        Venda resultado = vendaService.createVenda(venda);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(vendedorRepository, times(1)).findById(1L);
        verify(vendaRepository, times(1)).save(any(Venda.class));
    }

    @Test
    void createVendaFailTest() throws Exception {
        when(vendedorRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            vendaService.createVenda(venda);
        });

        assertEquals("Vendedor de ID " + venda.getVendedor().getId() + " não encontrado.",
                exception.getMessage());

        verify(vendedorRepository, times(1)).findById(1L);
        verify(vendaRepository, never()).save(any(Venda.class));
    }

    @Test
    void updateVendaTest() {
        Venda vendaUpdate = new Venda();
        vendaUpdate.setId(1L);
        vendaUpdate.setVendedor(vendedor);

        when(vendaRepository.findById(1L)).thenReturn(Optional.of(venda));
        when(vendaRepository.save(any(Venda.class))).thenReturn(vendaUpdate);

        Venda resultado = vendaService.updateVenda(1L, vendaUpdate);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());

        verify(vendaRepository, times(1)).findById(1L);
        verify(vendaRepository, times(1)).save(any(Venda.class));
    }

    @Test
    void updateVendaFailTest() {
        when(vendaRepository.findById(1L)).thenReturn(Optional.empty());

        Venda resultado = vendaService.updateVenda(1L, venda);

        assertNull(resultado);
        verify(vendaRepository, times(1)).findById(1L);
        verify(vendaRepository, never()).save(any(Venda.class));
    }

    @Test
    void deleteVendaTest() {
        doNothing().when(vendaRepository).deleteById(1L);

        vendaService.deleteVenda(1L);

        verify(vendaRepository, times(1)).deleteById(1L);
    }

    @Test
    void testListVendasPeloVendedorId() {
        when(vendaRepository.findVendaByVendedorId(1L)).thenReturn(Collections.singletonList(venda));

        List<Venda> resultado = vendaService.listVendasPeloVendedorId(1L);

        assertEquals(1, resultado.size());
        assertEquals(1L, resultado.get(0).getId());
        verify(vendaRepository, times(1)).findVendaByVendedorId(1L);
    }

}
