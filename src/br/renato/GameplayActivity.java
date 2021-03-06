package br.renato;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import br.renato.dao.DeckDao;
import br.renato.model.Card;

public class GameplayActivity extends Activity {
	private ImageView card;
	private Button nextButton;
	private Button viewOngoingButton;
	private Button deleteOngoingButton;
	private Button newGameButton;
	
	private String deckName;
	private List<Card> library;
	private List<Card> discarded;
	private List<Card> ongoing;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
        	deckName = extras.getString("deckName");
        }
        
        loadComponents();
        
        configureButtons();
        
        setupDeck();
    }

	private void setupDeck() {
		DeckDao dao = new DeckDao(this);
		
		library = dao.getCards(deckName);
		
		dao.close();
		
		discarded = new ArrayList<Card>();
		ongoing = new ArrayList<Card>();
		
		Collections.shuffle(library);
		
		Card startingCard = library.remove(0);
		
		ongoing.add(startingCard);
		
		changeCard(startingCard);	
	}

	private void changeCard(Card c) {
		int drawableResourceId = c.getResId();
		Bitmap bmp = BitmapFactory.decodeResource(this.getResources(), drawableResourceId);
		card.setImageBitmap(bmp);
		
		if (ongoing.size() < 2)
			viewOngoingButton.setVisibility(View.INVISIBLE);
		else
			viewOngoingButton.setVisibility(View.VISIBLE);
		
		if (ongoing.size() > 1 || c.getOngoing() == 1)
			deleteOngoingButton.setVisibility(View.VISIBLE);
		else
			deleteOngoingButton.setVisibility(View.INVISIBLE);
	}

	private void configureButtons() {
		nextButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				List<Card> toRemove = new ArrayList<Card>();
				
				if (library.isEmpty()) {
					library = discarded;
					discarded = new LinkedList<Card>();
					Collections.shuffle(library);
				}
				
				for (Card c : ongoing) {
					if (c.getOngoing() == 0) {
						toRemove.add(c);
					}
				}
				
				for (Card c : toRemove) {
					ongoing.remove(c);
					discarded.add(c);
				}
				
				Card newCard = library.remove(0);
				ongoing.add(newCard);
				changeCard(newCard);	
			}
		});
		
		viewOngoingButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {				
				if (ongoing.size() > 1) {
					Card currentCard = ongoing.remove(0);
					ongoing.add(currentCard);
					
					changeCard(ongoing.get(0));					
				}
			}
		});
		
		deleteOngoingButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if (!ongoing.isEmpty() && ongoing.get(0).getOngoing() == 1) {
						discarded.add(ongoing.remove(0));
						
						if (!ongoing.isEmpty()) {
							changeCard(ongoing.get(0));
						}
						else {
							card.setImageBitmap(null);
							deleteOngoingButton.setVisibility(View.INVISIBLE);
						}
				}
			}
		});
		
		newGameButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				startActivity(new Intent(GameplayActivity.this, MainActivity.class));
			}
		});
	}

	private void loadComponents() {
		card = (ImageView) findViewById(R.id.card);
		nextButton = (Button) findViewById(R.id.next_card_button);
		viewOngoingButton = (Button) findViewById(R.id.view_ongoing_button);
		deleteOngoingButton = (Button) findViewById(R.id.delete_from_ongoing_button);
		newGameButton = (Button) findViewById(R.id.new_game_button);
	}
}