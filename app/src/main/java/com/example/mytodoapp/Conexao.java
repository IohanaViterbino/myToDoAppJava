package com.example.mytodoapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class Conexao extends SQLiteOpenHelper {
    private static final String name = "banco.db"; // Nome do arquivo do banco de dados
    private static final int version = 1; // Versão do banco de dados

    // Construtor da classe
    public Conexao(@Nullable Context context, @Nullable SQLiteDatabase.CursorFactory factory) {
        // Chamando o construtor da classe pai (SQLiteOpenHelper)
        super(context, name, factory, version);
    }

    // Método chamado quando o banco de dados é criado pela primeira vez
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Executando uma instrução SQL para criar a tabela 'tarefas', se ela ainda não existir
        db.execSQL("CREATE TABLE IF NOT EXISTS tarefas (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," + // Coluna ID (chave primária)
                "texto TEXT," + // Coluna texto
                "isChecked INTEGER DEFAULT 0)"); // Coluna isChecked
    }

    // Método chamado quando é necessário atualizar o banco de dados para uma nova versão
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Este método é chamado apenas se a versão do banco de dados mudar.
        // Aqui você pode implementar a lógica para atualizar o esquema do banco de dados.
        // Neste exemplo, deixamos o método vazio, o que significa que não fazemos nada quando o banco de dados é atualizado.
    }
}
