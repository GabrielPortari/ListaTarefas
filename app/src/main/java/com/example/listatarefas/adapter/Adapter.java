package com.example.listatarefas.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listatarefas.R;
import com.example.listatarefas.model.Tarefa;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    List<Tarefa> tarefas;

    public Adapter(List<Tarefa> tarefas) {
        this.tarefas = tarefas;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista, parent, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Tarefa tarefa = tarefas.get(position);
        holder.tarefa.setText(tarefa.getNomeTarefa());
    }

    @Override
    public int getItemCount() {
        if(tarefas.size() > 0){
            return tarefas.size();
        };
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tarefa;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tarefa = itemView.findViewById(R.id.text_itemLista);
        }
    }
}
