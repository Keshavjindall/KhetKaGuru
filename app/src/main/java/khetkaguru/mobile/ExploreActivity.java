package khetkaguru.mobile;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ExploreActivity extends AppCompatActivity {

    private TextView textViewWeather;
    private TextView textViewBestPractices;
    private Button btnCheck;

    private Spinner spinnerIndianStates, spinnerIndianCities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        textViewWeather = findViewById(R.id.textViewWeather);
        textViewBestPractices = findViewById(R.id.textViewBestPractices);
        btnCheck = findViewById(R.id.btnCheck);

        spinnerIndianStates = findViewById(R.id.spinner_indian_states);
        spinnerIndianCities = findViewById(R.id.spinner_indian_cities);

        String[] states = getResources().getStringArray(R.array.indian_states_array);

        ArrayAdapter<String> statesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, states);
        statesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIndianStates.setAdapter(statesAdapter);

        spinnerIndianStates.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedState = parent.getItemAtPosition(position).toString();

                populateCitiesSpinner(selectedState);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void populateCitiesSpinner(String selectedState) {
        String[] cities = {};

        switch (selectedState) {
            case "Andhra Pradesh":
                cities = new String[]{"Hyderabad", "Vijayawada", "Visakhapatnam", "Guntur"};
                break;
            case "Arunachal Pradesh":
                cities = new String[]{"Itanagar", "Tawang", "Ziro"};
                break;
            case "Assam":
                cities = new String[]{"Guwahati", "Silchar", "Dibrugarh", "Tezpur"};
                break;
            case "Bihar":
                cities = new String[]{"Patna", "Gaya", "Bhagalpur", "Muzaffarpur"};
                break;
            case "Chhattisgarh":
                cities = new String[]{"Raipur", "Bilaspur", "Korba", "Durg"};
                break;
            case "Goa":
                cities = new String[]{"Panaji", "Margao", "Vasco da Gama", "Mapusa"};
                break;
            case "Gujarat":
                cities = new String[]{"Ahmedabad", "Surat", "Vadodara", "Rajkot"};
                break;
            case "Haryana":
                cities = new String[]{"Chandigarh", "Gurgaon", "Faridabad", "Panipat"};
                break;
            case "Himachal Pradesh":
                cities = new String[]{"Shimla", "Manali", "Dharamshala", "Kullu"};
                break;
            case "Jharkhand":
                cities = new String[]{"Ranchi", "Jamshedpur", "Dhanbad", "Bokaro"};
                break;
            case "Karnataka":
                cities = new String[]{"Bangalore", "Mysore", "Mangalore", "Hubli"};
                break;
            case "Kerala":
                cities = new String[]{"Thiruvananthapuram", "Kochi", "Kozhikode", "Thrissur"};
                break;
            case "Madhya Pradesh":
                cities = new String[]{"Bhopal", "Indore", "Gwalior", "Jabalpur"};
                break;
            case "Maharashtra":
                cities = new String[]{"Mumbai", "Pune", "Nagpur", "Nashik"};
                break;
            case "Manipur":
                cities = new String[]{"Imphal", "Churachandpur", "Thoubal"};
                break;
            case "Meghalaya":
                cities = new String[]{"Shillong", "Tura", "Jowai"};
                break;
            case "Mizoram":
                cities = new String[]{"Aizawl", "Lunglei", "Champhai"};
                break;
            case "Nagaland":
                cities = new String[]{"Kohima", "Dimapur", "Mokokchung"};
                break;
            case "Odisha":
                cities = new String[]{"Bhubaneswar", "Cuttack", "Rourkela", "Sambalpur"};
                break;
            case "Punjab":
                cities = new String[]{"Amritsar", "Ludhiana", "Jalandhar", "Patiala"};
                break;
            case "Rajasthan":
                cities = new String[]{"Jaipur", "Jodhpur", "Udaipur", "Ajmer"};
                break;
            case "Sikkim":
                cities = new String[]{"Gangtok", "Gyalshing", "Mangan"};
                break;
            case "Tamil Nadu":
                cities = new String[]{"Chennai", "Coimbatore", "Madurai", "Tiruchirappalli"};
                break;
            case "Telangana":
                cities = new String[]{"Hyderabad", "Warangal", "Nizamabad", "Karimnagar"};
                break;
            case "Tripura":
                cities = new String[]{"Agartala", "Udaipur", "Dharmanagar"};
                break;
            case "Uttar Pradesh":
                cities = new String[]{"Lucknow", "Kanpur", "Agra", "Varanasi"};
                break;
            case "Uttarakhand":
                cities = new String[]{"Dehradun", "Haridwar", "Nainital", "Rishikesh"};
                break;
            case "West Bengal":
                cities = new String[]{"Kolkata", "Darjeeling", "Howrah", "Siliguri"};
                break;
            default:
                cities = new String[]{"Select a city"};
                break;
        }

        ArrayAdapter<String> citiesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cities);
        citiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIndianCities.setAdapter(citiesAdapter);

        final String[] selectedCity = new String[1];

        spinnerIndianCities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCity[0] = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!selectedCity[0].isEmpty()) {
                    fetchWeatherData(selectedCity[0]);
                } else {
                    textViewWeather.setText("Please select a city.");
                }
            }
        });
    }

    private void fetchWeatherData(String city) {
        WeatherService weatherService = ApiClient.getClient().create(WeatherService.class);
        Call<WeatherResponse> call = weatherService.getWeatherByCity(city, "bf464fd5299ffaf48e9e4d3ad851ef80", "metric");

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful()) {
                    WeatherResponse weatherResponse = response.body();
                    if (weatherResponse != null) {
                        double temp = weatherResponse.getMain().getTemp();
                        String weatherInfo = "Temperature: " + temp + "°C";
                        textViewWeather.setText(weatherInfo);

                        suggestBestFarmingPractices(temp);
                    }
                } else {
                    textViewWeather.setText("Error fetching weather data.");
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                textViewWeather.setText("Failed to fetch weather data.");
                Log.e("API_ERROR", "Network or other error: " + t.getMessage());
            }
        });
    }

    private void suggestBestFarmingPractices(double temp) {
        String practices;

        if (temp < 15) {
            practices = "The temperature is too cold. Best crops: Wheat, Barley. \n" +
                    "Best practices: \n- Use cold-resistant seeds.\n- Protect crops from frost.";
        } else if (temp >= 15 && temp < 25) {
            practices = "The temperature is moderate. Best crops: Rice, Cotton, Maize. \n" +
                    "Best practices: \n- Optimize irrigation.\n- Apply organic fertilizers.";
        } else if (temp >= 25 && temp < 35) {
            practices = "The temperature is warm. Best crops: Sugarcane, Millet. \n" +
                    "Best practices: \n- Ensure proper water supply.\n- Use heat-tolerant seeds.";
        } else {
            practices = "The temperature is too hot. Best crops: Sorghum, Pearl Millet. \n" +
                    "Best practices: \n- Irrigate during cooler parts of the day.\n- Apply mulch to reduce water loss.";
        }

        textViewBestPractices.setText(practices);
    }
}