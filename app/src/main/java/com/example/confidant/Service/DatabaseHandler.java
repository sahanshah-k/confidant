package com.example.confidant.Service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.confidant.Domain.Details;
import com.example.confidant.Domain.Secrete;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "secreteDb";
    private static final String TABLE_DETAILS = "details";
    private static final String KEY_MAIL = "mail";
    private static final String KEY_NAME = "name";
    private static final String KEY_PIN = "pin";
    private static final String KEY_STATUS = "status";

    private static final String TABLE_SECRET= "secrete";
    private static final String SECRETE_ID = "id";
    private static final String SECRETE_NAME = "secreteName";
    private static final String SECRETE_KEY = "secreteKey";
    private static final String SECRETE_DESC = "secreteDesc";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_DETAILS_TABLE = "CREATE TABLE " + TABLE_DETAILS + "("
                + KEY_MAIL + " TEXT," + KEY_NAME + " TEXT,"
                + KEY_PIN + " INTEGER," + KEY_STATUS + " INTEGER" + ")";

        sqLiteDatabase.execSQL(CREATE_DETAILS_TABLE);

        String CREATE_SECRETE_TABLE = "CREATE TABLE " + TABLE_SECRET + "("
                + SECRETE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + SECRETE_NAME + " TEXT,"
                + SECRETE_KEY + " TEXT,"+SECRETE_DESC +" TEXT)";

        sqLiteDatabase.execSQL(CREATE_SECRETE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_DETAILS);

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_SECRET);

        onCreate(sqLiteDatabase);
    }

    public void addDetails(Details details) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, details.getName());
        values.put(KEY_MAIL, details.getMail());
        values.put(KEY_PIN, details.getPin());
        values.put(KEY_STATUS, details.getStatus());

        db.insert(TABLE_DETAILS, null, values);

        db.close();
    }

    public void addSecrete(Secrete secrete) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(SECRETE_NAME, secrete.getSecreteName());
        values.put(SECRETE_KEY, secrete.getSecreteKey());
        values.put(SECRETE_DESC, secrete.getDescription());
        db.insert(TABLE_SECRET, null, values);

        db.close();
    }
    public void updateSecrete(Secrete secrete,int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(SECRETE_NAME, secrete.getSecreteName());
        values.put(SECRETE_KEY, secrete.getSecreteKey());
        values.put(SECRETE_DESC, secrete.getDescription());
        db.update(TABLE_SECRET,values,"id="+id,null);
        db.close();
    }

    public void deleteSecrete(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SECRET,"id="+id,null);
        db.close();
    }

    public Details getDetails() {
        Details details = new Details();

        String selectQuery = "SELECT  * FROM " + TABLE_DETAILS + " LIMIT 1";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                details.setMail(cursor.getString(0));
                details.setName(cursor.getString(1));

            } while (cursor.moveToNext());
        }
        return details;

    }


    public Secrete getSecrete(int id) {
        Secrete secrete = new Secrete();

        String selectQuery = "SELECT  * FROM " + TABLE_SECRET + " WHERE "+SECRETE_ID + " = '" + id+"'";;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                secrete.setId(Integer.parseInt(cursor.getString(0)));
                secrete.setSecreteName(cursor.getString(1));
                secrete.setSecreteKey(cursor.getString(2));
                secrete.setDescription(cursor.getString(3));

            } while (cursor.moveToNext());
        }
        return secrete;
    }
    public List<String> getSecreteListTitle() {
        List<String> secreteListTitle = new ArrayList<String>();

        String selectQuery = "SELECT  * FROM " + TABLE_SECRET +" ORDER BY "+SECRETE_ID + " ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Secrete secrete = new Secrete();
                secrete.setId(Integer.parseInt(cursor.getString(0)));
                secrete.setSecreteName(cursor.getString(1));
                secrete.setSecreteKey(cursor.getString(2));

                secreteListTitle.add(secrete.getSecreteName());
            } while (cursor.moveToNext());
        }
        return secreteListTitle;
    }

    public List<Secrete> getSecreteListObjects() {
        List<Secrete> secreteListObjects = new ArrayList<Secrete>();

        String selectQuery = "SELECT  * FROM " + TABLE_SECRET +" ORDER BY "+SECRETE_ID + " ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Secrete secrete = new Secrete();
                secrete.setId(Integer.parseInt(cursor.getString(0)));
                secrete.setSecreteName(cursor.getString(1));
                secrete.setDescription(cursor.getString(2));
                secrete.setSecreteKey(cursor.getString(3));

                secreteListObjects.add(secrete);
            } while (cursor.moveToNext());
        }
        return secreteListObjects;
    }

    public long getDetailsCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, TABLE_DETAILS);
        db.close();
        return count;
    }

    public int verifyPin(String pin) {
        String countQuery = "SELECT  * FROM " + TABLE_DETAILS + " WHERE "+KEY_PIN + " = '" + pin+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
}
