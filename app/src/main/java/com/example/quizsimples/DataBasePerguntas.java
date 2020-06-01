package com.example.quizsimples;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBasePerguntas extends SQLiteOpenHelper {

    //Variáveis
    private static final int DB_VERSION= 1;
    private static final String DB_NAME= "quizDB";
    private static final String DB_TABLE= "perguntas";
    private static final String KEY_ID= "id";
    private static final String KEY_PERGUNTA= "pergunta";
    private static final String KEY_RESPOSTA= "resposta";

    //método construtor
    public DataBasePerguntas(Context context) {
        super(context, DB_NAME,null, DB_VERSION);
    }

    //Criar tabela
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_PERGUNTA_TABLE = " CREATE TABLE " + DB_TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_PERGUNTA + " TEXT, "
                + KEY_RESPOSTA + " TEXT "
                + ")";
        db.execSQL(CREATE_PERGUNTA_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
        onCreate(db);
    }

    //MÉTODO adicionar

    public void addPergunta(Pergunta pergunta) {//classe contato usar os gets

        SQLiteDatabase db = this.getWritableDatabase();//modo escrita

        ContentValues values = new ContentValues();
        values.put(KEY_PERGUNTA, pergunta.getPergunta());
        values.put(KEY_RESPOSTA, pergunta.getResposta());

        db.insert(DB_TABLE, null, values);
        db.close();
    }

    //GET Pegar uma pergunta com cursores

    Pergunta pegarPergunta(int id) {
        SQLiteDatabase db = this.getReadableDatabase();//modo leitura

        Cursor cursor = db.query(DB_TABLE, new String[]{KEY_ID, KEY_PERGUNTA, KEY_RESPOSTA }, KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null,null, null);

        Pergunta pergunta = new Pergunta();

        if (cursor != null){
            cursor.moveToFirst();
            if(cursor.getCount() > 0){
                pergunta.setId(cursor.getInt(0));
                pergunta.setPergunta(cursor.getString(1));
                pergunta.setResposta(cursor.getString(2));

            }else{
                return null;
            }
        }
        return pergunta;
    }

    // Pegar todas as perguntas tipo List<Main> para o return
    public List<Pergunta> getAllPerguntas()
    {

        List<Pergunta>  perguntaArray = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + DB_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst())
        {
            do
            {
                Pergunta pergunta = new Pergunta();
                pergunta.setId(Integer.parseInt(cursor.getString(0)));
                pergunta.setPergunta(cursor.getString(1));
                pergunta.setResposta(cursor.getString(2));

                perguntaArray.add(pergunta);
            }
            while (cursor.moveToNext());
        }

        return perguntaArray;
    }

    public int updatePergunta (Pergunta pergunta)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values =  new ContentValues();
        values.put(KEY_PERGUNTA, pergunta.getPergunta());
        values.put(KEY_RESPOSTA, pergunta.getResposta());

        return db.update(DB_TABLE, values, KEY_ID + "=?", new String[] {String.valueOf(pergunta.getId())});
    }

    public void deletePergunta(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_TABLE, KEY_ID + "=?", new String[] {String.valueOf(id)});
        db.close();
    }

    public int getPerguntaCount()
    {
        String countQuery = "SELECT * FROM " + DB_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        return cursor.getCount();

    }
}
