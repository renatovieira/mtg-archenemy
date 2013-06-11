package br.renato.dao;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import br.renato.model.Card;
import br.renato.model.Deck;

public class DeckDao extends SQLiteOpenHelper {
	private static final int VERSION = 13;
	private static final String TABLE = "Deck";
	private static final String[] COLS = { "id", "name", "cards" };
	private Context context;

	public DeckDao(Context context) {
		super(context, TABLE, null, VERSION);
		this.context = context;
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

	public List<Card> getCards(String deckName) {
		List<Card> cards = new ArrayList<Card>();

		Cursor c = getWritableDatabase().query(TABLE, COLS, "name=?",
				new String[] { deckName }, null, null, null);

		if (c.moveToNext()) {
			CardDao cd = new CardDao(context);

			String[] allCards = c.getString(2).split(",");

			for (String cId : allCards) {
				Card ca = cd.searchById(cId);

				cards.add(ca);
			}
			cd.close();
		}
		c.close();
		return cards;
	}

	public List<Deck> getDecks() {
		List<Deck> decks = new LinkedList<Deck>();

		Cursor c = getWritableDatabase().query(TABLE, COLS, null, null, null,
				null, null);
		while (c.moveToNext()) {
			Deck d = new Deck();

			d.setId(c.getLong(0));
			d.setName(c.getString(1));
			d.setCards(c.getString(2));

			decks.add(d);
		}
		c.close();

		return decks;
	}
	
	public int countDecks() {
		Cursor c = getWritableDatabase().query(TABLE, COLS, null, null, null,
				null, null);
		
		int count = c.getCount();
		
		c.close();
		
		return count;
	}
}
