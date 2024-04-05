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
        values.put("isChecked", task.isChecked() ? 1 : 0); // Converte o estado do checkbox para 1 (true) ou 0 (false)

        // 5. Inserindo os valores no banco de dados e retornando o ID da nova linha inserida
        return db.insert("tarefas", null, values); // "tarefas" é o nome da tabela onde as tarefas são armazenadas
    }

    public List<Task> obterTodos() {
        List<Task> tasks = new ArrayList<>();
        Cursor cursor = db.query("tarefas", new String[]{"id", "texto", "isChecked"}, null, null, null, null, null);

        // Verifica se o cursor contém as colunas esperadas
        if (cursor == null || !cursor.moveToFirst()) {
            // Cursor vazio ou não contém dados
            return tasks;
        }

        do {
            Task task = new Task();
            int idIndex = cursor.getColumnIndex("id");
            int textoIndex = cursor.getColumnIndex("texto");
            int checkedIndex = cursor.getColumnIndex("isChecked");

            if (idIndex != -1 && textoIndex != -1 && checkedIndex != -1) {
                int id = cursor.getInt(idIndex);
                String texto = cursor.getString(textoIndex);
                boolean isChecked = cursor.getInt(checkedIndex) == 1; // Converte o valor do banco de dados para true ou false

                task.setId(id);
                task.setTexto(texto);
                task.setIsChecked(isChecked);

                tasks.add(task);
            }
        } while (cursor.moveToNext());

        cursor.close();
        return tasks;
    }

    public  void excluir(Task task){
        db.delete("tarefas", "id = ?", new String[]{ task.getId().toString()});
    }

    public void atualizar(Task task) {
        // Cria um ContentValues para armazenar os novos valores
        ContentValues values = new ContentValues();
        values.put("isChecked", task.isChecked() ? 1 : 0); // Converte o booleano isChecked para um valor inteiro (0 ou 1)

        // Executa a atualização no banco de dados
        db.update("tarefas", values, "id = ?", new String[] { String.valueOf(task.getId()) });
    }


}
