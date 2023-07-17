package com.example.listatarefas.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.listatarefas.adapter.Adapter;
import com.example.listatarefas.R;
import com.example.listatarefas.helper.DBHelper;
import com.example.listatarefas.helper.RecyclerItemClickListener;
import com.example.listatarefas.helper.TarefaDAO;
import com.example.listatarefas.model.Tarefa;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton fab;
    RecyclerView recyclerView;
    List<Tarefa> tarefas = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAdd = new Intent(getApplicationContext(), AddTarefa.class);
                startActivity(intentAdd);
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        DBHelper db = new DBHelper(getApplicationContext());
        ContentValues cv = new ContentValues();
        db.getWritableDatabase().insert(DBHelper.TABELA_TAREFAS, null, cv);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //edição dos itens
                    //Seleciona a tarefa que sera editada
                Tarefa terafaSelecionada = tarefas.get(position);
                    //envia os dados para a proxima tela
                Intent intent = new Intent(MainActivity.this, AddTarefa.class);
                intent.putExtra("tarefaSelecionada", terafaSelecionada);
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {
                //remover itens
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Confirmar exclusão");
                dialog.setMessage("Deseja excluir a tarefa " + tarefas.get(position).getNomeTarefa() + "?");

                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
                        if(tarefaDAO.deletar(tarefas.get(position))){
                            carregaTarefas();
                            Toast.makeText(MainActivity.this, "Sucesso ao excluir tarefa", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this, "Erro ao excluir tarefa", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.setNegativeButton("Não", null);
                dialog.create();
                dialog.show();
            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        }));


    }

    @Override
    protected void onStart() {
        carregaTarefas();
        super.onStart();
    }

    public void carregaTarefas(){

        TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
        tarefas = tarefaDAO.listar();

        Adapter adapter = new Adapter(tarefas);
        recyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
    }
}