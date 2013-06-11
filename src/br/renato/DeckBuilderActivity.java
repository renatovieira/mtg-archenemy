package br.renato;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
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
	private ListView cardsList;
	private List<Card> cards;
	private List<RatingBar> numC = new ArrayList<RatingBar>();

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
		CardDao cd = new CardDao(this);
		cards = cd.getCards();
		cd.close();
		
		
		ArrayAdapter<View> adapter = new ArrayAdapter<View>(this,
				android.R.layout.simple_list_item_1) {
			public View getView(int position, View convertView, ViewGroup parent) {
				final Card card = cards.get(position);
				View view = DeckBuilderActivity.this.getLayoutInflater()
						.inflate(R.layout.deck_builder_item, null);
				
				numC.add(position, (RatingBar) view.findViewById(R.id.deck_builder_item_number));
				
				TextView cardName = (TextView) view.findViewById(R.id.deck_builder_item_name);
				cardName.setText(card.getName());
				
				cardName.setOnClickListener(new View.OnClickListener() {
					
					public void onClick(View v) {
						Bitmap bmp = BitmapFactory.decodeResource(DeckBuilderActivity.this.getResources(), card.getResId());
						cardImage.setImageBitmap(bmp);
					}
				});
				
				return view;
			}
			
			public long getItemId(int position) {
				return cards.get(position).getId();
			}
			
			public int getCount() {
				return cards.size();
			}
		};
		
		cardsList.setAdapter(adapter);
	}

	private void configureButtons() {

		saveButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				String deckName = deckNameET.getText().toString();
				
				if (deckName == null || deckName.isEmpty())
					deckName = (new DeckDao(DeckBuilderActivity.this).countDecks()) + "";

				Adapter adapter = cardsList.getAdapter();
				
				List <String> cards = new ArrayList<String>();
				
				for (int i = 0; i < adapter.getCount(); i++) {
					long cardId = adapter.getItemId(i);
					for (int j = 0; j < (int) numC.get(i).getRating(); j++) {
						cards.add(cardId+"");
					}
				}
				
				if (cards.size() >= 20) {
				    StringBuilder sb = new StringBuilder();
				    String sep = "";
				    for(String s: cards) {
				        sb.append(sep).append(s);
				        sep = ",";
				    }			    
				    String cardsSeparatedByComma = sb.toString();
				    
				    Deck d = new Deck();
				    d.setName(deckName);
				    d.setCards(cardsSeparatedByComma);
				    
				    DeckDao dao = new DeckDao(DeckBuilderActivity.this);
				    dao.insert(d);
					
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
	}

	private void loadComponents() {
		cardImage = (ImageView) findViewById(R.id.deck_builder_card);
		saveButton = (Button) findViewById(R.id.deck_builder_save);
		backButton = (Button) findViewById(R.id.deck_builder_back);
		cardsList = (ListView) findViewById(R.id.deck_builder_card_list);
		deckNameET = (EditText) findViewById(R.id.deck_builder_deck_name);		
	}
}