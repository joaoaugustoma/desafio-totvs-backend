package com.totvs.desafiotovs.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cliente_telefone")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteTelefone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "telefone")
    private String telefone;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Cliente cliente;
}
