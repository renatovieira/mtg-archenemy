package br.renato.model;

import java.util.HashMap;
import java.util.Map;

import br.renato.R;

public class Card {
	private Long id;
	private String name;
	private String imagefilename;
	private int ongoing;
	
	public static Map<String, Integer> nameToResId;
		
	static {
		nameToResId = new HashMap<String, Integer>();
		nameToResId.put("a_display_of_my_dark_power.jpg", R.drawable.a_display_of_my_dark_power);
		nameToResId.put("all_in_good_time.jpg", R.drawable.all_in_good_time);
		nameToResId.put("all_shall_smolder_in_my_wake.jpg", R.drawable.all_shall_smolder_in_my_wake);
		nameToResId.put("approach_my_molten_realm.jpg", R.drawable.approach_my_molten_realm);
		nameToResId.put("behold_the_power_of_destruction.jpg", R.drawable.behold_the_power_of_destruction);
		nameToResId.put("choose_your_champion.jpg", R.drawable.choose_your_champion);
		nameToResId.put("dance_pathetic_marionette.jpg", R.drawable.dance_pathetic_marionette);
		nameToResId.put("embrace_my_diabolical_vision.jpg", R.drawable.embrace_my_diabolical_vision);
		nameToResId.put("every_hope_shall_vanish.jpg", R.drawable.every_hope_shall_vanish);
		nameToResId.put("every_last_vestige_shall_rot.jpg", R.drawable.every_last_vestige_shall_rot);
		nameToResId.put("evil_comes_to_fruition.jpg", R.drawable.evil_comes_to_fruition);
		nameToResId.put("feed_the_machine.jpg", R.drawable.feed_the_machine);
		nameToResId.put("i_call_on_the_ancient_magics.jpg", R.drawable.i_call_on_the_ancient_magics);
		nameToResId.put("i_delight_in_your_convulsions.jpg", R.drawable.i_delight_in_your_convulsions);
		nameToResId.put("ignite_the_cloneforge.jpg", R.drawable.ignite_the_cloneforge);
		nameToResId.put("into_the_earthen_maw.jpg", R.drawable.into_the_earthen_maw);
		nameToResId.put("introductions_are_in_order.jpg", R.drawable.introductions_are_in_order);
		nameToResId.put("know_naught_but_fire.jpg", R.drawable.know_naught_but_fire);
		nameToResId.put("look_skyward_and_despair.jpg", R.drawable.look_skyward_and_despair);
		nameToResId.put("may_civilization_collapse.jpg", R.drawable.may_civilization_collapse);
		nameToResId.put("mortal_flesh_is_weak.jpg", R.drawable.mortal_flesh_is_weak);
		nameToResId.put("my_crushing_masterstroke.jpg", R.drawable.my_crushing_masterstroke);
		nameToResId.put("my_genius_knows_no_bounds.jpg", R.drawable.my_genius_knows_no_bounds);
		nameToResId.put("my_wish_is_your_command.jpg", R.drawable.my_wish_is_your_command);
		nameToResId.put("nature_demands_an_offering.jpg", R.drawable.nature_demands_an_offering);
		nameToResId.put("nature_shields_its_own.jpg", R.drawable.nature_demands_an_offering);
		nameToResId.put("nothing_can_stop_me_now.jpg", R.drawable.nothing_can_stop_me_now);
		nameToResId.put("og_i_bask_in_your_silent_awe.jpg", R.drawable.og_i_bask_in_your_silent_awe);
		nameToResId.put("og_i_know_all_i_see_all.jpg", R.drawable.og_i_know_all_i_see_all);
		nameToResId.put("og_my_undead_horde_awakens.jpg", R.drawable.og_my_undead_horde_awakens);
		nameToResId.put("og_the_very_soil_shall_shake.jpg", R.drawable.og_the_very_soil_shall_shake);
		nameToResId.put("only_blood_ends_your_nightmares.jpg", R.drawable.only_blood_ends_your_nightmares);
		nameToResId.put("realms_befitting_my_majesty.jpg", R.drawable.realms_befitting_my_majesty);
		nameToResId.put("roots_of_all_evil.jpg", R.drawable.roots_of_all_evil);
		nameToResId.put("rotted_ones_lay_siege.jpg", R.drawable.roots_of_all_evil);
		nameToResId.put("surrender_your_thoughts.jpg", R.drawable.surrender_your_thoughts);
		nameToResId.put("the_dead_shall_serve.jpg", R.drawable.the_dead_shall_serve);
		nameToResId.put("the_fate_of_the_flammable.jpg", R.drawable.the_fate_of_the_flammable);
		nameToResId.put("the_iron_guardian_stirs.jpg", R.drawable.the_iron_guardian_stirs);
		nameToResId.put("the_pieces_are_coming_together.jpg", R.drawable.the_pieces_are_coming_together);
		nameToResId.put("tooth_claw_and_tail.jpg", R.drawable.tooth_claw_and_tail);
		nameToResId.put("which_of_you_burns_brightest.jpg", R.drawable.which_of_you_burns_brightest);
		nameToResId.put("your_fate_is_thrice_sealed.jpg", R.drawable.your_fate_is_thrice_sealed);
		nameToResId.put("your_puny_minds_cannot_fathom.jpg", R.drawable.your_puny_minds_cannot_fathom);
		nameToResId.put("your_will_is_not_your_own.jpg", R.drawable.your_will_is_not_your_own);      
	}
	
	public int getResId () {
		return nameToResId.get(imagefilename);
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getOngoing() {
		return ongoing;
	}
	public void setOngoing(int ongoing) {
		this.ongoing = ongoing;
	}
	public String getImageFileName() {
		return imagefilename;
	}
	public void setImageFileName(String filename) {
		this.imagefilename = filename;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return id + "-" + imagefilename + " " + ongoing;
	}
}
