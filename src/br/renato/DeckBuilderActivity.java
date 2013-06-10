package br.renato;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class DeckBuilderActivity extends Activity {
	private ImageView card;
	private EditText deckNameET;
	private EditText deckInfoET;
	private Button saveButton;
	private Button backButton;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deck_builder);
        
        loadComponents();
        configureButtons();
        
        loadCards();
    }

	private void loadCards() {
		// TODO Carregar cartas
	}

	private void configureButtons() {
		
		saveButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				String deckName = deckNameET.getText().toString();
				String deckInfo = deckInfoET.getText().toString();

				//TODO Salvar o deck
				
				startActivity(new Intent(DeckBuilderActivity.this, DeckBuilderActivity.class));
			}
		});
		
		backButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(DeckBuilderActivity.this, DeckBuilderActivity.class));
			}
		});
	}

	private void loadComponents() {
		card = (ImageView) findViewById(R.id.deck_builder_card);
        deckNameET = (EditText) findViewById(R.id.deck_builder_deck_name);
        deckInfoET = (EditText) findViewById(R.id.deck_builder_deck_info);
        saveButton = (Button) findViewById(R.id.deck_builder_save);
        backButton = (Button) findViewById(R.id.deck_builder_back);
	}
}