package caetano.alves.galeria.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import caetano.alves.galeria.R;
import caetano.alves.galeria.activity.MainActivity;
import caetano.alves.galeria.model.MyItem;

public class MyAdapter extends RecyclerView.Adapter {
    MainActivity mainActivity; // Tela principal
    List<MyItem> itens; // Lista dos itens adicionados

    //Construtor da classe
    public MyAdapter(MainActivity mainActivity, List<MyItem> itens){
       this.mainActivity = mainActivity;
       this.itens = itens;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mainActivity); // Criando um inflater pra ler o XML da pagina inicial e criar os elementos de interface
        View v = inflater.inflate(R.layout.item_list,parent,false); // Criando os elementos de interface
        return new MyViewHolder(v); // Retornando o holder com os elementos da view
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyItem myItem = itens.get(position); // Selecionando o item que será atualizado/criado

        View v = holder.itemView; // Capturando o bloco do item

        ImageView imvPhoto = v.findViewById(R.id.imvPhoto); // Capturando o elemento da tela para adicionar a imagem
        imvPhoto.setImageURI(myItem.photo); // Adicionando a imagem no elemento de tela

        TextView tvTitle = v.findViewById(R.id.tvTitle); // Capturando o elemento da tela para adicionar o título
        tvTitle.setText(myItem.title); // Adicionando o título no elemento de tela

        TextView tvDesc = v.findViewById(R.id.tvDesc); // Capturando o elemento de tela para adicionar a descrição
        tvDesc.setText(myItem.description); // Adicionando a descrição
    }

    @Override
    public int getItemCount() {
        return itens.size();
    } // Retorna a quantidade de itens
}
