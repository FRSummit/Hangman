package com.sammi.frsummit.hangman;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private char targetWord[] = {'S', 'O', 'M', 'E', 'O', 'N', 'E'};
    private char currentWord[];
    private ArrayList<String> words;
    private int mistakes = 0;
    private TextView promptText;

    private void drawHangman() {
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        Bitmap bitmap = Bitmap.createBitmap(400, 250, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        if (mistakes >= 1)
            canvas.drawCircle(200, 50, 40, paint);
        if (mistakes >= 2)
            canvas.drawLine(200, 90, 200, 120, paint);
        imageView.setImageDrawable(new BitmapDrawable(getResources(), bitmap));
    }

    private void generateWords() {
        DBHelper dbHelper = new DBHelper(this, null, null, 1);
        dbHelper.addWord("ARNOLD");
        dbHelper.addWord("NICHOLAS");
        dbHelper.addWord("JOHNY");
        dbHelper.addWord("TIMMY");

        words = dbHelper.getAllWords();
        words.add("THIS");
        words.add("IS");
        words.add("NEW");

        for (String w: words)
            System.out.println("** " + w);

        Collections.shuffle(words);
        String firstWord = words.get(0);
        targetWord = new char[firstWord.length()];
        for (int i = 0; i < targetWord.length; i++)
            targetWord[i] = firstWord.charAt(i);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("HERE I AM");
        promptText = (TextView) findViewById(R.id.prompt_text);
        generateWords();
        currentWord = new char[targetWord.length];
        for (int i = 0; i < currentWord.length; i++)
            currentWord[i] = '_';
        displayCurrentWord();
        drawHangman();
    }

    public void handleEClick(View view) {
        for (int i = 0; i < targetWord.length; i++) {
            if (targetWord[i] == 'E')
                currentWord[i] = 'E';
        }

        TextView word = (TextView) findViewById(R.id.word);
        word.setText(new String(currentWord));
    }

    private void displayCurrentWord() {
        TextView word = (TextView) findViewById(R.id.word);
        String displayString = "";
        for (int i = 0; i < currentWord.length; i++) {
            if (i != 0)
                displayString += " ";
            displayString += currentWord[i];
        }
        word.setText(displayString);
    }

    public void handleButtonClick(View view) {
        Button button = (Button) view;
        char buttonText = button.getText().toString().charAt(0);
        System.out.printf("User clicked %c button\n", buttonText);
        button.setEnabled(false);
        boolean found = false;
        for (int i = 0; i < targetWord.length; i++)
            if (targetWord[i] == buttonText) {
                currentWord[i] = buttonText;
                found = true;
            }

        if (!found)
            mistakes++;

        displayCurrentWord();
        drawHangman();
    }
}
