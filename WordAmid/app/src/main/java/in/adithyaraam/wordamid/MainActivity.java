package in.adithyaraam.wordamid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void randomizeClick(View v){
        Intent i = new Intent(MainActivity.this, WordActivity.class);
        i.putExtra("order", 1);
        startActivity(i);
        finish();
    }

    public void alphabeticalClick(View v){
        Intent i = new Intent(MainActivity.this, WordActivity.class);
        i.putExtra("order",2);
        startActivity(i);
        finish();
    }

    public void unknownClick(View v){
        Intent i = new Intent(MainActivity.this, WordActivity.class);
        i.putExtra("order",3);
        startActivity(i);
        finish();
    }

    public void knownClick(View v){
        Intent i = new Intent(MainActivity.this, WordActivity.class);
        i.putExtra("order",4);
        startActivity(i);
        finish();
    }

    public void informationClick(View view) {
        Intent i = new Intent(MainActivity.this, InformationActivity.class);
        startActivity(i);
        finish();
    }
}
