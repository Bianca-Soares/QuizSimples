package com.example.quizsimples;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditarQuiz extends AppCompatActivity {

    TextView textResult, textTotal;
    EditText editPergunta, editResposta, editId;
    Button btnQuiz, btnAddPergunta, btnAdd, btnBuscar, btnEditar;
    Layout layoutPesquisa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_editar_quiz);

        final TextView textResult =  findViewById(R.id.textResult);
        final TextView textTotal = findViewById(R.id.textTotal);

        editPergunta= findViewById(R.id.editPergunta);
        editResposta= findViewById(R.id.editResposta);
        editId = findViewById(R.id.editId);

        Button btnQuiz = findViewById(R.id.btnQuiz);
        btnAddPergunta = findViewById(R.id.btnAddPergunta);
        btnAdd = findViewById(R.id.btnAdd);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnEditar = findViewById(R.id.btnEditar);

        final DataBasePerguntas dataBase = new DataBasePerguntas(this);

        btnEditar.setVisibility(View.INVISIBLE);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editId.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Id vazio!", Toast.LENGTH_SHORT).show();
                    btnEditar.setVisibility(View.INVISIBLE);
                }else {

                    int id = Integer.parseInt(editId.getText().toString());
                    Pergunta p = dataBase.pegarPergunta(id);
                    if (p != null) {
                        btnEditar.setVisibility(View.VISIBLE);
                        editPergunta.setText(p.pergunta);
                        editResposta.setText(p.resposta);

                    } else {
                        btnEditar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), "Nada encontrado com o ID ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        btnQuiz.setOnClickListener(new View.OnClickListener() {
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
                if(editPergunta.getText().toString().isEmpty() || editResposta.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), " Campos em branco", Toast.LENGTH_SHORT).show();

                }else{
                    dataBase.addPergunta(new Pergunta(editPergunta.getText().toString(), editResposta.getText().toString().toUpperCase()));
                    //Limpa edit
                    editPergunta.setText("");
                    editResposta.setText("");
                    Toast.makeText(getApplicationContext(), " Pergunta Adicionada!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editPergunta.getText().toString().isEmpty() || editResposta.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), " Campos em branco", Toast.LENGTH_SHORT).show();

                }else {
                    int id = Integer.parseInt(editId.getText().toString());
                    String pergunta = editPergunta.getText().toString();
                    String resposta = editResposta.getText().toString().toUpperCase();

                    dataBase.updatePergunta(new Pergunta(id, pergunta, resposta));

                    Toast.makeText(getApplicationContext(), "Alterada!", Toast.LENGTH_SHORT).show();
                    //Limpa edit
                    editPergunta.setText("");
                    editResposta.setText("");
                    btnEditar.setVisibility(View.INVISIBLE);

                }

            }
        });

    }


}
