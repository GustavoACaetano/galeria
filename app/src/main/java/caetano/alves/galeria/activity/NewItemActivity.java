package caetano.alves.galeria.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import caetano.alves.galeria.R;
import caetano.alves.galeria.model.NewItemActivityViewModel;

public class NewItemActivity extends AppCompatActivity {
    static int PHOTO_PICKER_REQUEST = 1;
    Uri photoSelected = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

        NewItemActivityViewModel vm = new ViewModelProvider( this ).get(NewItemActivityViewModel.class ); // Obtendo o view model da new activity

        Uri selectPhotoLocation = vm.getSelectedPhotoLocation(); // Recebendo o URI guardado dentro do View Model

        if(selectPhotoLocation != null) { // Se a foto foi escolhida antes de rotacionar a tela
            ImageView imvfotoPreview = findViewById(R.id.imvFotoPreview); // Capturando o preview da imagem
            imvfotoPreview.setImageURI(selectPhotoLocation); // Definindo a foto no preview
        }


        ImageButton imbCI = findViewById(R.id.imbCI); // Capturando o botão de adicionar imagem
        imbCI.setOnClickListener(new View.OnClickListener() { // Configurando o clique do botão
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT); // Criando intenção para abrir um documento do aparelho
                photoPickerIntent.setType("image/*"); // Selecionando o tipo de documento como qualquer imagem
                startActivityForResult(photoPickerIntent, PHOTO_PICKER_REQUEST); // Iniciando a intenção e o resultado será a foto selecionada
            }
        });

        Button btnAddItem = findViewById(R.id.btnAddItem); // Capturando o botão de adicionar um item (foto, titulo e descriçao)
        btnAddItem.setOnClickListener(new View.OnClickListener() { // Configurando o clique do botão
            @Override
            public void onClick(View v) {
                if (photoSelected == null) { // Checando se alguma foto foi selecionada
                    Toast.makeText(NewItemActivity.this, "É necessário selecionar uma imagem!", Toast.LENGTH_LONG).show(); // Mensagem de erro caso não tenha foto
                    return;
                }

                EditText etTitle = findViewById(R.id.etTitle); // Capturando o edit text do titulo
                String title = etTitle.getText().toString(); // Armazenando o texto armazenado no campo do titulo
                if (title.isEmpty()) { // Checando se tinha algo escrito
                    Toast.makeText(NewItemActivity.this, "É necessário inserir um título", Toast.LENGTH_LONG).show(); // Mensagem de erro caso não tenha nada escrito
                    return;
                }

                EditText etDesc = findViewById(R.id.etDesc); // Capturando o edit text de descrição
                String description = etDesc.getText().toString(); // Armazenando o texto armazenado no campo de descriçao
                if (description.isEmpty()) { // Checando se tinha algo escrito
                    Toast.makeText(NewItemActivity.this, "É necessário inserir uma descrição", Toast.LENGTH_LONG).show(); // Mensagem de erro caso não tenha nada escrito
                    return;
                }

                Intent i = new Intent(); // Criando a intent para voltar a outra tela com o resultado/informações
                i.setData(photoSelected); // Colocando a foto escolhida como informação da intenção
                i.putExtra("title", title); // Adicionando o título no dicionário
                i.putExtra("description", description); // Adicionando a descrição no dicionário
                setResult(Activity.RESULT_OK, i); // Informando que o resultado terá dados sendo enviados
                finish(); // Fechando a activity. A intent será executada e a outra tela volta para principal
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_PICKER_REQUEST){ // Confere qual foi a chamada do start activity on result
            if (resultCode == Activity.RESULT_OK){ // Confere se o retorno da activity deu certo
                photoSelected = data.getData(); // Armazena a imagem escolhida
                ImageView imvFotoPreview = findViewById(R.id.imvFotoPreview); // Captura o elemento para preview da imagem na tela
                imvFotoPreview.setImageURI(photoSelected); // Define a imagem selecionada no elemento do preview

                NewItemActivityViewModel vm = new ViewModelProvider( this).get(NewItemActivityViewModel.class); // Obtendo o View Model da NewActivity
                vm.setSelectedPhotoLocation(photoSelected); // Armazenando o URI da imagem selecionada

            }
        }
    };
}