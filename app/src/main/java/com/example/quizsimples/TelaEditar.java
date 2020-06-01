package com.example.quizsimples;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class TelaEditar extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_editar);


        /*
        *
        *
        *
        *
        *
         TextView textResult, textTotal;
    EditText editPergunta, editResposta;
    Button btnTelaQuiz, btnAddPergunta, btnAdd, btnExibir, btnEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_editar_quiz);

        final TextView textResult =  findViewById(R.id.textResult);
        final TextView textTotal = findViewById(R.id.textTotal);


        Button btnTelaQuiz = findViewById(R.id.btnTelaQuiz);
        Button btnAddPergunta = findViewById(R.id.btnAddPergunta);
        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnExibir = findViewById(R.id.btnExibir);
        Button btnEditar = findViewById(R.id.btnEditar);

        final DataBasePerguntas dataBase = new DataBasePerguntas(this);

        btnTelaQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //chamar tela main
                Intent intent= new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pergunta = editPergunta.getText().toString();
                String resposta = editResposta.getText().toString();

                //criar objeto quiz com pergun e resp
                final Pergunta quiz = new Pergunta(pergunta,resposta);

                dataBase.addPergunta(quiz);//add objeto quiz no DB

                Toast.makeText(getApplicationContext(),  " Adicionado!", Toast.LENGTH_SHORT).show();

                //Limpa edit
                editPergunta.setText("");
                editResposta.setText("");
            }
        });
        * */
    }
}
