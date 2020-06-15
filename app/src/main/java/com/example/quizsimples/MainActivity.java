package com.example.quizsimples;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView textCont, textPergunta;

    Button btnMais, btnOp1, btnOp2, btnOp3, btnOp4;

    String pergunta, opCerta;
    int contCertas = 0;
    int contQuiz = 1;
    int totalQuiz= 0;
    int id;

    DataBasePerguntas db = new DataBasePerguntas(this);//Objeto DataBasePerguntas


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textCont = findViewById(R.id.textCont);
        textPergunta = findViewById(R.id.textPergunta);


        btnMais = findViewById(R.id.btnMais);
        btnOp1 = findViewById(R.id.btnOp1);
        btnOp2 = findViewById(R.id.btnOp2);
        btnOp3 = findViewById(R.id.btnOp3);
        btnOp4 = findViewById(R.id.btnOp4);

        //contQuiz = db.getPerguntaCount();
        Log.d("oncre","OnCreate");

        exibir();
    }

    private void exibir() {
        Log.d("oncre","exibir()  ");
        DataBasePerguntas db = new DataBasePerguntas(this);//Objeto DataBasePerguntas

        ArrayList<ArrayList<String>> arrayLista = new ArrayList<>();

        Pergunta pergunta = new Pergunta();//Objeto pergunta

        if(db.getPerguntaCount()==0) {
            db.addPergunta(new Pergunta("A finalidade da construção do PDTI é:\n" +
                    "\n" +
                    "A) Mapear os processos de Tecnologia da Informação.\n" +
                    "B) Conhecer os custos pormenorizados dos ativos de TI.\n" +
                    "C) Obter o alinhamento estratégico de TI.\n" +
                    "D) Conceber os projetos tecnológicos de infraestrutura.\n", "C"));
        }
        //Lista para receber lista de perguntas
        List<Pergunta> listaQuiz = db.getAllPerguntas();
        //passar para um array para usar index

        for(Pergunta p: listaQuiz){
            ArrayList<String> arrayQuizAux = new ArrayList<>();

            arrayQuizAux.add(0, String.valueOf(p.getId()));
            arrayQuizAux.add(1,p.getPergunta());
            arrayQuizAux.add(2,p.getResposta());


            arrayLista.add(arrayQuizAux);
        }
        ArrayList<String>  quizAux = new ArrayList<>();

        Random random = new Random();
        int nAleatorio = random.nextInt(db.getPerguntaCount());// int aleat de 1 ate o TAM

        //array para pegar cada pergunta pergunta aleatoriamente


        ArrayList<String> listaQuizAtual =  arrayLista.get(nAleatorio);//array com 0 pergunta e 1 resposta
        Log.d("oncre","nAleatorio "+nAleatorio);


        textCont.setText("ID " + listaQuizAtual.get(0));// id da pergunta da vez
        textPergunta.setText(listaQuizAtual.get(1));// pergunta da vez
        opCerta= listaQuizAtual.get(2);//opção certa

        arrayLista.remove(nAleatorio);// remover pergunta já usada do array

    }

    public void mais(View view){

        Intent intentMais = new Intent(getApplicationContext(), TelaResultado.class);
        intentMais.putExtra("TOTAL_DE_PERGUNTAS", db.getPerguntaCount());//enviar total de per
        startActivity(intentMais);
    }

    //analizar resposta do botão clicado //todos os botões são check
    public void check(View view){
       final DataBasePerguntas db = new DataBasePerguntas(this);
       // ArrayList<Pergunta> listaQuiz= new ArrayList<>();

        //pegar a escolha do usúario
        Button btnClicado = findViewById(view.getId());//pega o id do botão que ativou o check
        String textBtn = btnClicado.getText().toString().toUpperCase(); // pega o texto do botão

        String textAlerta="";

        //compara texto do botão clicado com a ressposta certa
        if(textBtn.equals(opCerta)){

            textAlerta = "Certa Resposta!";
            opCerta="";
            contCertas ++;
        }else{
            textAlerta= "Resposta é...";
        }

        //Criar alerta
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(textAlerta);
        builder.setMessage(opCerta);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(contQuiz == db.getPerguntaCount()){
                    //ir para tela resultado
                    Intent intent = new Intent(getApplicationContext(), TelaResultado.class);
                    intent.putExtra("TOTAL_DE_ACERTOS", contCertas);//enviar total de acertos para tela d resultado
                    intent.putExtra("TOTAL_DE_PERGUNTAS", db.getPerguntaCount());//enviar total de perguntas

                    startActivity(intent);
                }else{
                    //ir para o próximo quiz
                    contQuiz++;
                    Log.d("oncre","else:   OnCreate");
                    exibir();


                }
            }
        });
        builder.setCancelable(false);
        builder.show();

    }
}
