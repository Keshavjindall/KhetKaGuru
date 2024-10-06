package khetkaguru.mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.view.View;

import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ChatBotActivity extends AppCompatActivity {

    private EditText userInputEditText;
    private Button sendButton;
    private ListView chatListView;
    private ArrayAdapter<String> chatAdapter;
    private ArrayList<String> chatMessages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot);

        // Initialize views
        userInputEditText = findViewById(R.id.userInputEditText);
        sendButton = findViewById(R.id.sendButton);
        chatListView = findViewById(R.id.chatListView);

        // Initialize chat messages array and adapter
        chatMessages = new ArrayList<>();
        chatAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, chatMessages);
        chatListView.setAdapter(chatAdapter);

        String apiKey = getString(R.string.api_key);

        GenerativeModel gm = new GenerativeModel("gemini-1.5-flash", apiKey);
        GenerativeModelFutures model = GenerativeModelFutures.from(gm);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userInput = userInputEditText.getText().toString().trim();
                if (!userInput.isEmpty()) {
                    addUserMessage(userInput);
                    generateResponse(model, userInput);
                    userInputEditText.setText("");
                }
            }
        });
    }

    private void generateResponse(GenerativeModelFutures model, String userInput) {
        Content content = new Content.Builder().addText(userInput).build();

        Executor executor = Executors.newSingleThreadExecutor();

        ListenableFuture<GenerateContentResponse> response = model.generateContent(content);

        Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
            @Override
            public void onSuccess(GenerateContentResponse result) {
                String resultText = result.getText();
                addBotMessage(resultText);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                addBotMessage("Error generating response");
            }
        }, executor);
    }

    private void addUserMessage(String message) {
        View messageView = getLayoutInflater().inflate(R.layout.item_chat_message, null);

        LinearLayout userMessageContainer = messageView.findViewById(R.id.userMessageContainer);
        TextView userMessageTextView = messageView.findViewById(R.id.userMessageTextView);

        userMessageTextView.setText("You: " + message);
        userMessageContainer.setVisibility(View.VISIBLE);

        LinearLayout botMessageContainer = messageView.findViewById(R.id.botMessageContainer);
        botMessageContainer.setVisibility(View.GONE);

        chatMessages.add("You: " + message);
        chatAdapter.notifyDataSetChanged();
        chatListView.setSelection(chatMessages.size() - 1);
    }

    private void addBotMessage(String message) {
        View messageView = getLayoutInflater().inflate(R.layout.item_chat_message, null);

        LinearLayout botMessageContainer = messageView.findViewById(R.id.botMessageContainer);
        TextView botMessageTextView = messageView.findViewById(R.id.botMessageTextView);

        botMessageTextView.setText("Farmergpt: " + message);
        botMessageContainer.setVisibility(View.VISIBLE);

        LinearLayout userMessageContainer = messageView.findViewById(R.id.userMessageContainer);
        userMessageContainer.setVisibility(View.GONE);

        chatMessages.add("Farmergpt: " + message);
        runOnUiThread(() -> {
            chatAdapter.notifyDataSetChanged();
            chatListView.setSelection(chatMessages.size() - 1);
        });
    }
}