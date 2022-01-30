package com.example.budzikinteraktywny.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Collections;

import android.widget.ImageButton;

import com.example.budzikinteraktywny.R;

public class GameMemoryFragment extends Fragment {
    ImageButton[] buttons;
    ArrayList<Integer> images;
    int cardBlank;
    int visibleCards;
    boolean twoVisible;
    int lastCardId;
    int foundPairs;

    public static GameMemoryFragment newInstance() {
        return new GameMemoryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_memory, container, false);

        buttons = new ImageButton[]{
                view.findViewById(R.id.button1),
                view.findViewById(R.id.button2),
                view.findViewById(R.id.button3),
                view.findViewById(R.id.button4),
                view.findViewById(R.id.button5),
                view.findViewById(R.id.button6),
                view.findViewById(R.id.button7),
                view.findViewById(R.id.button8),
                view.findViewById(R.id.button9),
                view.findViewById(R.id.button10),
                view.findViewById(R.id.button11),
                view.findViewById(R.id.button12),
        };

        images = new ArrayList<Integer>();
        images.add(R.drawable.icon_bear);
        images.add(R.drawable.icon_cat);
        images.add(R.drawable.icon_cow);
        images.add(R.drawable.icon_dog);
        images.add(R.drawable.icon_fox);
        images.add(R.drawable.icon_tiger);
        images.add(R.drawable.icon_bear);
        images.add(R.drawable.icon_cat);
        images.add(R.drawable.icon_cow);
        images.add(R.drawable.icon_dog);
        images.add(R.drawable.icon_fox);
        images.add(R.drawable.icon_tiger);

        cardBlank = R.drawable.icon_blank;
        visibleCards = 0;
        twoVisible = false;
        lastCardId = -1;
        foundPairs = 0;

        Collections.shuffle(images);

        for(int i = 0; i < buttons.length; i++) {
            buttons[i].setBackgroundResource(R.drawable.icon_blank);
            buttons[i].setTag(cardBlank);
            setCardListener(i);
        }

        return view;
    }

    public void setCardListener(int i) {
        buttons[i].setOnClickListener(v -> {
            if (Integer.parseInt(buttons[i].getTag().toString()) == cardBlank && !twoVisible) {
                buttons[i].setBackgroundResource(images.get(i));
                buttons[i].setTag(images.get(i));

                if (visibleCards == 0)
                    lastCardId = i;

                visibleCards++;
            }
            else if (Integer.parseInt(buttons[i].getTag().toString()) != cardBlank) {
                buttons[i].setBackgroundResource(cardBlank);
                buttons[i].setTag(cardBlank);
                visibleCards--;

            }

            if (visibleCards == 2) {
                twoVisible = true;
                if (buttons[i].getTag() == buttons[lastCardId].getTag()) {
                    buttons[i].setClickable(false);
                    buttons[lastCardId].setClickable(false);
                    twoVisible = false;
                    visibleCards = 0;
                    foundPairs += 1;

                    if (foundPairs == 6)
                        sendFinishedMessage();
                }
            }
            else if (visibleCards == 0)
                twoVisible = false;
        });
    }

    public void sendFinishedMessage() {
            Bundle result = new Bundle();
            result.putBoolean("isFinished", true);
            getParentFragmentManager().setFragmentResult("requestKey", result);
        }
    }