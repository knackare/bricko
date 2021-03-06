package com.example.bricko;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    private final int SQUARE_COUNT = 16;
    private final boolean game_over = false;
    private Square blank, current;
    private long start, end;
    private Boolean sound_on = false;
    private final Integer[] images = {
            R.drawable.puzzle_01, R.drawable.puzzle_02, R.drawable.puzzle_03, R.drawable.puzzle_04,
            R.drawable.puzzle_05, R.drawable.puzzle_06, R.drawable.puzzle_07, R.drawable.puzzle_08,
            R.drawable.puzzle_09, R.drawable.puzzle_10, R.drawable.puzzle_11, R.drawable.puzzle_12,
            R.drawable.puzzle_13, R.drawable.puzzle_14, R.drawable.puzzle_15, R.drawable.blank};
    private final Integer[] index = {
            0,  1,  2,  3,
            4,  5,  6,  7,
            8,  9,  10, 11,
            12, 13, 14, 15};
    private Square[] views;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Lägger till alla imagview formerna från activity_main.xml i Square klassen.
        views = new Square[]{
                new Square(findViewById(R.id.tileOne)), new Square(findViewById(R.id.tileTwo)), new Square(findViewById(R.id.tileThree)), new Square(findViewById(R.id.tileFour)),
                new Square(findViewById(R.id.tileFive)), new Square(findViewById(R.id.tileSix)), new Square(findViewById(R.id.tileSeven)), new Square(findViewById(R.id.tileEight)),
                new Square(findViewById(R.id.tileNine)),new Square(findViewById(R.id.tileTen)),new Square(findViewById(R.id.tileEleven)),new Square(findViewById(R.id.tileTwelve)),
                new Square(findViewById(R.id.tileThirteen)),new Square(findViewById(R.id.tileFourteen)),new Square(findViewById(R.id.tileFifteen)),new Square(findViewById(R.id.tileSixteen))};
    }

    public void playSound(Boolean play){
        if(sound_on){
            if(play){
                //mediaPlayer = MediaPlayer.create(this, R.raw.f_swipe);
            }
            if(!play){
                //mediaPlayer = MediaPlayer.create(this, R.raw.f_gover);
            }
            //mediaPlayer.start();
        }
        mediaPlayer = null;
    }

    public void timer(){
        Date start_time = new Date();
        start = start_time.getTime();
    }

    public void stopRound(View caller){
        TextView showTime = findViewById(R.id.time);
        Date end_time = new Date();
        end = end_time.getTime();
        int hundreds = (int) ((end-start)/10) % 100;
        int seconds = (int) ((end-start)/1000) % 60;
        int minutes = (int) ((end-start) / (1000*60)) % 60;
        String time_string = String.format(Locale.getDefault(),"%02d:%02d:%02d", minutes, seconds, hundreds);
        showTime.setText(time_string);
        sound_on = false;
    }

    public void startGame(View caller){
        timer();
        shuffle();
        addListener();
        sound_on = true;
        LogNumbersOrder();
    }

    private void LogNumbersOrder() {
        Log.v("INDEX", "----");

        for (Integer i : index) {
            Log.v("INDEX", i.toString());
        }

        Log.v("INDEX", "----");

        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        //Log.v("INDEX", String.join(", ", Stream.of(index).map(i -> i.toString()).toArray(String[]::new)));
        //}
    }

    //Aktivierar en klass som ger alla Squares en onClick funktion.
    private void addListener() {
        for (Square view : views) {
            view.getView().setOnClickListener(new PuzzleListener());
        }
    }

    //Slumpar runt alla index värden samt ger alla Square eller tiles ett start tag värde.
    private void shuffle() {
        List<Integer> ind = Arrays.asList(index);
        Collections.shuffle(ind);
        ind.toArray(index);

        for (int i = 0; i < SQUARE_COUNT; i++) {
            Integer number = this.index[i]; // "startnummer"
            views[i].getView().setImageResource(images[number]);
            views[i].getView().setTag(images[number]);
            views[i].setIndex(i); // index i matrisen
            views[i].setTag(number); // återanvänd startnummer som unikt id
            views[i].getView().setSoundEffectsEnabled(false);
        }
    }

    private Square getBlank() {
        Log.v("getBlank", String.format("Looking for %s", R.drawable.blank));
        for (Square view : views) {
            if ((int)view.getView().getTag() == R.drawable.blank) {
                Log.v("getBlank", String.format("Found %s", view.getView().getTag()));
                return view;
            }
        }
        return null;
    }

    private Square getSquare(int tag) {
        Log.v("getSquare", String.valueOf(tag));
        for (Square view : views) {
            if ((int) view.getView().getTag() == tag) {
                Log.v("getSquare", String.format("Found %s", view.getView().getTag()));
                return view;
            }
        }
        return null;
    }

    private Square getSquareAtIndex(int index) {
        for (Square view : views) {
            if (view.getIndex() == index) {
                return view;
            }
        }
        return null;
    }

    private boolean switchable() {
        final int current_index = current.getIndex();
        LogCurrentAndBlank("switchable", current_index);
        int[] valid_blanks;
        switch(current_index) {
            case 0:
                valid_blanks = new int[]{1,4};
                break;
            case 1:
                valid_blanks = new int[]{0,2,5};
                break;
            case 2:
                valid_blanks = new int[]{1,3,6};
                break;
            case 3:
                valid_blanks = new int[]{2,7};
                break;
            case 4:
                valid_blanks = new int[]{0,5,8};
                break;
            case 5:
                valid_blanks = new int[]{1,4,6,9};
                break;
            case 6:
                valid_blanks = new int[]{2,5,7,10};
                break;
            case 7:
                valid_blanks = new int[]{3,6,11};
                break;
            case 8:
                valid_blanks = new int[]{4,9,12};
                break;
            case 9:
                valid_blanks = new int[]{5,8,10,13};
                break;
            case 10:
                valid_blanks = new int[]{6,9,11,14};
                break;
            case 11:
                valid_blanks = new int[]{7,10,15};
                break;
            case 12:
                valid_blanks = new int[]{8,13};
                break;
            case 13:
                valid_blanks = new int[]{9,12,14};
                break;
            case 14:
                valid_blanks = new int[]{10,13,15};
                break;
            case 15:
                valid_blanks = new int[]{11,14};
                break;
            default:
                throw new IllegalStateException("Unexpected value");
        }
        for (int valid_blank : valid_blanks) {
            if (getSquareAtIndex(valid_blank).getTag() == blank.getTag()) {
                playSound(true);
                return true;
            }
        }
        playSound(false);
        return false;
    }

    private boolean allInPlace() {
        int correct_order = 0;
        for(int i = 0; i < SQUARE_COUNT; i++){
            if((int)views[i].getView().getTag() == images[i]){
                correct_order++;
            }
        }
        return correct_order == SQUARE_COUNT;
    }

    private void switchImages() {
        int current_tag = (int) current.getView().getTag();
        int new_blank_index = current.getIndex();
        int move_to_index = blank.getIndex();

        current.getView().setImageResource(R.drawable.blank);
        current.getView().setTag(R.drawable.blank);
        current.setIndex(new_blank_index);

        blank.getView().setImageResource(current_tag);
        blank.getView().setTag(current_tag);
        blank.setIndex(move_to_index);

        LogCurrentAndBlank("switchImages", move_to_index);
    }

    private void LogCurrentAndBlank(String origin, int currentIndex) {
        Log.v(origin, "current:" + currentIndex + ", blank:" + getBlank().getIndex());
    }

    //En klass inom en klass som gör det möjligt för denna klass att enkelt använda MainActivites alla
    //värden som current och blank, samt view arrayn. Om det hade varit en separat klass skulle man
    //behövt föra över all den information från den ena klassen till den andra.
    private class PuzzleListener implements View.OnClickListener {
        public void onClick(View caller) {
            int curr = (int) caller.getTag();
            current = getSquare(curr);
            blank = getBlank();

            if (switchable()) {
                switchImages();
            }

            if(allInPlace()) {
                System.out.println("You won!");
                sound_on = false;
            }
        }
    }
}
