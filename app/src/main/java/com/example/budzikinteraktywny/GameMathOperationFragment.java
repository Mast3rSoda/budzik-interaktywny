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

import org.w3c.dom.Text;

import java.util.Random;

public class GameMathOperationFragment extends Fragment {

    private int correctAnswer;


    public static GameMathOperationFragment newInstance() {
        return new GameMathOperationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_game_math_operation, container, false);

        TextView mathOperation = view.findViewById(R.id.mathGameOperation);
        EditText mathAnswer = view.findViewById(R.id.mathGameAnswer);
        Button submitButton = view.findViewById(R.id.submitButton);
        mathOperation.setText(createOperation());

        submitButton.setOnClickListener(v -> {

                int intMathAnswer = Integer.parseInt(mathAnswer.getText().toString());
                Toast.makeText(getActivity(), R.string.incorrectAnswer, Toast.LENGTH_SHORT).show();

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

    public String createOperation() {

        Random randomNumber = new Random();
        int num1 = randomNumber.nextInt(150) + 1;
        int num2 = randomNumber.nextInt(150) + 1;
        int num3 = randomNumber.nextInt(150) + 1;

        String operationString;
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

        return operationString + " =";
    }
}