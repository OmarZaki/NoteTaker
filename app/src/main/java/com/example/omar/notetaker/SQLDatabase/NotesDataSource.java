package com.example.omar.notetaker.SQLDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.omar.notetaker.DataModel.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by OMAR on 11/11/2016.
 */

public class NotesDataSource {

    // database fields
    private SQLiteDatabase db ;
    private SQLNotesHelper dbHelper;
    private String[] allColumns = {SQLContacts.COLUMN_ID, SQLContacts.COLUMN_TITLE,SQLContacts.COLUMN_TEXT};

    // constructor

    public NotesDataSource(Context context) {
        dbHelper= new SQLNotesHelper(context);

    }
    // Open Database connection
    public void open() throws SQLException{
        db=dbHelper.getWritableDatabase();

    }
    // Close the database connection
    public void close(){
        dbHelper.close();
    }

    // add new note
    public Note addNewNote(String title, String text){
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLContacts.COLUMN_TITLE, title);
        contentValues.put(SQLContacts.COLUMN_TEXT, text);
        // insert Note and return the id of the inserted row
        long insertedId = db.insert(SQLContacts.TABLE_NAME , null, contentValues);

        Cursor cursor = db.query(SQLContacts.TABLE_NAME,allColumns,SQLContacts.COLUMN_ID+" = "+ insertedId, null
        ,null,null,null);
        cursor.moveToFirst();
        Note note = convertCursorToNote(cursor);
        cursor.close();
        return note;

    }
    // get all the list of Notes
    public List<Note> getAllNotes(){
        List<Note> listOfAllNotes= new ArrayList<Note>();

        Cursor cursor = db.query(SQLContacts.TABLE_NAME,allColumns,null,null,null,null,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Note note = convertCursorToNote(cursor);
            listOfAllNotes.add(note);
            cursor.moveToNext();
        }
        cursor.close();
        return listOfAllNotes;

    }

    // remove note by id
    public long removeNoteById(Note note){
        long deletedNote= db.delete(SQLContacts.TABLE_NAME,SQLContacts.COLUMN_ID+SQLContacts.EQUAL+"?",new String[]{String.valueOf(note.getId())});
        return deletedNote;
    }
    public Note getNoteById(Note note){
        String noteId = String.valueOf(note.getId());
        Cursor cursor = db.query(SQLContacts.TABLE_NAME,
                allColumns,
                SQLContacts.COLUMN_ID+SQLContacts.EQUAL+ noteId
                        , null,null,null,null);
        cursor.moveToFirst();
        Note selectedNote = convertCursorToNote(cursor);
        cursor.close();
        return note;
    }

    public long updateNoteById(Note note){
        String noteId = String.valueOf(note.getId());
        String[] Columns = {SQLContacts.COLUMN_TITLE,SQLContacts.COLUMN_TEXT};
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLContacts.COLUMN_TITLE, note.getTitle());
        contentValues.put(SQLContacts.COLUMN_TEXT, note.getText());
        long updatedId = db.update(SQLContacts.TABLE_NAME,
                contentValues,
                SQLContacts.COLUMN_ID+SQLContacts.EQUAL+"?",
                new String[]{noteId});
       return updatedId;
    }

    /**
     * Convert Cursor to Note Object.
     *
     * @param cursor
     * @return
     */
    private Note convertCursorToNote(Cursor cursor) {
        Note note = new Note();
        note.setId(cursor.getLong(0));
        note.setTitle(cursor.getString(1));
        note.setText(cursor.getString(2));
        return note;
    }
}
