package data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

//correspond to what is on MainActivity
public class MainActivityViewModel extends ViewModel {
    public String editString;
    //Not hold direct variable. Using MutableLiveData<> variables
    // that are parameterized to whatever the variable type should be, in this case String
    // public MutableLiveData<String> editString = new MutableLiveData<>();
}




