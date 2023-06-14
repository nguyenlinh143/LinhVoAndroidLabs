package algonquin.cst2335.vo000077;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * The MainActivity class represents the main activity of the application.
 *It handles the user interface and functionality for checking the complexity of a password.
 * It checks if the password contains an uppercase letter, a lowercase letter, a number, and a special character.
 * Based on the complexity check, it displays a message in a TextView.
 * @author julievo143
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {
    /**
     * Called when the activity is starting. Initializes the UI components and sets up the event listener for the button.
     *
     * @param savedInstanceState The saved instance state Bundle, or null if no previous state.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = findViewById(R.id.textView);
        EditText et = findViewById(R.id.editText);
        Button btn = findViewById(R.id.button);

        //add eventListener to check password is too simple or not
        btn.setOnClickListener(click ->{
            String password = et.getText().toString();
            checkPasswordComplexity(password);

            if(checkPasswordComplexity(password)){
                tv.setText("Your password is complex enough");
            }else {
                tv.setText("You shall not pass!");
            }
        });

    }

    /**
     * Checks if the password is too simple or not.
     *
     * @param password The String object representing the password to check.
     * @return true if the password meets the complexity requirements, false otherwise.
     */
    boolean checkPasswordComplexity(String password) {
        boolean foundUpperCase, foundLowerCase, foundNumber, foundSpecial;

        foundUpperCase = foundLowerCase = foundNumber = foundSpecial = false;
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);

            if (Character.isUpperCase(c)) {
                foundUpperCase = true;
            } else if (Character.isLowerCase(c)) {
                foundLowerCase = true;
            } else if (Character.isDigit(c)) {
                foundNumber = true;
            } else if (isSpecialCharacter(c)) {
                foundSpecial = true;
            }
        }
        if (!foundUpperCase) {
            Toast.makeText(getApplicationContext(), "Password is missing an uppercase letter", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!foundLowerCase) {
            Toast.makeText(getApplicationContext(), "Password is missing a lowercase letter", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!foundNumber) {
            Toast.makeText(getApplicationContext(), "Password is missing a number", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!foundSpecial) {
            Toast.makeText(getApplicationContext(), "Password is missing a special character", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
    /**
     * Checks if a character is a special character.
     *
     * @param c The character to check.
     * @return true if the character is a special character (#$%^&*!@?), false otherwise.
     */
    private static boolean isSpecialCharacter(char c) {
        // Return true if c is one of: #$%^&*!@?
        // Return false otherwise
        String specialCharacters = "#$%^&*!@?";
        return specialCharacters.contains(String.valueOf(c));
    }

}