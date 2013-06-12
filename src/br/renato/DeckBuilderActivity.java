package br.renato;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Toast;
import br.renato.dao.CardDao;
import br.renato.dao.DeckDao;
import br.renato.model.Card;
import br.renato.model.Deck;

public class DeckBuilderActivity extends Activity {
	private ImageView cardImage;
	private EditText deckNameET;
	private Button saveButton;
	private Button backButton;
	private List<Card> cards;
	private List<Integer> numCards;
	private int currentCard;
	private Button nextCardButton;
	private Button previousCardButton;
	private NumberPicker numCardPicker;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.deck_builder);
		currentCard = 0;

		loadComponents();
		configureComponents();

		loadCards();
		
		changedCard(0);
	}

	private void loadCards() {
		CardDao cd = new CardDao(this);
		cards = cd.getCards();
		cd.close();
		numCards = new ArrayList<Integer>();
		
		for (int i = 0; i < cards.size(); i++) {
			numCards.add(i, 0);
		}		
	}

	private void configureComponents() {

		saveButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				String deckName = deckNameET.getText().toString();
				
				if (deckName == null || deckName.isEmpty())
					deckName = (new DeckDao(DeckBuilderActivity.this).countDecks()) + "";

				List <String> cardsIds = new ArrayList<String>();
				
				for (int i = 0; i < cards.size(); i++) {
					for (int j = 0; j < numCards.get(i); j++) {
						cardsIds.add(cards.get(i).getId()+"");
					}
				}
				
				if (cardsIds.size() >= 20) {
				    StringBuilder sb = new StringBuilder();
				    String sep = "";
				    for(String s: cardsIds) {
				        sb.append(sep).append(s);
				        sep = ",";
				    }			    
				    String cardsSeparatedByComma = sb.toString();
				    
				    Deck d = new Deck();
				    d.setName(deckName);
				    d.setCards(cardsSeparatedByComma);
				    
				    DeckDao dao = new DeckDao(DeckBuilderActivity.this);
				    dao.insert(d);
				    dao.close();
					
					startActivity(new Intent(DeckBuilderActivity.this,
							MainActivity.class));
				}
				
				else {
					Toast.makeText(DeckBuilderActivity.this, "Minimum is 20 cards", Toast.LENGTH_LONG).show();
				}
			}
		});

		backButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(DeckBuilderActivity.this,
						MainActivity.class));
			}
		});
		
		nextCardButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (currentCard < cards.size() - 1) {
					int previousCard = currentCard;
					currentCard++;
					changedCard(previousCard);
				}
			}
		});

		previousCardButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(currentCard > 0) {
					int previousCard = currentCard;
					currentCard--;
					changedCard(previousCard);
				}
			}
		});
		
		numCardPicker.setMinValue(0);
		numCardPicker.setMaxValue(2);
	}
	
	private void changeCardImage(Card c) {
		int drawableResourceId = c.getResId();
		Bitmap bmp = BitmapFactory.decodeResource(this.getResources(), drawableResourceId);
		cardImage.setImageBitmap(bmp);
	}

	private void changedCard(int previousCard) {
		numCards.set(previousCard, numCardPicker.getValue());
		
		if (currentCard == 0)
			previousCardButton.setVisibility(View.INVISIBLE);
		else
			previousCardButton.setVisibility(View.VISIBLE);

		if (currentCard == cards.size() - 1)
			nextCardButton.setVisibility(View.INVISIBLE);
		else
			nextCardButton.setVisibility(View.VISIBLE);
		
		changeCardImage(cards.get(currentCard));
		numCardPicker.setValue(numCards.get(currentCard));
	}

	private void loadComponents() {
		cardImage = (ImageView) findViewById(R.id.deck_builder_card);
		saveButton = (Button) findViewById(R.id.deck_builder_save);
		backButton = (Button) findViewById(R.id.deck_builder_back);
		deckNameET = (EditText) findViewById(R.id.deck_builder_deck_name);
		nextCardButton = (Button) findViewById(R.id.deck_builder_next_card);
		previousCardButton = (Button) findViewById(R.id.deck_builder_previous_card);
		numCardPicker = (NumberPicker) findViewById(R.id.deck_builder_number_picker);
	}
}