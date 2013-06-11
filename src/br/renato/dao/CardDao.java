package br.renato.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import br.renato.model.Card;

public class CardDao extends SQLiteOpenHelper {
	private static final int VERSION = 7;
	private static final String TABLE = "Card";
	private static final String[] COLS = {  "id", "name", "imagefilename", "ongoing" };

	public CardDao(Context context) {
		super(context, TABLE, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE " + TABLE + " ");
		sb.append("(id INTEGER PRIMARY KEY, ");
		sb.append(" name TEXT UNIQUE NOT NULL, ");
		sb.append(" imagefilename TEXT NOT NULL, ");
		sb.append(" ongoing INTEGER NOT NULL);");
		
		db.execSQL(sb.toString());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		StringBuilder sb = new StringBuilder();
		sb.append("DROP TABLE IF EXISTS " + TABLE);
		db.execSQL(sb.toString());
		onCreate(db);
	}
	
	public void insert(Card c) {				
		ContentValues cv = new ContentValues();
		cv.put("name", c.getName());
		cv.put("imagefilename", c.getImageFileName());
		cv.put("ongoing", c.getOngoing());
		
		getWritableDatabase().insert(TABLE, null, cv);
	}
	
	public List<Card> getCards() {
		List <Card> cards = new ArrayList<Card>();
		
		Cursor c = getWritableDatabase().query(TABLE, COLS, null, null, null, null, null);
		while (c.moveToNext()) {
			Card ca = new Card();
			ca.setId(c.getLong(0));
			ca.setName(c.getString(1));
			ca.setImageFileName(c.getString(2));
			ca.setOngoing(c.getInt(3));
			
			cards.add(ca);
		}
		c.close();
		
		return cards;
	}

	public Card searchById(String cId) {
		Cursor c = getWritableDatabase().query(TABLE, COLS, "id=?", new String[] { String.valueOf(cId) }, null, null, null);
		
		Card ca = new Card();
		ca.setId(c.getLong(0));
		ca.setName(c.getString(1));
		ca.setImageFileName(c.getString(2));
		ca.setOngoing(c.getInt(3));
		
		return ca;
	}
}
