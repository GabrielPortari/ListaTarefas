package com.example.listatarefas.helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.listatarefas.model.Tarefa;

import java.util.ArrayList;
import java.util.List;

public class TarefaDAO implements ITarefaDAO{
    private SQLiteDatabase write;
    private SQLiteDatabase read;

    public TarefaDAO(Context context) {
        DBHelper db = new DBHelper(context);
        write = db.getWritableDatabase();
        read = db.getReadableDatabase();
    }

    @Override
    public boolean salvar(Tarefa tarefa) {
        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNomeTarefa());
        try{
            write.insert(DBHelper.TABELA_TAREFAS, null, cv);
            Log.i("INFO", "TAREFA " + tarefa.getNomeTarefa() + " SALVA COM SUCESSO");
        }catch (Exception e){
            Log.i("INFO", "ERRO AO SALVAR TAREFA");
            return false;
        }
        return true;
    }

    @Override
    public boolean atualizar(Tarefa tarefa) {
        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNomeTarefa());

        try{
            String[] args = {String.valueOf(tarefa.getId())};
            write.update(DBHelper.TABELA_TAREFAS, cv, "id=?", args);
            Log.i("INFO", "TAREFA " + tarefa.getNomeTarefa() + " ATUALIZADA COM SUCESSO");
        }catch (Exception e){
            Log.i("INFO", "ERRO AO ATUALIZAR TAREFA");
            return false;
        }
        return true;
    }

    @Override
    public boolean deletar(Tarefa tarefa) {
        try{
            String[] args = {String.valueOf(tarefa.getId())};
            write.delete(DBHelper.TABELA_TAREFAS, "id=?", args);
            Log.i("INFO", "TAREFA " + tarefa.getNomeTarefa() + " EXCLUIDA COM SUCESSO");
        }catch (Exception e){
            Log.i("INFO", "ERRO AO EXCLUIR TAREFA");
            return false;
        }
        return true;
    }

    @Override
    public List<Tarefa> listar() {
        List<Tarefa> tarefas = new ArrayList<>();

        String sql = "SELECT * FROM " + DBHelper.TABELA_TAREFAS + " ;";
        Cursor c = read.rawQuery(sql, null);

        while(c.moveToNext()){

            Tarefa tarefa = new Tarefa();

            Long id = c.getLong(c.getColumnIndex("id"));
            String nomeTarefa = c.getString(c.getColumnIndex("nome"));
            tarefa.setId(id);
            tarefa.setNomeTarefa(nomeTarefa);

            tarefas.add(tarefa);
        }
        return tarefas;
    }
    //DATA ACCESS OBJECT

}
