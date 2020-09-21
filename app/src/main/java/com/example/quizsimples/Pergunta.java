package com.example.quizsimples;

public class Pergunta {

    int id;
    String pergunta;
    String resposta;
    String categoria;

    public Pergunta() {

    }
    public Pergunta(String pergunta, String resposta, String categoria) {
        this.pergunta = pergunta;
        this.resposta = resposta;
        this.categoria = categoria;
    }

    public Pergunta(int id, String pergunta, String resposta, String categoria) {
        this.id = id;
        this.pergunta = pergunta;
        this.resposta = resposta;
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
