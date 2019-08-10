package in.adithyaraam.wordamid;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MeaningFragment extends Fragment {

    Button edit,mark;
    TextView wrdID,ordID,mngWrd, mngMeaning,mngMnemonic,mngSentence,mngMneTitle, mngSenTitle;
    String id;
    SQLiteDatabase db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setRetainInstance(true);

        View v = inflater.inflate(R.layout.fragment_meaning, container, false);

        edit = (Button) getActivity().findViewById(R.id.quitButton);
        mark = (Button) getActivity().findViewById(R.id.markButton);
        wrdID = (TextView) getActivity().findViewById(R.id.current_ID);
        ordID = (TextView) getActivity().findViewById(R.id.order_ID);
        mngWrd = (TextView) v.findViewById(R.id.mng_word);
        mngMeaning = (TextView) v.findViewById(R.id.mng_meaning);
        mngMnemonic = (TextView) v.findViewById(R.id.mng_mnemonic);
        mngSentence = (TextView) v.findViewById(R.id.mng_sentence);
        mngMneTitle = (TextView) v.findViewById(R.id.mng_mnemonicTitle);
        mngSenTitle = (TextView) v.findViewById(R.id.mng_sentenceTitle);

        id = (String) wrdID.getText();

        //Toast.makeText(getActivity(), id, Toast.LENGTH_LONG).show();

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getTableValues();

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), EditActivity.class);
                i.putExtra("order",Integer.parseInt(ordID.getText().toString()));
                i.putExtra("word",mngWrd.getText());
                i.putExtra("meaning",mngMeaning.getText());
                i.putExtra("wordID",Integer.parseInt(wrdID.getText().toString()));
                startActivity(i);
                getActivity().finish();
            }
        });

        mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int fetchID = Integer.parseInt(wrdID.getText().toString());
                try {
                    db = getActivity().openOrCreateDatabase("mydb.db", android.content.Context.MODE_PRIVATE, null);
                    String query = "UPDATE `words` SET `seen` = NOT `seen` WHERE `id` = " + fetchID;
                    db.execSQL(query);
                }
                catch (Exception e){
                    Log.e("Meaning Fragent","markClick Error"+e);
                }
                Toast.makeText(getActivity(),"Update Successful",Toast.LENGTH_SHORT).show();
                if(mark.getText().equals("MARK"))
                    mark.setText("UNMARK");
                else
                    mark.setText("MARK");
            }
        });
    }


    public void getTableValues() {


        try {

            db= getActivity().openOrCreateDatabase("mydb.db", android.content.Context.MODE_PRIVATE, null);
            Cursor allrows = db.rawQuery("SELECT * FROM `words` WHERE `id` = "+id, null);
            allrows.moveToFirst();

            //String id = allrows.getString(0);
            mngWrd.setText(allrows.getString(1));
            mngMeaning.setText(allrows.getString(2));
            String mne = allrows.getString(3);
            String sen = allrows.getString(4);
            if(mne.isEmpty())
            {
                mngMneTitle.setVisibility(View.GONE);
                mngMnemonic.setVisibility(View.GONE);
            }
            else
            {
                mngMneTitle.setVisibility(View.VISIBLE);
                mngMnemonic.setVisibility(View.VISIBLE);
                mngMnemonic.setText(mne);
            }

            if(sen.isEmpty())
            {
                mngSenTitle.setVisibility(View.GONE);
                mngSentence.setVisibility(View.GONE);
            }
            else
            {
                mngSenTitle.setVisibility(View.VISIBLE);
                mngSentence.setVisibility(View.VISIBLE);
                mngSentence.setText(sen);
            }

            allrows.close();
            db.close();

        }
        catch(Exception e)
        {
            Log.e("MEANING FRAGMENT ERROR", "" + e);
        }

    }



}