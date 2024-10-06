package khetkaguru.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button btnExplore;
    private Button btnLearning;
    private Button btnBot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        btnExplore = findViewById(R.id.btnExplore);
        btnLearning = findViewById(R.id.btnLearning);
        btnBot = findViewById(R.id.btnBot);

        btnExplore.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ExploreActivity.class);
            startActivity(intent);
        });
        btnLearning.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LearningActivity.class);
            startActivity(intent);
        });
        btnBot.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ChatBotActivity.class);
            startActivity(intent); 
        });


    }
}