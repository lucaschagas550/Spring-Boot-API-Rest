package br.com.alura.forum.controller.dto;

import br.com.alura.forum.modelo.Topico;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class TopicoDto {

    private Long id;
    private String tipo;
    private String menssagem;
    private LocalDateTime dataCriacao;

    public TopicoDto(Topico topico) {
        this.id = topico.getId();
        this.tipo = topico.getTitulo();
        this.menssagem = topico.getMensagem();
        this.dataCriacao = topico.getDataCriacao();
    }

    public static List<TopicoDto> converter(List<Topico> topicos) {
        return topicos.stream().map(TopicoDto::new).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public String getMenssagem() {
        return menssagem;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

}
