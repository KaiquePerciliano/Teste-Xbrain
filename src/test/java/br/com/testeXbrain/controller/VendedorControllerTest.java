package br.com.testeXbrain.controller;

import br.com.testeXbrain.model.Vendedor;
import br.com.testeXbrain.service.VendedorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VendedorController.class)
public class VendedorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VendedorService vendedorService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllVendedoresTest() throws Exception {
        List<Map<String, Object>> vendedores = List.of(Map.of("Name", "Kaique")
                , Map.of("Name", "Maria"));

        when(vendedorService.listAllVendedores()).thenReturn(vendedores);

        mockMvc.perform(get("/vendedor/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].Name").value("Kaique"))
                .andExpect(jsonPath("$[1]Name").value("Maria"));

        verify(vendedorService, times(1)).listAllVendedores();
    }

    @Test
    void getVendedorByIdTest() throws Exception {
        Vendedor vendedor = new Vendedor(1L, "teste");

        when(vendedorService.getVendedorById(vendedor.getId())).thenReturn(vendedor);

        mockMvc.perform(get("/vendedor/1")).andExpect(status().isOk());

        verify(vendedorService, times(1)).getVendedorById(vendedor.getId());
    }

    @Test
    void getVendedorVendasPeriodoTest() throws Exception {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date dataInicio = formato.parse("2025-03-01");
        Date dataFim = formato.parse("2025-03-30");

        List<Map<String, Object>> entrada = List.of(
                Map.of("id", 1L, "nome", "teste",
                        "totalVendas", 5, "mediaVendas", 0.5),
                Map.of("id", 2L, "nome", "test2",
                        "totalVendas", 10, "mediaVendas", 1.0)
        );

        when(vendedorService.getVendasByDateAndVendedor(any(Date.class), any(Date.class)))
                .thenReturn(entrada);

        mockMvc.perform(get("/vendedor/periodo-vendas")
                        .param("dataInicio", "2025-03-01")
                        .param("dataFim", "2025-03-30"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].nome").value("teste"))
                .andExpect(jsonPath("$[0].totalVendas").value(5))
                .andExpect(jsonPath("$[0].mediaVendas").value(0.5))
                .andExpect(jsonPath("$[1].nome").value("test2"))
                .andExpect(jsonPath("$[1].totalVendas").value(10))
                .andExpect(jsonPath("$[1].mediaVendas").value(1.0));

        verify(vendedorService, times(1))
                .getVendasByDateAndVendedor(any(Date.class), any(Date.class));
    }

    @Test
    void createVendedorTest() throws Exception {
        Vendedor vendedor = new Vendedor(1L, "test");

        when(vendedorService.createVendedor(any(Vendedor.class))).thenReturn(vendedor);

        mockMvc.perform(post("/vendedor/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vendedor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("test"));

        verify(vendedorService, times(1)).createVendedor(any(Vendedor.class));
    }

    @Test
    void updateVendedorTest() throws Exception {

        Vendedor vendedorExistente = new Vendedor(1L, "test");

        when(vendedorService.updateVendedor(eq(vendedorExistente.getId()), any(Vendedor.class)))
                .thenReturn(vendedorExistente);

        mockMvc.perform(put("/vendedor/{id}", vendedorExistente.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vendedorExistente)))
                .andExpect(status().isOk());

        verify(vendedorService, times(1))
                .updateVendedor(eq(vendedorExistente.getId()), any(Vendedor.class));
    }

    @Test
    void deleteVendedorTest() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/vendedor/{id}", id)).andExpect(status().isOk());

        verify(vendedorService, times(1)).deleteVendedor(id);
    }
}
