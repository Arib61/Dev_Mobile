package com.example.mathquiz;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView number1TextView, number2TextView, feedbackTextView;
    private EditText answerEditText;
    private Button plusButton, minusButton, multiplyButton, checkButton, generateButton;
    private Random random = new Random();
    private int num1, num2;
    private char currentOperation = '+'; // opération actuelle sélectionnée

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation des vues
        number1TextView = findViewById(R.id.number1TextView);
        number2TextView = findViewById(R.id.number2TextView);
        answerEditText = findViewById(R.id.answerEditText);
        feedbackTextView = findViewById(R.id.feedbackTextView);
        plusButton = findViewById(R.id.plusButton);
        minusButton = findViewById(R.id.minusButton);
        multiplyButton = findViewById(R.id.multiplyButton);
        checkButton = findViewById(R.id.checkButton);
        generateButton = findViewById(R.id.generateButton);

        // Génération initiale
        generateNumbers();

        // Événements des boutons d'opération
        plusButton.setOnClickListener(v -> {
            currentOperation = '+';
            clearAnswerAndFeedback();
        });
        minusButton.setOnClickListener(v -> {
            currentOperation = '-';
            clearAnswerAndFeedback();
        });
        multiplyButton.setOnClickListener(v -> {
            currentOperation = '×';
            clearAnswerAndFeedback();
        });

        // Bouton Vérifier
        checkButton.setOnClickListener(v -> checkAnswer());

        // Bouton Générer
        generateButton.setOnClickListener(v -> {
            generateNumbers();
            clearAnswerAndFeedback();
        });
    }

    private void generateNumbers() {
        num1 = random.nextInt(889) + 111; // 111 à 999 inclus
        num2 = random.nextInt(889) + 111;
        number1TextView.setText(String.valueOf(num1));
        number2TextView.setText(String.valueOf(num2));
    }

    private void clearAnswerAndFeedback() {
        answerEditText.setText("");
        feedbackTextView.setText("");
    }

    private void checkAnswer() {
        String input = answerEditText.getText().toString().trim();

        if (input.isEmpty()) {
            feedbackTextView.setText("Veuillez entrer une réponse !");
            feedbackTextView.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            return;
        }

        try {
            int userAnswer = Integer.parseInt(input);
            int correctAnswer = calculateCorrectAnswer();

            if (userAnswer == correctAnswer) {
                feedbackTextView.setText("✅ Correct !");
                feedbackTextView.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
            } else {
                feedbackTextView.setText("❌ Faux ! La bonne réponse est : " + correctAnswer);
                feedbackTextView.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            }
        } catch (NumberFormatException e) {
            feedbackTextView.setText("Veuillez entrer un nombre valide.");
            feedbackTextView.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        }
    }

    private int calculateCorrectAnswer() {
        switch (currentOperation) {
            case '+': return num1 + num2;
            case '-': return num1 - num2;
            case '×': return num1 * num2;
            default: return 0;
        }
    }
}