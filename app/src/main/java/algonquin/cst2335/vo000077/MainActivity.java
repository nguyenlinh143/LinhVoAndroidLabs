package algonquin.cst2335.vo000077;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;

import algonquin.cst2335.vo000077.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        queue = Volley.newRequestQueue(this);

        binding.forecastButton.setOnClickListener(click -> {
            String cityName = binding.cityTextField.getText().toString();
            String url = "https://api.openweathermap.org/data/2.5/weather?q=" +
                    URLEncoder.encode(cityName) +
                    "&appid=7e943c97096a9784391a981c4d878b22&units=metric&units=metric";

            JsonRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    response -> {
                        try {
                            JSONObject main = response.getJSONObject("main");
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


                            JSONArray weatherArray = response.getJSONArray("weather");
                            JSONObject pos0 = weatherArray.getJSONObject(0);
                            String iconName = pos0.getString("icon");
                            String pictureURL = "http://openweathermap.org/img/w/" + iconName + ".png";

                            String pathname = getFilesDir()+"/"+iconName+"png";
                            File file = new File(pathname);
                            Bitmap image;
                            if(file.exists()){
                               image = BitmapFactory.decodeFile(pathname);
                             //   binding.descriptionTextView.setImageBitmap(image);
                           }
                           else {
                               ImageRequest imgReq = new ImageRequest(url, new Response.Listener>Bitmap<() {
                                   @Override
                                   public void onResponse(Bitmap bitmap) {
                                       // Do something with loaded bitmap...
                                       image = bitmap;
                                       try {
                                           image.compress(Bitmap.CompressFormat.PNG,100, MainActivity.this.openFileOutput(iconName +".png", Activity.MODE_PRIVATE))
                                       } catch (FileNotFoundException ex) {
                                           throw new RuntimeException(ex);
                                       }
                                   }
                                   catch(Exception e){

                                   }
                               }, 1024, 1024, ImageView.ScaleType.CENTER, null, error -> {
                                    // Handle error
                                  //  error.printStackTrace();
                                });

                                queue.add(imgReq);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }, error -> {
                // Handle error
                error.printStackTrace();
            });

            queue.add(request);
        });
    }
}