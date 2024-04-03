package com.example.mytodoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TaskDAO {
    private final Conexao connection;
    private final SQLiteDatabase db;

    public TaskDAO(Context context){
        // 1. Criando uma instância de Conexao para gerenciar a conexão com o banco de dados
        connection = new Conexao(context, null);

        // 2. Obtendo uma referência para o banco de dados gravável
        db = connection.getWritableDatabase();
    }

    // 3. Método para inserir uma nova tarefa no banco de dados
    public long inserir(Task task){
        // 4. Criando um ContentValues para armazenar os valores da tarefa
        ContentValues values = new ContentValues();
        values.put("texto", task.getTexto()); // Colocando o texto da tarefa no ContentValues

        // 5. Inserindo os valores no banco de dados e retornando o ID da nova linha inserida
        return db.insert("tarefas", null, values); // "tarefas" é o nome da tabela onde as tarefas são armazenadas
    }

    public List<Task> obterTodos() {
        List<Task> tasks = new ArrayList<>();
        Cursor cursor = db.query("tarefas", new String[]{"id", "texto"}, null, null, null, null, null);

        while (cursor.moveToNext()) {
            Task task = new Task();
            int idIndex = cursor.getColumnIndex("id");
            int textoIndex = cursor.getColumnIndex("texto");

            if (idIndex != -1 && textoIndex != -1) {
                int id = cursor.getInt(idIndex);
                String texto = cursor.getString(textoIndex);

                task.setId(id);
                task.setTexto(texto);

                tasks.add(task);
            }
        }

        cursor.close();
        return tasks;
    }

    public  void excluir(Task task){
        db.delete("tarefas", "id = ?", new String[]{ task.getId().toString()});
    }
}
