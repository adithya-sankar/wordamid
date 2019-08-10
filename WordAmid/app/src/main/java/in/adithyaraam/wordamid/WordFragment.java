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


public class WordFragment extends Fragment {


    Button back,mark;
    SQLiteDatabase db;

    TextView wrdNme,wrdID,ordID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_word, container, false);

        back = (Button) getActivity().findViewById(R.id.quitButton);
        mark = (Button) getActivity().findViewById(R.id.markButton);
        wrdNme = (TextView) v.findViewById(R.id.word_textView);
        wrdID = (TextView) getActivity().findViewById(R.id.current_ID);
        ordID = (TextView) getActivity().findViewById(R.id.order_ID);

        getTableValues();
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MainActivity.class);
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
                    Log.e("Word Fragment", "markClick Error "+e);
                }
                Toast.makeText(getActivity(), "Update Successful", Toast.LENGTH_SHORT).show();
                if(mark.getText().equals("MARK"))
                    mark.setText("UNMARK");
                else
                    mark.setText("MARK");
            }
        });
    }

    public void getTableValues() {


        try {
            String query;
            db= getActivity().openOrCreateDatabase("mydb.db", android.content.Context.MODE_PRIVATE, null);
            int ord = Integer.parseInt(ordID.getText().toString());
            int fetchID;
            switch (ord)
            {
                case 1:
                    query = "SELECT * FROM `words` ORDER BY RANDOM() LIMIT 1";
                    break;
                case 2:
                    fetchID = Integer.parseInt(wrdID.getText().toString());
                    fetchID++;
                    query = "SELECT * FROM `words` WHERE `id` = "+fetchID;
                    break;
                case 3:
                    query = "SELECT * FROM `words` WHERE `seen` = 0 ORDER BY RANDOM() LIMIT 1";
                    break;
                case 4:
                    query = "SELECT * FROM `words`WHERE `seen` = 1 ORDER BY RANDOM() LIMIT 1";
                    break;
                default:
                    query = "SELECT * FROM `words` ORDER BY RANDOM() LIMIT 1";
                    break;
            }
            Cursor allrows = db.rawQuery(query, null);
            if(allrows.getCount()==0)
            {
                Toast.makeText(getActivity(),"No Words in this category yet",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
                getActivity().finish();
            }
            allrows.moveToFirst();

            String id = allrows.getString(0);
            String wordName = allrows.getString(1);

            wrdID.setText(id);
            wrdNme.setText(wordName);
            if (allrows.getInt(5)==1)
                mark.setText("UNMARK");
            else
                mark.setText("MARK");
            //Toast.makeText(getActivity(), id, Toast.LENGTH_LONG).show();

            allrows.close();
            db.close();

        }
        catch(Exception e)
        {
            Log.e("WORD FRAGMENT ERROR", "" + e);
            wrdID.setText("");
        }

    }


}