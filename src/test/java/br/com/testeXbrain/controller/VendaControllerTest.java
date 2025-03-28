package br.com.testeXbrain.controller;

import br.com.testeXbrain.model.Venda;
import br.com.testeXbrain.service.VendaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VendaController.class)
public class VendaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VendaService vendaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllVendasTest() throws Exception {
        when(vendaService.listAllVendas()).thenReturn(List.of(new Venda(), new Venda()));

        this.mockMvc.perform(get("/vendas/")).andExpect(status().isOk());
        verify(vendaService, times(1)).listAllVendas();
    }

    @Test
    void getVendaByIdTest() throws Exception {
        Venda venda = new Venda();
        venda.setId(1L);

        when(vendaService.listVendaById(venda.getId())).thenReturn(venda);

        this.mockMvc.perform(get("/vendas/1")).andExpect(status().isOk());

        verify(vendaService, times(1)).listVendaById(venda.getId());
    }

    @Test
    void createVendaTest() throws Exception {
        Venda venda = new Venda();
        venda.setId(1L);
        venda.setValor(150.50f);

        when(vendaService.createVenda(venda)).thenReturn(venda);

        mockMvc.perform(post("/vendas/").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(venda))).andExpect(status().isOk());

        verify(vendaService, times(1)).createVenda(any(Venda.class));
    }

    @Test
    void updateVendaTest() throws Exception {
        Venda vendaExistente = new Venda();
        vendaExistente.setId(1L);
        vendaExistente.setValor(100.50f);

        when(vendaService.updateVenda(eq(vendaExistente.getId()), any(Venda.class)))
                .thenReturn(vendaExistente);

        mockMvc.perform(put("/vendas/{id}", vendaExistente.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vendaExistente))).andExpect(status().isOk());

        verify(vendaService, times(1))
                .updateVenda(eq(vendaExistente.getId()), any(Venda.class));
    }

    @Test
    void deleteVendaTest() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/vendas/{id}", id)).andExpect(status().isOk());
        verify(vendaService, times(1)).deleteVenda(id);
    }

    @Test
    void getVendasByVendedorIdTest() throws Exception {

        Long vendedorId = 1L;

        Venda venda1 = new Venda();
        venda1.setId(1L);
        venda1.setValor(100.50f);

        Venda venda2 = new Venda();
        venda2.setId(2L);
        venda2.setValor(200.75f);

        List<Venda> vendas = Arrays.asList(venda1, venda2);

        when(vendaService.listVendasPeloVendedorId(vendedorId)).thenReturn(vendas);

        mockMvc.perform(get("/vendas/vendedor/{vendedorId}", vendedorId))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].valor").value(100.50f))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].valor").value(200.75f));

        verify(vendaService, times(1)).listVendasPeloVendedorId(vendedorId);
    }
}