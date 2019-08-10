package in.adithyaraam.wordamid;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.desarrollodroide.libraryfragmenttransactionextended.FragmentTransactionExtended;

/**
 * Created by adith on 20-Jan-16.
 */
public class WordActivity extends AppCompatActivity {


    private Fragment mFirstFragment;
    TextView orderID,wrdID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_words);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        orderID = (TextView) findViewById(R.id.order_ID);
        wrdID = (TextView) findViewById(R.id.current_ID);
        Intent i = getIntent();
        int order = i.getIntExtra("order", 1);
        orderID.setText(""+order);
        if(order == 2)
        {
            wrdID.setText("160102000");
        }

        //Add first fragment
        mFirstFragment = new WordFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.fragment_place, mFirstFragment);
        fragmentTransaction.commit();

    }

    public void addTransition(View view) {
        Button button = (Button) findViewById(R.id.quitButton);
        if (getFragmentManager().getBackStackEntryCount()==0) {
            Fragment secondFragment = new MeaningFragment();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            FragmentTransactionExtended fragmentTransactionExtended = new FragmentTransactionExtended(this, fragmentTransaction, mFirstFragment, secondFragment, R.id.fragment_place);
            fragmentTransactionExtended.addTransition(FragmentTransactionExtended.FLIP_HORIZONTAL);
            fragmentTransactionExtended.commit();
            button.setText("EDIT");
        }else{
            getFragmentManager().popBackStack();
            button.setText("QUIT");
        }
    }

    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(WordActivity.this,MainActivity.class);
        startActivity(i);
        finish();
    }

}