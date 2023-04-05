package caetano.alves.galeria.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import caetano.alves.galeria.R;
import caetano.alves.galeria.adapter.MyAdapter;
import caetano.alves.galeria.model.MyItem;

public class MainActivity extends AppCompatActivity {
    static int NEW_ITEM_REQUEST =1; // Identificador da chamada da activity for result
    List<MyItem> itens = new ArrayList<>();
    MyAdapter myAdapter;

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

        myAdapter = new MyAdapter(this, itens);
        rvItens.setAdapter(myAdapter);

        rvItens.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvItens.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvItens.getContext(), DividerItemDecoration.VERTICAL);
        rvItens.addItemDecoration(dividerItemDecoration);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == NEW_ITEM_REQUEST) {
            if(resultCode == Activity.RESULT_OK) {
                MyItem myItem = new MyItem();
                myItem.title = data.getStringExtra("title");
                myItem.description = data.getStringExtra("description");
                myItem.photo = data.getData();
                itens.add(myItem);
                myAdapter.notifyItemInserted(itens.size()-1);
            }
        }
    }
}