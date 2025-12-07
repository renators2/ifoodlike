package com.seuprojeto.ifoodlike.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lojas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Loja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    @Column(nullable = false)
    private String nomeFantasia;

    @Column(nullable = false, unique = true)
    private String cnpj;

    private String endereco;

    private String telefone;

    @Column(nullable = false)
    private Boolean ativa = true;

    @OneToMany(mappedBy = "loja", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Produto> produtos = new ArrayList<>();

    @OneToMany(mappedBy = "loja", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Pedido> pedidos = new ArrayList<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
