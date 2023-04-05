package caetano.alves.galeria.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

public class NewItemActivity extends AppCompatActivity {
    static int PHOTO_PICKER_REQUEST = 1;
    Uri photoSelected = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

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

                Intent i = new Intent();
                i.setData(photoSelected);
                i.putExtra("title", title);
                i.putExtra("description", description);
                setResult(Activity.RESULT_OK, i);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_PICKER_REQUEST){
            if (resultCode == Activity.RESULT_OK){
                photoSelected = data.getData();
                ImageView imvFotoPreview = findViewById(R.id.imvFotoPreview);
                imvFotoPreview.setImageURI(photoSelected);

            }
        }
    };
}