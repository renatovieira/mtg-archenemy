package br.renato;

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
import android.widget.TextView;
import android.widget.Toast;
import br.renato.dao.CardDao;
import br.renato.model.Card;

public class MainActivity extends Activity {
	private Spinner deckSelector;
	private TextView deckName;
	private Button startButton;
	private Button deckBuilderButton;
	private int selectedPosition;

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

		//TODO trocar para firstRun
		if (true) 
			insertAllCards();
		Editor edit = preferences.edit();
		edit.putBoolean("isFirstRun", false);
		edit.commit();		
	}

	private void insertAllCards() {
		CardDao dao = new CardDao(this);
		
		Card card = new Card();
		
		String[] cardNames = getResources().getStringArray(
				R.array.cards);

		for (String s : cardNames) {			
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
				//TODO Carrega a activity deck builder passando o id do deck para carregar
			}
		});
		
		deckBuilderButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, DeckBuilderActivity.class));
			}
		});
	}

	private void configureDeckSelector() {
		//TODO Carregar lista com nomes dos decks
		final String[] deckNames = { "Uno", "Dos", "Tres" };

		ArrayAdapter<String> decksAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, deckNames);

		deckSelector.setAdapter(decksAdapter);

		deckSelector.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parentView,
					View selectedItemView, int position, long id) {
				//TODO Carregar deck e mostrar descrição
				selectedPosition = position;
				deckName.setText(deckNames[selectedPosition]);
			}

			public void onNothingSelected(AdapterView<?> parentView) {
				//Do nothing
			}
		});
	}

	private void loadComponents() {
		deckSelector = (Spinner) findViewById(R.id.deck_selector);
		deckName = (TextView) findViewById(R.id.deck_info);
		startButton = (Button) findViewById(R.id.start_button);
		deckBuilderButton = (Button) findViewById(R.id.deck_builder_button);
	}
}