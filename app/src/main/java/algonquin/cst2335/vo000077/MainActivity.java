package algonquin.cst2335.vo000077;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import algonquin.cst2335.vo000077.databinding.ActivityMainBinding;
import data.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding variableBinding;
 MainActivityViewModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(this).get(MainActivityViewModel.class);

        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());

variableBinding.textview.setText(model.editString);

        //TextView myText = variableBinding.textview;
        Button myButton = variableBinding.mybutton;
        //EditText my_edit = variableBinding.myedittext;
        // replace findViewById(R.id.myedittext) = variableBinding.myedittext // to avoid mistakes
        myButton.setOnClickListener((click) ->{
            model.editString = variableBinding.myedittext.getText().toString();
          variableBinding.textview.setText("Your edit text: "+model.editString);
});
}}