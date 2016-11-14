package com.example.omar.notetaker;

import android.app.Dialog;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.omar.notetaker.DataModel.Note;
import com.example.omar.notetaker.SQLDatabase.NotesDataSource;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AddNoteFragment.OnFragmentInteractionListener {
    /**
     * User Interface Elements Declarations
     */
    Toolbar appToolbar ;
    ListView lvAllNotesList;
    String [] testNotesTitleList = {"Note 1", "Note 2", "Note 3" , "Note 4"} ;
    String [] testNotesDateList = {"date 1", "date 2", "date 3","date 4" } ;

    List<Note> allNotes= new ArrayList<Note>();

    /**
     * Data Source
     */
    NotesDataSource dbSource;
    /**
     * Array adapter
     */
    CustomListAllAdapter customListAllAdapter;
    /**
     *
      * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Setup the Database source
         */
        dbSource= new NotesDataSource(this);
        dbSource.open();
        /**
         * Setup the user interfaces elements
         */
        setupToolbar();
        /**
         * setup the List All Notes Adapter.
         */
        allNotes= dbSource.getAllNotes();
        customListAllAdapter = new CustomListAllAdapter(this,allNotes);
        lvAllNotesList.setAdapter(customListAllAdapter);

    }
    private void setupToolbar() {

        appToolbar=(Toolbar)findViewById(R.id.appToolbar);
        appToolbar.setTitle(R.string.theAppName);
        appToolbar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        setSupportActionBar(appToolbar);

        // link All Notes List
        lvAllNotesList = (ListView) findViewById(R.id.listAllNotes);

    }


    public void onClickFloatingButton(View view) {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.fragment_add_note);
        Button btnCancel= (Button) dialog.findViewById(R.id.btnCancel);
        Button btnAdd = (Button) dialog.findViewById(R.id.btnAdd);
        dialog.setTitle("Add New Note");
        final EditText etTitle = (EditText)dialog.findViewById(R.id.etNoteTitle) ;
        final EditText etText = (EditText)dialog.findViewById(R.id.etNoteText) ;



        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // cancel
                dialog.dismiss();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 String nTitle = etTitle.getText().toString();
                 String nText = etText.getText().toString();
                if(!nText.equals("") && !nTitle.equals("")){
                    // add to database
                    Note note =dbSource.addNewNote(nTitle,nText);
                    // add to the method
                    Toast.makeText(getApplicationContext(),note.getTitle()+" Added !",Toast.LENGTH_LONG).show();
                    customListAllAdapter.add(note);
                    customListAllAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }else{
                    Toast.makeText(dialog.getContext(),"fill the input plz!",Toast.LENGTH_SHORT).show();
                }

            }
        });
        dialog.show();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
