package in.adithyaraam.wordamid;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class InformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i =getIntent();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if(i.getIntExtra("time",0)==1) {
            fab.setImageResource(android.R.drawable.ic_media_play);
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(InformationActivity.this,MainActivity.class);
                startActivity(i1);
                finish();
            }
        });
    }


    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(InformationActivity.this,MainActivity.class);
        startActivity(i);
        finish();
    }
}
