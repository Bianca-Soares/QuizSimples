package com.example.quizsimples;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Array;
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

    private int counter = 0, total;

    private List<Pergunta> perguntaList;
    private  Pergunta perguntaAtual;

    private static final String[] CATEGORIAS= new String[]{
            "Categoria 1", "Categoria 2"
    };

    private MultiAutoCompleteTextView editCategoria;

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

        Spinner spinnerCategorias = findViewById(R.id.spinnerCategorias);

        perguntaList = db.getAllPerguntas();
        total = perguntaList.size();

        editarCategorias();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1, CATEGORIAS);
        spinnerCategorias.setAdapter(adapter);

        exibir();
    }

    private void editarCategorias( ) {
        int cont = 0;
        String[] CATEGORIAS = new String[10];
        if(cont < total) {

            perguntaAtual = perguntaList.get(cont);
            CATEGORIAS[cont] = perguntaAtual.getCategoria();
            Log.d("oncre","exibir()  "+ CATEGORIAS[cont]+" "+total+" "+cont);
            cont++;

        }

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
                    "D) Conceber os projetos tecnológicos de infraestrutura.\n", "C","categoria x"));
        }

        if(counter < total) {
            textCont.setText("Q " + (counter +1));

            perguntaAtual = perguntaList.get(counter);
            textPergunta.setText(perguntaAtual.getPergunta());
            opCerta = perguntaAtual.getResposta().toUpperCase();

            counter ++;
            Log.d("total", String.valueOf(total));

        }else{
            finish();
        }

    }

    public void mais(View view){

        Intent intentMais = new Intent(getApplicationContext(), TelaResultado.class);
        intentMais.putExtra("TOTAL_DE_PERGUNTAS", db.getPerguntaCount());//enviar total de per
        startActivity(intentMais);
    }
    public void ok(View view){
        //String input = spinnerCate .getText().toString();

        exibir();
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
