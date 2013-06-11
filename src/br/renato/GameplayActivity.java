package br.renato;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class GameplayActivity extends Activity {
	private ImageView card;
	private Button nextButton;
	private Button viewOngoingButton;
	private Button deleteOngoingButton;
	private Button newGameButton;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        
        loadComponents();
        
        configureButtons();
    }

	private void configureButtons() {
		nextButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				//TODO Carrega proxima carta, se deck estiver vazio embaralha as cartas que estao no descarte
			}
		});
		
		viewOngoingButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				//TODO cicla entra as cartas ativas, se tiver mais de uma
			}
		});
		
		deleteOngoingButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				//TODO deleta da lista de cartas ativas, se for ongoing
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