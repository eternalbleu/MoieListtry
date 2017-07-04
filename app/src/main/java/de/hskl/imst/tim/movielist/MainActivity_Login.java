package de.hskl.imst.tim.movielist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {
    private Button Blogin;
    public static final String EXTRA_MESSAGE = "example";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(activity_main_login);
    }
    public void Blogin(View view)
    {
        Intent intent = new Intent(MainActivity.this,MovieList.class);
        EditText editText =(EditText) findViewById(R.id.etUsername);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        MainActivity.this.startActivity(intent);
        // Bilder müssen noch reingefügt werden
    }
}