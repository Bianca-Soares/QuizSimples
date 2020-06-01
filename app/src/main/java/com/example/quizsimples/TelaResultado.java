package com.example.quizsimples;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TelaResultado extends AppCompatActivity {

    TextView textResult, textTotal;
    EditText editPergunta, editOpCerta;
    Button btnEditar, btnTelaQuiz, btnExibir, btnPesquisar, btnAddPergunta;
    RelativeLayout layoutPesquisa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_resultado);

        final TextView textResult =  findViewById(R.id.textResult);
        final TextView textTotal = findViewById(R.id.textTotal);

        Button btnEditar = findViewById(R.id.btnEditar);
        Button btnTelaQuiz = findViewById(R.id.btnTelaQuiz);
        btnExibir= findViewById(R.id.btnExibir);
        btnPesquisar = findViewById(R.id.btnPesquisar);
        btnAddPergunta = findViewById(R.id.btnAddPergunta);


        layoutPesquisa = findViewById(R.id.layoutPesquisa);

        limparTudo();
        //Calcular o resultado e Exibir

        //int que pega da activity main o número de acertos
        int totalAcertos = getIntent().getIntExtra("TOTAL_DE_ACERTOS", 0);
        //PEGA DA TELA MAIN  TOTAL DE PERGUNTAS
        int totalPerguntas = getIntent().getIntExtra("TOTAL_DE_PERGUNTAS", 0);

        //Salvar info de melhor pontuação sem usar BD
        SharedPreferences melhorPonto = getSharedPreferences("quizSimpless", Context.MODE_PRIVATE);

        //int pontosMax padrão 0
        int pontosMax= melhorPonto.getInt("totalPontos",0);
        //se for maior pontosMax recebe esse valor
        if(totalAcertos > pontosMax)
        {
            pontosMax = totalAcertos;
        }
        textResult.setText(totalAcertos + " /"+ totalPerguntas);
        textTotal.setText("Melhor pontuação:  "+ pontosMax);

        SharedPreferences.Editor editor = melhorPonto.edit();
        editor.putInt("totalPontos", pontosMax);
        editor.commit();



        btnTelaQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //chamar tela main
                Intent intent= new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Tela para editar quiz
                Intent intentEditar = new Intent(getApplicationContext(), EditarQuiz.class);
                startActivity(intentEditar);
            }
        });

    }

    private void limparTudo() {
        btnExibir.setVisibility(View.INVISIBLE);
        btnPesquisar.setVisibility(View.INVISIBLE);
        btnAddPergunta.setVisibility(View.INVISIBLE);
        layoutPesquisa.setVisibility(View.INVISIBLE);
    }
}
