package caetano.alves.galeria.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import caetano.alves.galeria.R;
import caetano.alves.galeria.adapter.MyAdapter;
import caetano.alves.galeria.model.MainActivityViewModel;
import caetano.alves.galeria.model.MyItem;
import caetano.alves.galeria.model.Util;

public class MainActivity extends AppCompatActivity {
    static int NEW_ITEM_REQUEST =1; // Identificador da chamada da activity for result
    MyAdapter myAdapter; // Adapter para recicler view

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fabAddItem = findViewById(R.id.fabAddNewItem); // Capturando o botão flutuante de adicionar novo item
        fabAddItem.setOnClickListener(new View.OnClickListener() { // Configurando o clique do botão para levar para nova tela
            @Override
            public void onClick(View view) {
                Intent i = new Intent (MainActivity.this, NewItemActivity.class); // Criar intenção de ir para nova tela
                startActivityForResult(i, NEW_ITEM_REQUEST); // Iniciar a intenção esperando uma resposta
            }
        });

        RecyclerView rvItens = findViewById(R.id.rvItens); // Capturando o recycler view

        MainActivityViewModel vm = new ViewModelProvider(this).get(MainActivityViewModel.class); // View Model de main activity é obtido
        List<MyItem> itens = vm.getItens(); // Lista obtida do view model

        myAdapter = new MyAdapter(this, itens); // Criando um adapter da tela principal com os itens recebidos do view model
        rvItens.setAdapter(myAdapter); // Colocando o adapter para a recycle view

        rvItens.setHasFixedSize(true); // Avisa que não há variação de tamanho entre os itens

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this); // Criando um gerenciador de layout linear
        rvItens.setLayoutManager(layoutManager); // Colocando o gerenciador no recycler view

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvItens.getContext(), DividerItemDecoration.VERTICAL); // Criando um decorador para separar os itens
        rvItens.addItemDecoration(dividerItemDecoration); // Adicionando o decorador no recycler view
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == NEW_ITEM_REQUEST) {
            if(resultCode == Activity.RESULT_OK) {
                MyItem myItem = new MyItem(); // Criando entidade para armazenar os dados do item

                // Armazenando as informações no item
                myItem.title = data.getStringExtra("title");
                myItem.description = data.getStringExtra("description");
                // Capturando o URI da imagem pra transformar em bitmap
                Uri selectedPhotoURI = data.getData();

                try { // Usando o try pra capturar um possível erro de não enc
                    Bitmap photo = Util.getBitmap(MainActivity.this, selectedPhotoURI, 100, 100); // Gravando o bitmap da imagem
                    myItem.photo = photo; // Armazenando a imagem no item
                } catch (FileNotFoundException e) { // Capturando o erro
                    e.printStackTrace();
                }

                MainActivityViewModel vm = new ViewModelProvider(this).get(MainActivityViewModel.class); // View Model de main activity é obtido
                List<MyItem> itens = vm.getItens(); // Lista obtida do view model

                itens.add(myItem); // Adicionando o item na lista
                myAdapter.notifyItemInserted(itens.size()-1); // Avisando o adapter do item incluido para ser exibido
            }
        }
    }
}