package br.renato;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import br.renato.dao.CardDao;
import br.renato.dao.DeckDao;
import br.renato.model.Card;
import br.renato.model.Deck;

public class MainActivity extends Activity {
	private Spinner deckSelector;
	private Button startButton;
	private Button deckBuilderButton;
	private String selectedDeck;
	private List<String> deckNames;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		loadComponents();

		configureDeckSelector();
		
		configureButtons();
		
		setupIfFirstRun();
	}

	private void setupIfFirstRun() {
		SharedPreferences preferences = getSharedPreferences("firstRun",
				MODE_PRIVATE);
		boolean firstRun = preferences.getBoolean("isFirstRun", true);

		if (firstRun) {
			insertAllCards();
			insertDefaultDecks();
		}
		Editor edit = preferences.edit();
		edit.putBoolean("isFirstRun", false);
		edit.commit();		
	}

	private void insertDefaultDecks() {
		DeckDao dao = new DeckDao(this);
		
		Deck d = new Deck();
		
		d.setName("Doomsday (Artifacts)");
		d.setCards("1,4,7,11,11,13,28,28,14,14,16,38,38,22,26,39,39,32,42,43");
		dao.insert(d);
		
		d.setName("Civilization (Ramp, Tramble, Def)");
		d.setCards("9,9,10,12,13,15,16,19,24,24,25,25,32,33,33,30,42,43,44,44");
		dao.insert(d);
		
		d.setName("Dragonfire (Dragon, Burn)");
		d.setCards("2,2,3,37,27,27,13,16,17,18,18,21,23,23,32,40,41,41,42,43");
		dao.insert(d);
		
		d.setName("Apocalypse (Removal, Graveyeard)"); 
		d.setCards("5,5,6,36,36,0,8,8,13,16,20,29,31,31,32,34,34,35,42,43");
		dao.insert(d);
		
		dao.close();
	}

	private void insertAllCards() {
		CardDao dao = new CardDao(this);
		
		Card card = new Card();
		
		String[] cardNames = getResources().getStringArray(
				R.array.cards);
		
		long count = 0;

		for (String s : cardNames) {
			card.setId(count++);
			card.setImageFileName(s + ".jpg");
			
			if (s.startsWith("og_")) {
				card.setOngoing(1);
				s = s.substring(3);
			}
			else
				card.setOngoing(0);
			
			String name = s.replace('_', ' ');
			name = toTitleCase(name);
			
			card.setName(name);
									
			dao.insert(card);
		}
		dao.close();
	}
	
	private String toTitleCase(String givenString) {
        String[] arr = givenString.split(" ");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < arr.length; i++) {
        	sb.append(Character.toUpperCase(arr[i].charAt(0))).append(arr[i].substring(1)).append(" ");
        }          
      return sb.toString().trim();
    } 

	private void configureButtons() {
		startButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, GameplayActivity.class);
				i.putExtra("deckName", selectedDeck);
				startActivity(i);
			}
		});
		
		deckBuilderButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, DeckBuilderActivity.class));
			}
		});
	}

	private void configureDeckSelector() {
		deckNames = new ArrayList<String>();
		
		DeckDao dao = new DeckDao(this);
		List <Deck> decks = dao.getDecks();
		dao.close();
		
		for (Deck d : decks) {
			deckNames.add(d.getName());
		}
		
		ArrayAdapter<String> decksAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, deckNames);

		deckSelector.setAdapter(decksAdapter);

		deckSelector.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parentView,
					View selectedItemView, int position, long id) {
				selectedDeck = deckNames.get(position);
			}

			public void onNothingSelected(AdapterView<?> parentView) {
				//Do nothing
			}
		});
	}

	private void loadComponents() {
		deckSelector = (Spinner) findViewById(R.id.deck_selector);
		startButton = (Button) findViewById(R.id.start_button);
		deckBuilderButton = (Button) findViewById(R.id.deck_builder_button);
	}
}