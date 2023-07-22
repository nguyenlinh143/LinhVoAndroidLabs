package algonquin.cst2335.vo000077;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
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
                    "&appid=7e943c97096a9784391a981c4d878b22&units=metric";

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
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
                            binding.maxTempTextView.setText("The max temperature is " + max + " degrees");
                            binding.maxTempTextView.setVisibility(View.VISIBLE);
                            binding.humidityTextView.setText("The humidity is " + humidity + " %");
                            binding.humidityTextView.setVisibility(View.VISIBLE);




                            JSONArray weatherArray = response.getJSONArray("weather");
                            JSONObject pos0 = weatherArray.getJSONObject(0);
                          String description = pos0.getString("description");
                            String iconName = pos0.getString("icon");
                            String pictureURL = "http://openweathermap.org/img/w/" + iconName + ".png";

                            String pathname = getFilesDir() + "/" + iconName + ".png";
                            File file = new File(pathname);
                            final Bitmap[] image = {null}; // Initialize the variable to null

                            if (file.exists()) {
                                image[0] = BitmapFactory.decodeFile(pathname);
                            } else {
                                ImageRequest imgReq = new ImageRequest(pictureURL, bitmap -> {
                                    // Do something with loaded bitmap...
                                    image[0] = bitmap;
                                    try {
                                        FileOutputStream fOut = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);
                                        image[0].compress(Bitmap.CompressFormat.PNG, 100, fOut);
                                        fOut.flush();
                                        fOut.close();
                                        binding.icon.setImageBitmap(image[0]);
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }, 1024, 1024, ImageView.ScaleType.CENTER, null, error -> {
                                    // Handle error
                                    // error.printStackTrace();
                                });

                                queue.add(imgReq);
                            }

                            if (image[0] != null) {
                                binding.icon.setImageBitmap(image[0]);
                             binding.descriptionTextView.setText("Description : " + description);
                                binding.descriptionTextView.setVisibility(View.VISIBLE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }, error -> {
                // Handle error
                // error.printStackTrace();
            });

            queue.add(request);
        });
    }
}
