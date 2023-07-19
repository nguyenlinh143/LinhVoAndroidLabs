package algonquin.cst2335.vo000077;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;

import algonquin.cst2335.vo000077.databinding.ActivityMainBinding;

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

    RequestQueue queue = null;   // for sending network requests:

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate( getLayoutInflater() );

        //This part goes at the top of the onCreate function:
        queue = Volley.newRequestQueue(this);


        //This part goes at the top of the onCreate function:
        RequestQueue queue = Volley.newRequestQueue(this); //like a constructor

        //loads an XML file on the page
        setContentView(  binding.getRoot()  );



        binding.forecastButton.setOnClickListener( click -> {

            String cityName = binding.cityTextField.getText().toString();
            String url = "https://api.openweathermap.org/data/2.5/weather?q=" +
                    URLEncoder.encode(cityName) //replace spaces, &. = with other characters
                    + "&appid=7e943c97096a9784391a981c4d878b22&units=metric&units=metric";
            JsonRequest request = new JsonObjectRequest(Request.Method.GET, url,null,
           //gets CALLs when SUCCESSFUL

            (response)->{
                JSONObject main ;

                try {
                    main = response.getJSONObject("main");
                    double current = main.getDouble("temp");
                    double min = main.getDouble("temp_min");
                    double max = main.getDouble("temp_max");
                    int humidity = main.getInt("humidity");

                                binding.temperatureTextView.setText("The current temperature is " + current + " degrees");
                              binding.temperatureTextView.setVisibility(View.VISIBLE);
                                binding.minTempTextView.setText("The min temperature is " + min + " degrees");
                               binding.minTempTextView.setVisibility(View.VISIBLE);
                                binding.maxTempTextView.setText("The max temperature is " + min + " degrees");
                                binding.maxTempTextView.setVisibility(View.VISIBLE);
                                binding.humidityTextView.setText("The humidity is " + humidity + " degrees");
                            binding.humidityTextView.setVisibility(View.VISIBLE);

                    JSONArray weatherArray = response.getJSONArray( "weather");



                    JSONObject pos0 = weatherArray.getJSONObject( 0 );
                    String  iconName = pos0.getString("icon");

                    String pictureURL = "http://openweathermap.org/img/w/" + iconName + ".png";
                    ImageRequest imgReq = new ImageRequest(pictureURL, new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap bitmap) {

                            int i = 0;
                        }
                    }, 1024, 1024, ImageView.ScaleType.CENTER, null,
                            (error ) -> {
                                int i = 0;
                            });

                    queue.add(imgReq);
                }
                catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            },

            //gets CALLs when SUCCESSFUL
                    (error) ->{
                        int i = 0;
                    }
            );
                    queue.add(request);
                }); ////run the web query







    }
}