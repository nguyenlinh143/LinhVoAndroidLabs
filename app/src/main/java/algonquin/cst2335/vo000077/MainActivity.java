package algonquin.cst2335.vo000077;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import algonquin.cst2335.vo000077.databinding.ActivityMainBinding;
import data.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding variableBinding;
    private MainActivityViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = new ViewModelProvider(this).get(MainActivityViewModel.class);

        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());
      variableBinding.textview.setText(model.editString.getValue());
        variableBinding.mybutton.setOnClickListener(click -> {
            model.editString.postValue(variableBinding.myedittext.getText().toString());
        });

        model.editString.observe(this, s -> {
            variableBinding.textview.setText("Your order: " + s);
        });

        model.isChecked.observe(this, selected -> {
            variableBinding.mycheckbox.setChecked( selected);
            variableBinding.myradiobutton.setChecked( selected);
            variableBinding.myswitch.setChecked(selected);

            Toast.makeText(this, "The value is now: " + selected, Toast.LENGTH_SHORT).show();
        });


        variableBinding.imageView.setOnClickListener(click -> {
            int width = variableBinding.imageView.getMeasuredWidth();
            int height = variableBinding.imageView.getMeasuredHeight();
            Toast.makeText(this, "The width = " + width + " and height = " + height, Toast.LENGTH_SHORT).show();
        });


        variableBinding.mycheckbox.setOnCheckedChangeListener((v, isChecked) -> model.isChecked.postValue(isChecked));
        variableBinding.myradiobutton.setOnCheckedChangeListener((v, isChecked) -> model.isChecked.postValue(isChecked));
        variableBinding.myswitch.setOnCheckedChangeListener((v, isChecked) -> model.isChecked.postValue(isChecked));
    }
}
