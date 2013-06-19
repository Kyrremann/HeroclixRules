package no.heroclix.rules;

import heroclix.Rules.R;
import android.R.style;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class ImgListCreator extends Activity {

	private String NAME;
	private String GALLERY;
	private ListView lw;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);

		NAME = getIntent().getExtras().getString("NAME");
		GALLERY = getIntent().getExtras().getString("GALLERY");
		Log.d("List", NAME);

		final String[] rules;
		if (NAME.equals("speed_abilities"))
			rules = getResources()
					.getStringArray(R.array.speed_abilities_names);
		else if (NAME.equals("attack_abilities"))
			rules = getResources().getStringArray(
					R.array.attack_abilities_names);
		else if (NAME.equals("defense_abilities"))
			rules = getResources().getStringArray(
					R.array.defense_abilities_names);
		else if (NAME.equals("damage_abilities"))
			rules = getResources().getStringArray(
					R.array.damage_abilities_names);
		else if (NAME.equals("team_abilities"))
			rules = getResources().getStringArray(R.array.ta_names);
		// else rules = getResources().getStringArray(R.array.ta_names);
		else if (NAME.equals("ata"))
			rules = getResources().getStringArray(R.array.ata_names);
		else if (NAME.equals("abilities"))
			rules = getResources().getStringArray(R.array.abilities_names);
		else if (NAME.equals("map"))
			rules = getResources().getStringArray(R.array.map_rules_names);
		else if (NAME.equals("feats"))
			rules = getResources().getStringArray(R.array.feats_names);
		else if (NAME.equals("bfc"))
			rules = getResources().getStringArray(R.array.bfc_names);
		else if (NAME.equals("objects"))
			rules = getResources().getStringArray(R.array.objects_names);
		else if (NAME.equals("glossary"))
			rules = getResources().getStringArray(R.array.glossary_names);
		// else if(NAME.equals("unlisted_keywords")) rules =
		// getResources().getStringArray(R.array.unlisted_keywords_names);
		else
			rules = getResources().getStringArray(
					R.array.unlisted_keywords_names);

		lw = (ListView) findViewById(android.R.id.list);
		lw.setAdapter(new MyAdapter(this, rules, GALLERY));
		lw.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> av, View v, int position,
					long id) {
				Intent myIntent = new Intent(getApplicationContext(),
						ListInfo.class);
				myIntent.putExtra("NAME", NAME);
				myIntent.putExtra("POSITION", position);
				myIntent.putExtra("SIZE", rules.length);
				startActivityForResult(myIntent, 0);
			}

		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.d("resultCode", "" + resultCode);
		Database myDbHelper = new Database(this);
		myDbHelper.open();
		Cursor c = myDbHelper.getTable("centerList");
		c.moveToFirst();
		if (1 == c.getInt(1))
			lw.setSelection(resultCode);
		c.close();
		myDbHelper.close();
	}

	class MyAdapter extends BaseAdapter {

		Context mContext;
		String[] ruleList;
		private Integer[] teamImg = { R.drawable.ta_2000ad,
				R.drawable.ta_arachnos, R.drawable.ta_assassins,
				R.drawable.ta_avengers, R.drawable.ta_avengers_initiative,
				R.drawable.ta_batman_ally, R.drawable.ta_batman_enemy,
				R.drawable.ta_brotherhood, R.drawable.ta_bprd,
				R.drawable.ta_calculator, R.drawable.ta_cog,
				R.drawable.ta_covenant_empire, R.drawable.ta_crime_syndicate,
				R.drawable.ta_crossgen, R.drawable.ta_crusaders,
				R.drawable.ta_danger_girl, R.drawable.ta_defenders,
				R.drawable.ta_dominion, R.drawable.ta_fantastic_four,
				R.drawable.ta_federation, R.drawable.ta_federation_away_team,
				R.drawable.ta_federation_support_team, R.drawable.ta_founders,
				R.drawable.ta_freedom_phalanx,
				R.drawable.ta_green_lantern_corps,
				R.drawable.ta_guardians_of_the_globe, R.drawable.ta_hydra,
				R.drawable.ta_hypertime, R.drawable.ta_injustice_league,
				R.drawable.ta_justice_league, R.drawable.ta_justice_society,
				R.drawable.ta_kabuki, R.drawable.ta_kingdom_come,
				R.drawable.ta_klingon_empire,
				R.drawable.ta_legion_of_super_heroes,
				R.drawable.ta_locust_hord, R.drawable.ta_masters_of_evil,
				R.drawable.ta_minions_of_doom, R.drawable.ta_morlocks,
				R.drawable.ta_mystics, R.drawable.ta_outsiders,
				R.drawable.ta_police_department, R.drawable.ta_power_cosmic,
				R.drawable.ta_quintessence, R.drawable.ta_romulan,
				R.drawable.ta_shield, R.drawable.ta_serpent_society,
				R.drawable.ta_sinister_syndicate, R.drawable.ta_skrull,
				R.drawable.ta_spider_man_ally, R.drawable.ta_street_fighter,
				R.drawable.ta_suicide_squad, R.drawable.ta_superman_ally,
				R.drawable.ta_superman_enemy, R.drawable.ta_templars,
				R.drawable.ta_teen_titans, R.drawable.ta_top_cow,
				R.drawable.ta_ultimates, R.drawable.ta_ultimate_x_men,
				R.drawable.ta_underworld, R.drawable.ta_unsc,
				R.drawable.ta_vox_populi, R.drawable.ta_watchmen,
				R.drawable.ta_x_men };

		private Integer[] powerImg = { R.drawable.pa_red, R.drawable.pa_orange,
				R.drawable.pa_yellow, R.drawable.pa_lime, R.drawable.pa_green,
				R.drawable.pa_turquois, R.drawable.pa_blue,
				R.drawable.pa_purple, R.drawable.pa_pink, R.drawable.pa_brown,
				R.drawable.pa_black, R.drawable.pa_grey };

		// private Database myDbHelper;
		private String GALLERY;

		public MyAdapter(Context context, String[] list, String GALLERY) {
			mContext = context;
			ruleList = list;
			this.GALLERY = GALLERY;
		}

		public int getCount() {
			return ruleList.length;
		}

		public Object getItem(int id) {
			return id;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			RelativeLayout layout = new RelativeLayout(mContext);

			LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT);
			LayoutParams imgParams = new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

			TextView tv = new TextView(mContext);
			tv.setTextColor(Color.BLACK);
			// myDbHelper = new Database(parent.getContext());
			// myDbHelper.open();
			// Cursor c = myDbHelper.getTable("listSize");
			// c.moveToFirst();
			// tv.setTextSize(c.getInt(1));
			// c.close();
			// myDbHelper.close();

			tv.setTextAppearance(mContext, style.TextAppearance_Medium);
			tv.setTextColor(Color.BLACK);

			ImageView img = new ImageView(mContext);
			img.setId(16);
			tv.setText(ruleList[position]);
			tv.setPadding(5, 5, 5, 5);
			tv.setGravity(Gravity.CENTER_VERTICAL);
			// tv.setGravity(Gravity.LEFT);
			params.addRule(RelativeLayout.LEFT_OF, 16);

			if (GALLERY.equals("power"))
				img.setImageResource(powerImg[position]);
			// else if(GALLERY.equals("team"))
			// img.setImageResource(teamImg[position]);
			else
				img.setImageResource(teamImg[position]);
			img.setPadding(2, 2, 10, 2);
			layout.addView(tv, params);
			imgParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			imgParams.addRule(RelativeLayout.CENTER_VERTICAL);
			layout.addView(img, imgParams);
			return layout;
		}
	}
}