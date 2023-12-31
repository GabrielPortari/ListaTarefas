package com.example.listatarefas.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static int VERSION = 1;
    public static String NOME_DB = "DB_TAREFAS";
    public static String TABELA_TAREFAS = "tarefas";

    public DBHelper(Context context) {
        super(context, NOME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABELA_TAREFAS + " (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT NOT NULL);";
        try {
            db.execSQL(sql);
            Log.i("Info DB", "Sucesso ao criar a tabela");
        }catch (Exception e){
            Log.i("Info DB", "Erro ao criar a tabela" + e.getMessage());
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABELA_TAREFAS + " ;";
        try {
            db.execSQL(sql);
            onCreate(db);
            Log.i("Info DB", "Sucesso ao atualizar o app");
        }catch (Exception e){
            Log.i("Info DB", "Erro ao atualizar o app" + e.getMessage());
        }
    }

}
