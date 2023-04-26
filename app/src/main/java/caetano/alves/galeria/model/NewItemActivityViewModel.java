package caetano.alves.galeria.model;

import android.net.Uri;

import androidx.lifecycle.ViewModel;

// Classe para armazenar a URI da foto escolhida
public class NewItemActivityViewModel extends ViewModel {

    Uri selectedPhotoLocation = null; // Variável para armazenar a URI

    // Método para obter a URI
    public Uri getSelectedPhotoLocation() {
        return selectedPhotoLocation;
    }

    // Método para definir a URI
    public void setSelectedPhotoLocation(Uri selectedPhotoLocation){
        this.selectedPhotoLocation = selectedPhotoLocation;
    }
}
