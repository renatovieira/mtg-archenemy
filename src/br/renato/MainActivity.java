package br.renato;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

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