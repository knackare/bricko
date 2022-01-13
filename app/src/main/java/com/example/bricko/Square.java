package com.example.bricko;

import android.widget.ImageView;

public class Square {

    private ImageView view;
    private int index, tag;

    //Tar emot en ImageView i form av en tile.
    public Square(ImageView setView) {
        view = setView;
    }

    public ImageView getView() {
        return view;
    }

    //Sätter ett index som kopplas till varje ImageView.
    public void setIndex(int setTo) {
        index = setTo;
    }

    public int getIndex() {
        return index;
    }

    //Används för att ge varje ImageView ett tag värde, som inte är hård kodat in i själva ImageViewen.
    public void setTag(int setTo) {
        tag = setTo;
    }

    public int getTag() {
        return tag;
    }

}
