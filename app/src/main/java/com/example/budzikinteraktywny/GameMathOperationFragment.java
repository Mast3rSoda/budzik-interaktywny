package com.example.budzikinteraktywny;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class GameMathOperationFragment extends Fragment {
    private Random randomNumber = new Random();
    private String operationString;

    private int correctAnswer;
    TextView mathOperation;
    EditText mathAnswer;
    int intMathAnswer;
    Button submitButton;

    public GameMathOperationFragment() {
    }

    public static GameMathOperationFragment newInstance(String param1, String param2) {
        GameMathOperationFragment fragment = new GameMathOperationFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createOperation();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_math_operation, container, false);

        mathOperation = view.findViewById(R.id.mathGameOperation);
        mathAnswer = view.findViewById(R.id.mathGameAnswer);
        submitButton = view.findViewById(R.id.submitButton);
        mathOperation.setText(operationString);

        submitButton.setOnClickListener(v -> {
            String answerString = mathAnswer.getText().toString();

            try {
                intMathAnswer = Integer.parseInt(answerString);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), R.string.incorrectAnswer, Toast.LENGTH_SHORT).show();
                return;
            }

            if (intMathAnswer == correctAnswer) {
                Toast.makeText(getActivity(), R.string.correctAnswer, Toast.LENGTH_SHORT).show();
                Bundle result = new Bundle();
                result.putBoolean("isFinished", true);
                getParentFragmentManager().setFragmentResult("requestKey", result);
            }
            else {
                Toast.makeText(getActivity(), R.string.incorrectAnswer, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public void createOperation() {
        int num1;
        int num2;
        int num3;

        num1 = randomNumber.nextInt(150) + 1;
        num2 = randomNumber.nextInt(150) + 1;
        num3 = randomNumber.nextInt(150) + 1;

        if (randomNumber.nextInt(11) % 2 == 0) {
            correctAnswer = num1 + num2;
            operationString = num1 + " + " + num2;
        }
        else {
            correctAnswer = num1 - num2;
            operationString = num1 + " - " + num2;
        }

        if (randomNumber.nextInt(11) % 2 == 0) {
            correctAnswer = correctAnswer + num3;
            operationString = operationString + " + " + num3;
        }
        else {
            correctAnswer = correctAnswer - num3;
            operationString = operationString + " - " + num3;
        }

        operationString += " =";
    }
}