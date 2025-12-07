package com.seuprojeto.ifoodlike.dtos.comum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiErrorDTO {

    private int status;
    private String erro;
    private String mensagem;
    private String path;
    private LocalDateTime timestamp;
    private List<String> detalhes;
}
