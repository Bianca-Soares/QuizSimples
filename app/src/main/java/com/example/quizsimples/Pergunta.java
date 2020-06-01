package com.example.quizsimples;

public class Pergunta {

    int id;
    String pergunta;
    String resposta;

    public Pergunta() {

    }
    public Pergunta(String pergunta, String resposta) {
        this.pergunta = pergunta;
        this.resposta = resposta;
    }

    public Pergunta(int id, String pergunta, String resposta) {
        this.id = id;
        this.pergunta = pergunta;
        this.resposta = resposta;
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
}
