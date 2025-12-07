package com.seuprojeto.ifoodlike.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categorias_produto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoriaProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    private String descricao;

    @Column(nullable = false)
    private Boolean ativa = true;

    @OneToMany(mappedBy = "categoria")
    @Builder.Default
    private List<Produto> produtos = new ArrayList<>();
}
