package com.example.listatarefas.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.listatarefas.R;
import com.example.listatarefas.helper.TarefaDAO;
import com.example.listatarefas.model.Tarefa;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class AddTarefa extends AppCompatActivity {
    private TextInputEditText textTarefa;
    private Tarefa tarefaEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tarefa);
        textTarefa = findViewById(R.id.editText_nomeTarefa);
        //Caso seja edição, recupera tarefa
        tarefaEdit = (Tarefa) getIntent().getSerializableExtra("tarefaSelecionada");
        if(tarefaEdit != null) {
            textTarefa.setText(tarefaEdit.getNomeTarefa());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.ItemSalvar) {
            TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
            if(tarefaEdit != null) { // EDITANDO TAREFA
                if(!textTarefa.getText().toString().isEmpty()) {
                    Tarefa tarefa = new Tarefa();
                    tarefa.setNomeTarefa(textTarefa.getText().toString());
                    tarefa.setId(tarefaEdit.getId());

                    //Atualizar no banco de dados
                    if(tarefaDAO.atualizar(tarefa)){
                        finish();
                        Toast.makeText(this, "Sucesso ao editar tarefa", Toast.LENGTH_SHORT).show();
                    }else{

                    }
                }
            }else { // ADICIONANDO TAREFA
                if(!textTarefa.getText().toString().isEmpty()) {

                    Tarefa tarefa = new Tarefa();
                    tarefa.setNomeTarefa(textTarefa.getText().toString());
                    if (tarefaDAO.salvar(tarefa)) {
                        finish();
                        Toast.makeText(this, "Sucesso ao adicionar tarefa", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Erro ao salvar tarefa", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }
}