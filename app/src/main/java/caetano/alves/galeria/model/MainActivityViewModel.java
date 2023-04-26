package caetano.alves.galeria.model;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

// Classe para armazenar as informações da main activity
public class MainActivityViewModel extends ViewModel {
    List<MyItem> itens = new ArrayList<>(); // A variável itens será uma array que irá armazenar as informações

    // Método para retornar os itens
    public List<MyItem> getItens() {
        return itens;
    }

}
