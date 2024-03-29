package com.example.budzikinteraktywny.fragment;

import android.content.res.Resources;
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

import com.example.budzikinteraktywny.R;

import java.util.ArrayList;
import java.util.Collections;

public class RewriteGameFragment extends Fragment {

    private String correctAnswer;
    TextView wordsSentence;
    EditText wordsAnswer;
    Button submitButton;

    public static RewriteGameFragment newInstance() {
        return new RewriteGameFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createSentence();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rewrite_game, container, false);

        wordsSentence = view.findViewById(R.id.rewriteGameSelectedWords);
        wordsAnswer = view.findViewById(R.id.rewriteGameAnswer);
        submitButton = view.findViewById(R.id.submitButton);
        wordsSentence.setText(correctAnswer);

        submitButton.setOnClickListener(v -> {
            String answerString = wordsAnswer.getText().toString();

            if (answerString.equals(correctAnswer)) {
                Log.d("rewriteGame", "correct " + correctAnswer);
                Toast.makeText(getActivity(), R.string.correctAnswer, Toast.LENGTH_SHORT).show();
                Bundle result = new Bundle();
                result.putBoolean("isFinished", true);
                getParentFragmentManager().setFragmentResult("requestKey", result);
            }
            else {
                Log.d("rewriteGame", "incorrect " + correctAnswer);
                Toast.makeText(getActivity(), R.string.incorrectAnswer, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public void createSentence() {
        Resources res = getResources();
        String[] words = res.getStringArray(R.array.rewriteGameWords);
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 1; i < 11; i++) {
            list.add(i);
        }
        Collections.shuffle(list);

        correctAnswer = words[list.get(0)] + " " +
                words[list.get(1)] + " " +
                words[list.get(2)] + " " +
                words[list.get(3)];
    }
}
