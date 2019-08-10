package in.adithyaraam.wordamid;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    SQLiteDatabase db;
    int wrdID;
    EditText mnemonic, sentence;
    TextView word,meaning;
    int order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Intent i = getIntent();
        wrdID = i.getIntExtra("wordID",160102001);
        order = i.getIntExtra("order",1);
        mnemonic = (EditText) findViewById(R.id.edt_mnemonic);
        sentence = (EditText) findViewById(R.id.edt_sentence);
        word = (TextView) findViewById(R.id.edt_word);
        meaning = (TextView) findViewById(R.id.edt_meaning);

        word.setText(i.getStringExtra("word"));
        meaning.setText(i.getStringExtra("meaning"));

    }

    public void saveClick(View v){
        int flag=1;
        try {
            db = openOrCreateDatabase("mydb.db", android.content.Context.MODE_PRIVATE, null);
            ContentValues values = new ContentValues();
            values.put("mnemonic", mnemonic.getText().toString());
            values.put("sentence", sentence.getText().toString());
            values.put("seen", "1");
            db.update("words", values, "id=" + wrdID, null);
        }
        catch (Exception e){
            Log.e("Edit Activity","saveClick Error"+e);
            flag = 0;
        }

        if (flag==1)
            Toast.makeText(EditActivity.this,"Update Successful",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(EditActivity.this,"Update Failed",Toast.LENGTH_SHORT).show();
        Intent i = new Intent(EditActivity.this,WordActivity.class);
        i.putExtra("order",order);
        startActivity(i);
        finish();
    }

    public void cancelClick(View view) {
        Intent i = new Intent(EditActivity.this,WordActivity.class);
        i.putExtra("order",order);
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(EditActivity.this,WordActivity.class);
        startActivity(i);
        finish();
    }
}
