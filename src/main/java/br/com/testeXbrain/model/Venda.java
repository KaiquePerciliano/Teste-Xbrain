package com.testeXbrain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private Date dataVenda;
    private float valor;
    private Vendedor idVendedor;
    private Vendedor nomeVendedor;
}
