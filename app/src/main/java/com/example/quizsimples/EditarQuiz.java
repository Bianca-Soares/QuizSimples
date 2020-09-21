package com.example.quizsimples;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class EditarQuiz extends AppCompatActivity {

    TextView textResult, textTotal;
    EditText editPergunta, editResposta, editId, editCategoria;
    Button btnQuiz, btnAddPergunta, btnAdd, btnBuscar, btnEditar;
    Layout layoutPesquisa;

    private static final String[] ALTERNATIVAS= new String[]{
            "A", "B", "C", "D"," "
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_editar_quiz);

        final TextView textResult =  findViewById(R.id.textResult);
        final TextView textTotal = findViewById(R.id.textTotal);

        editPergunta= findViewById(R.id.editPergunta);
        editId = findViewById(R.id.editId);
        editCategoria = findViewById(R.id.editCategoria);

        Button btnQuiz = findViewById(R.id.btnQuiz);
        btnAddPergunta = findViewById(R.id.btnAddPergunta);
        btnAdd = findViewById(R.id.btnAdd);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnEditar = findViewById(R.id.btnEditar);

        final Spinner spinnerAlternativas = findViewById(R.id.spinnerAlternativas);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, ALTERNATIVAS);
        spinnerAlternativas.setAdapter(adapter);

        spinnerAlternativas.setSelection(4);

        final DataBasePerguntas dataBase = new DataBasePerguntas(this);

        btnEditar.setVisibility(View.INVISIBLE);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editId.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Id vazio!", Toast.LENGTH_SHORT).show();
                    btnEditar.setVisibility(View.INVISIBLE);
                    editPergunta.setText("");
                    spinnerAlternativas.setSelection(4);
                    editCategoria.setText("");
                }else {

                    int id = Integer.parseInt(editId.getText().toString());
                    Pergunta p = dataBase.pegarPergunta(id);
                    if (p != null) {
                        btnEditar.setVisibility(View.VISIBLE);
                        editPergunta.setText(p.pergunta);
                        spinnerAlternativas.setSelection(alternativa(p));
                        editCategoria.setText(p.categoria);

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
                if(editPergunta.getText().toString().isEmpty() ||
                        editCategoria.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), " Campos em branco", Toast.LENGTH_SHORT).show();
                }else{
                    String pergunta = editPergunta.getText().toString();
                    String resposta = spinnerAlternativas.getSelectedItem().toString().toUpperCase();
                    String categoria = editCategoria.getText().toString().toUpperCase();
                    resposta = letraA(resposta);
                    dataBase.addPergunta(new Pergunta(pergunta, resposta, categoria));
                    //Limpa edit
                    editPergunta.setText("");
                    spinnerAlternativas.setSelection(4);
                    editCategoria.setText("");

                    Toast.makeText(getApplicationContext(), " Pergunta Adicionada!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editId.getText().toString().isEmpty() || editPergunta.getText().toString().isEmpty() ||
                         editCategoria.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), " Campos em branco", Toast.LENGTH_SHORT).show();
                    spinnerAlternativas.setSelection(0);
                }else {
                    int id = Integer.parseInt(editId.getText().toString());
                    String pergunta = editPergunta.getText().toString();
                    String resposta = spinnerAlternativas.getSelectedItem().toString().toUpperCase();
                    String categoria = editCategoria.getText().toString().toUpperCase();
                    resposta = letraA(resposta);
                    dataBase.updatePergunta(new Pergunta(id, pergunta, resposta, categoria));

                    Toast.makeText(getApplicationContext(), "Alterada!", Toast.LENGTH_SHORT).show();
                    //Limpa edit
                    editPergunta.setText("");
                    spinnerAlternativas.setSelection(4);
                    editCategoria.setText("");
                    btnEditar.setVisibility(View.INVISIBLE);

                }
            }
        });
    }

    public int alternativa(Pergunta p){
        int id = -1;

        for (int i = 0; i < ALTERNATIVAS.length; ++i)
        {
            if (p.getResposta().equals(ALTERNATIVAS[i]))
            {
                return i;
            }
        }
        return id;
    }

    public String letraA(String s){
        String a = s;
        if(a.equals(" "))
            a = "A";
        return a;

    }
}
