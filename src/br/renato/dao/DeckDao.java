package br.renato.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import br.renato.model.Card;
import br.renato.model.Deck;

public class DeckDao extends SQLiteOpenHelper {
	private static final int VERSION = 1;
	private static final String TABLE = "Deck";
	private static final String[] COLS = { "id", "name", "cards" };

	public DeckDao(Context context) {
		super(context, TABLE, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE " + TABLE + " ");
		sb.append("(id INTEGER PRIMARY KEY, ");
		sb.append(" name TEXT UNIQUE NOT NULL, ");
		sb.append(" cards TEXT NOT NULL);");

		db.execSQL(sb.toString());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		StringBuilder sb = new StringBuilder();
		sb.append("DROP TABLE IF EXISTS " + TABLE);
		db.execSQL(sb.toString());
		onCreate(db);
	}

	public void insert(Deck d) {
		ContentValues cv = new ContentValues();
		cv.put("name", d.getName());
		cv.put("cards", d.getCards());

		getWritableDatabase().insert(TABLE, null, cv);
	}

	public List<Card> getCards(Long deckId, Context ct) {
		List <Card> cards = new ArrayList<Card>();
				
		Cursor c = getWritableDatabase().query(TABLE, COLS, "id=?", new String[] { String.valueOf(deckId) }, null, null, null);
		
		CardDao cd = new CardDao(ct);
		
		String[] allCards = c.getString(2).split(",");
		
		for (String cId : allCards) {
			Card ca = cd.searchById(cId);
			
			cards.add(ca);
		}
		c.close();
		cd.close();
		
		return cards;
	}
}
