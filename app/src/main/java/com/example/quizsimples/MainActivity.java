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
    int quizCont = 1;
    int id;

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

        final DataBasePerguntas db = new DataBasePerguntas(this);//Objeto DataBasePerguntas
        Pergunta pergunta=new Pergunta();//Objeto pergunta

        //Array para receber array de perguntas
        ArrayList<ArrayList<String>> arrayLista = new ArrayList<>();

        List<Pergunta> listaQuiz = db.getAllPerguntas();

        String text= "";

        for(Pergunta p: listaQuiz){
            ArrayList<String> arrayQuizAtual = new ArrayList<>();
            text = text+ "\nID : "+p.getId()+" \npergunta: "+p.getPergunta();
            arrayQuizAtual.add(0,p.getPergunta());
            arrayQuizAtual.add(1,p.getResposta());

            arrayLista.add(arrayQuizAtual);
        }
        Log.d("ded"," "+arrayLista.get(0));
        textPergunta.setText(text);
        Log.d("ded"," "+db.getPerguntaCount());
        //lista de questionário recebe a perguntas com as alternativas e a alternativa certa(CharSequence)

//         exibirQuiz();
    }

    public void exibirQuiz(){
        DataBasePerguntas db = new DataBasePerguntas(this);
        //List<Pergunta> listaQuiz = db.getAllPerguntas();

        Pergunta perg = new Pergunta();
        //Atualizar a numeração do quiz
//        String qtd = Integer.toString(db.getPerguntaCount());
//        textCont.setText("Q "+2+" /"+ db.getPerguntaCount());

        List<Pergunta> listaQuiz = new ArrayList();


        //gerar numero aleatório entre 0 e total(size) de quiz
        Random random = new Random();
        List<Pergunta> quiz = new ArrayList();
        perg= db.pegarPergunta(3);

        for(Pergunta p: listaQuiz){
            id= p.getId();
            pergunta= p.getPergunta();
            opCerta= p.getResposta();
        }
        textPergunta.setText(""+pergunta);

        Log.d("deb"," "+id);
        Log.d("deb"," "+opCerta);
        Log.d("deb"," "+pergunta);

//        Log.d("debi"," "+perg.getId());
        Log.d("debi"," "+perg.getPergunta());
        Log.d("debi"," "+perg.getResposta());

        /*
        int intRandom = random.nextInt(listaQuiz.size());

        //pegar o quiz de posição aleatória intRandom
        Pergunta quiz = listaQuiz.get(intRandom);

        //Ordem dos elemetos {"Pergunta e alternativas", "opCerta"}
        //o texto da pergunta recebe a posição 0 da lista quiz
        textPergunta.setText(quiz.getPergunta()); // posição 0 pergunta

        opCerta =  quiz.getResposta(); // posição 1 opção certa

        //Remover quiz respodido da lista de quiz
        listaQuiz.remove(intRandom);*/
    }

    public void mais(View view){

        Intent intentMais = new Intent(getApplicationContext(), TelaResultado.class);
        intentMais.putExtra("TOTAL_DE_PERGUNTAS", 3);//enviar total de per
        startActivity(intentMais);
    }

    //analizar resposta do botão clicado //todos os botões são check
    public void check(View view){
       final DataBasePerguntas db = new DataBasePerguntas(this);
        ArrayList<Pergunta> listaQuiz= new ArrayList<>();
        //pegar a escolha do usúario
        Button btnClicado = findViewById(view.getId());//pega o id do botão que ativou o check
        String textBtn = btnClicado.getText().toString(); // pega o texto do botão

        String textAlerta;

        //compara texto do botão clicado com a ressposta certa
        if(textBtn.equals(opCerta)){
            textAlerta = "Certa Resposta!";
            contCertas ++;
        }else{
            textAlerta= "Resposta Certa ...";
        }

        //Criar alerta
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(textAlerta);
        builder.setMessage(opCerta);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(quizCont == 3){
                    //ir para tela resultado
                    Intent intent = new Intent(getApplicationContext(), TelaResultado.class);
                    intent.putExtra("TOTAL_DE_ACERTOS", contCertas);//enviar total de acertos para tela d resultado
                    intent.putExtra("TOTAL_DE_PERGUNTAS", " "+db.getPerguntaCount());//enviar total de per
                    startActivity(intent);
                }else{
                    //ir para o próximo quiz
                    quizCont++;
                    exibirQuiz();
                }
            }
        });
        builder.setCancelable(false);
        builder.show();
    }
}
