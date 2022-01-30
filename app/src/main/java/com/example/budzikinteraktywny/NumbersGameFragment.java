package com.example.budzikinteraktywny;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class NumbersGameFragment extends Fragment {
    private Random random = new Random();

    TextView randomNumber;

    private int winIterator = 0;
    private int intRandomNumber;
    private int randomNumberButtonIndex;
    private int randomOtherNumber;

    private int i = 0;

    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;
    Button button7;
    Button button8;
    Button button9;

    Button[] buttons = {
            button1,
            button2,
            button3,
            button4,
            button5,
            button6,
            button7,
            button8,
            button9
    };

    public NumbersGameFragment() {

    }

    public static NumbersGameFragment newInstance(String param1, String param2) {
        NumbersGameFragment fragment = new NumbersGameFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_numbers_game, container, false);

        buttons[0] = view.findViewById(R.id.button1);
        buttons[1] = view.findViewById(R.id.button2);
        buttons[2] = view.findViewById(R.id.button3);
        buttons[3] = view.findViewById(R.id.button4);
        buttons[4] = view.findViewById(R.id.button5);
        buttons[5] = view.findViewById(R.id.button6);
        buttons[6] = view.findViewById(R.id.button7);
        buttons[7] = view.findViewById(R.id.button8);
        buttons[8] = view.findViewById(R.id.button9);
        randomNumber = view.findViewById(R.id.randomNumber);

        displayNumbers();

        for (i=0;i<9;i++){
            final Button button = buttons[i];
            button.setOnClickListener(v -> {
                if (Integer.parseInt(button.getText().toString()) == intRandomNumber) {
                    winIterator++;
                    Log.d("numbersGame", "correct " + intRandomNumber);
                    Toast.makeText(getActivity(), R.string.correctAnswer, Toast.LENGTH_SHORT).show();
                    if (winIterator == 10) {
                        Bundle result = new Bundle();
                        result.putBoolean("isFinished", true);
                        getParentFragmentManager().setFragmentResult("requestKey", result);
                    }
                } else {
                    winIterator = 0;
                    Log.d("numbersGame", "incorrect " + intRandomNumber);
                    Toast.makeText(getActivity(), R.string.incorrectAnswer, Toast.LENGTH_SHORT).show();
                }
                displayNumbers();
            });
        }

        return view;
    }

    public void displayNumbers() {

        intRandomNumber = random.nextInt(100) + 1;
        randomNumberButtonIndex = random.nextInt(9);

        String stringRandomNumber = String.valueOf(intRandomNumber);
        randomNumber.setText(stringRandomNumber);

        buttons[randomNumberButtonIndex].setText(stringRandomNumber);
        for (int i = 0; i < 9; i++) {
            if (i == randomNumberButtonIndex) {
                continue;
            }
            do {
                randomOtherNumber = random.nextInt(100) + 1;
            } while (randomOtherNumber == intRandomNumber);
            buttons[i].setText(Integer.toString(randomOtherNumber));
        }

    }
}
