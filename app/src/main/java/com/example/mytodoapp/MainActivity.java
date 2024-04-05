package com.example.mytodoapp;

import android.support.v4.content.ContextCompat;
import android.support.v4.widget.CompoundButtonCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private LinearLayout listLayout;
    private TaskDAO dao;
    private List<Task> tarefas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialize o DAO
        dao = new TaskDAO(this);

        // Esconder a barra de status e a barra de navegação
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        // Inicialize os elementos da interface do usuário
        editText = findViewById(R.id.editText);
        Button addButton = findViewById(R.id.addButton);
        listLayout = findViewById(R.id.listLayout);

        // Adicione um listener ao botão de adicionar
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarTask();
                listarTarefas();
            }
        });

        // Liste todas as tarefas existentes no banco de dados
        listarTarefas();
    }
    private void salvarTask (){
        String texto = editText.getText().toString().trim();

        // Verifica se o texto não está vazio antes de salvar a tarefa
        if (!texto.isEmpty()) {
            Task task = new Task();
            task.setTexto(texto);
            task.setIsChecked(false);
            long id = dao.inserir(task);

            // Limpa o EditText após salvar a tarefa
            editText.getText().clear();
        } else {
            // Exibe uma mensagem para o usuário informando que o texto está vazio
            Toast.makeText(this, "Digite algo antes de salvar", Toast.LENGTH_SHORT).show();
        }
    }

    private void listarTarefas() {
        // Obtenha todas as tarefas do banco de dados
        tarefas = dao.obterTodos();

        // Limpe a lista de tarefas exibida na interface do usuário
        listLayout.removeAllViews();

        // Itere sobre a lista de tarefas e adicione cada uma delas à interface do usuário
        for (final Task task : tarefas) {
            final RelativeLayout itemLayout = new RelativeLayout(this);
            itemLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.gray));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(60, 30, 60, 0);
            itemLayout.setLayoutParams(layoutParams);
            itemLayout.setMinimumHeight(125);
            itemLayout.setBackgroundResource(R.drawable.rounded_checkbox);

            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(task.getTexto());
            checkBox.setTextSize(18);
            CompoundButtonCompat.setButtonTintList(checkBox, ContextCompat.getColorStateList(this, R.color.black));
            RelativeLayout.LayoutParams checkParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            checkParams.addRule(RelativeLayout.CENTER_VERTICAL);
            checkParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            checkParams.setMargins(15,15,0,15);
            checkBox.setLayoutParams(checkParams);
            checkBox.setChecked(task.isChecked()); // Define o estado do CheckBox de acordo com o valor armazenado no banco de dados
            itemLayout.addView(checkBox);

            Button deleteButton = new Button(this);
            deleteButton.setBackgroundResource(R.drawable.ic_x_delete);
            RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(
                    72,
                    72
            );
            buttonParams.addRule(RelativeLayout.CENTER_VERTICAL);
            buttonParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            buttonParams.setMarginEnd(25);
            deleteButton.setLayoutParams(buttonParams);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Excluir a tarefa do banco de dados
                    dao.excluir(task);
                    // Remover o item da lista na interface do usuário
                    listLayout.removeView(itemLayout);
                }
            });
            itemLayout.addView(deleteButton);

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // Atualiza o estado do CheckBox no banco de dados quando o usuário marcá-lo ou desmarcá-lo
                    task.setIsChecked(isChecked);
                    dao.atualizar(task);
                    // Atualiza a cor do texto do CheckBox de acordo com o estado
                    if (isChecked) {
                        checkBox.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.red));
                    } else {
                        checkBox.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.black));
                    }
                }
            });

            // Adicione o item à lista na interface do usuário
            listLayout.addView(itemLayout);
        }
    }

}
