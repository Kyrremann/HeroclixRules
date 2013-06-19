package no.heroclix.rules;

import heroclix.Rules.R;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ListInfo extends ScrollableTabActivity {

	TextView info;
	Database myDbHelper;
	Cursor c;
	int POSITION;
	String NAME;
	String GALLERY;
	int SIZE;
	String[] rules;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listinfo);

		POSITION = getIntent().getExtras().getInt("POSITION");
		NAME = getIntent().getExtras().getString("NAME");
		GALLERY = getIntent().getExtras().getString("GALLERY");
		SIZE = getIntent().getExtras().getInt("SIZE");
		info = (TextView) findViewById(R.id.lInfoText);

		if (NAME.equals("abilities"))
			rules = getResources().getStringArray(R.array.abilities);
		else if (NAME.equals("map"))
			rules = getResources().getStringArray(R.array.map_rules);
		else if (NAME.equals("objects"))
			rules = getResources().getStringArray(R.array.objects);
		else if (NAME.equals("ata"))
			rules = getResources().getStringArray(R.array.ata);
		else if (NAME.equals("feats"))
			rules = getResources().getStringArray(R.array.feats);
		else if (NAME.equals("glossary"))
			rules = getResources().getStringArray(R.array.glossary);
		else if (NAME.equals("team_abilities"))
			rules = getResources().getStringArray(R.array.team_abilities);
		else if (NAME.equals("speed_abilities"))
			rules = getResources().getStringArray(R.array.speed_abilities);
		else if (NAME.equals("attack_abilities"))
			rules = getResources().getStringArray(R.array.attack_abilities);
		else if (NAME.equals("defense_abilities"))
			rules = getResources().getStringArray(R.array.defense_abilities);
		else if (NAME.equals("damage_abilities"))
			rules = getResources().getStringArray(R.array.damage_abilities);
		else if (NAME.equals("lotr"))
			rules = getResources().getStringArray(R.array.lotr);
		else
			rules = getResources().getStringArray(R.array.bfc);

		myDbHelper = new Database(this);
		myDbHelper.open();
		c = myDbHelper.getTable("size");
		c.moveToFirst();
		info.setTextSize(c.getInt(1));
		c.close();
		myDbHelper.close();

		if (POSITION >= rules.length) {
			info.setText("Something went wrong. Please try to click something else, and then come back");
		} else {
			parseText(rules[POSITION], info);
		}
		info.setMovementMethod(new ScrollingMovementMethod());

		Button previous, done, next;
		previous = (Button) findViewById(R.id.previous);
		done = (Button) findViewById(R.id.done);
		next = (Button) findViewById(R.id.next);

		previous.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (POSITION == 0)
					POSITION = SIZE - 1;
				else
					POSITION--;
				if (POSITION >= rules.length)
					info.setText("Something went wrong. Please try to click something else, and then come back");
				else
					parseText(rules[POSITION], info);
			}
		});

		done.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				setResult(POSITION);
				finish();
			}
		});

		next.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if ((POSITION + 1) == SIZE)
					POSITION = 0;
				else
					POSITION++;
				if (POSITION >= rules.length)
					info.setText("Something went wrong. Please try to click something else, and then come back");
				else
					parseText(rules[POSITION], info);
			}
		});
	}

	private void parseText(String text, TextView textView) {
		String[] tmp = null;
		tmp = text.split("[#]");

		for (String s : tmp) {
			Log.d("Split: ", s);
		}

		SpannableStringBuilder builder = new SpannableStringBuilder();
		builder.append(tmp[0]);
		int lengthOfPart1 = builder.length();
		builder.append(" ");
		for (int i = 1; i < tmp.length; i++) {
			int image = -1;
			Drawable d = null;
			if (tmp[i].equals("speed")) {
				image = R.drawable.speed;
			} else if (tmp[i].equals("attack")) {
				image = R.drawable.attack;
			} else if (tmp[i].equals("defense")) {
				image = R.drawable.defense;
			} else if (tmp[i].equals("damage")) {
				image = R.drawable.damage;
			} else if (tmp[i].equals("swim")) {
				image = R.drawable.swim;
			} else if (tmp[i].equals("sharpshooter")) {
				image = R.drawable.sharpshooter;
			} else if (tmp[i].equals("indomitable")) {
				image = R.drawable.indomitable;
			} else if (tmp[i].equals("flight") || tmp[i].equals("flying")) {
				image = R.drawable.flight;
			} else if (tmp[i].equals("duo") || tmp[i].equals("duoatc")) {
				image = R.drawable.duo;
			} else if (tmp[i].equals("giant")) {
				image = R.drawable.giant;
			} else if (tmp[i].equals("colossus")) {
				image = R.drawable.colossus;
			} else if (tmp[i].equals("transSwim")) {
				image = R.drawable.transswim;
			} else if (tmp[i].equals("transFlight")) {
				image = R.drawable.transflight;
			} else if (tmp[i].equals("transSpeed")) {
				image = R.drawable.transspeed;
			} else if (tmp[i].equals("trait")) {
				image = R.drawable.trait;
			} else if (tmp[i].equals("lightning")) {
				image = R.drawable.bolt;
			} else if (tmp[i].equals("3d")) {
				image = R.drawable.threed;
			} else if (tmp[i].equals("tavengers")) {
				image = R.drawable.taavengers;
			} else if (tmp[i].equals("lotrbook"))
				image = R.drawable.lotrbook;
			else if (tmp[i].equals("lotrm"))
				image = R.drawable.lotrm;
			else if (tmp[i].equals("lotrs"))
				image = R.drawable.lotrs;
			else if (tmp[i].equals("impmov"))
				image = R.drawable.impmov;
			else if (tmp[i].equals("imptar"))
				image = R.drawable.imptar;
			else if (tmp[i].equals("ignet"))
				image = R.drawable.ignet;
			else if (tmp[i].equals("ignht"))
				image = R.drawable.ignht;
			else if (tmp[i].equals("ignbt"))
				image = R.drawable.ignbt;
			else if (tmp[i].equals("ignibt"))
				image = R.drawable.ignibt;
			else if (tmp[i].equals("ignobt"))
				image = R.drawable.ignobt;
			else if (tmp[i].equals("igndmg"))
				image = R.drawable.igndmg;
			else if (tmp[i].equals("ignfc"))
				image = R.drawable.ignfc;
			else if (tmp[i].equals("ignoc"))
				image = R.drawable.ignoc;
			else if (tmp[i].equals("doubleo"))
				image = R.drawable.doubleo;
			else if (tmp[i].equals("doubleoarr"))
				image = R.drawable.doubleoarr;
			else if (tmp[i].equals("tiny"))
				image = R.drawable.tiny;
			else if (tmp[i].equals("imp"))
				image = R.drawable.imp;
			else if (tmp[i].equals("chara"))
				image = R.drawable.chara;
			// else if (tmp[i].equals("frag"))
			// image = R.drawable.frag;
			// else if (tmp[i].equals("flashbang"))
			// image = R.drawable.flashbang;
			// else if (tmp[i].equals("plasma"))
			// image = R.drawable.plasma;
			// else if (tmp[i].equals("thermite"))
			// image = R.drawable.thermite;
			// else if (tmp[i].equals("smoke"))
			// image = R.drawable.smoke;
			// else if (tmp[i].equals("ink"))
			// image = R.drawable.ink;
			else if (tmp[i].equals("mindgem"))
				image = R.drawable.mindgem;
			else if (tmp[i].equals("powergem"))
				image = R.drawable.powergem;
			else if (tmp[i].equals("realitygem"))
				image = R.drawable.realitygem;
			else if (tmp[i].equals("soulgem"))
				image = R.drawable.soulgem;
			else if (tmp[i].equals("spacegem"))
				image = R.drawable.spacegem;
			else if (tmp[i].equals("timegem"))
				image = R.drawable.timegem;
			else if (tmp[i].equals("fancy4"))
				image = R.drawable.fancy4;
			else if (tmp[i].equals("fancy3"))
				image = R.drawable.fancy3;
			else if (tmp[i].equals("fancy2"))
				image = R.drawable.fancy2;
			else if (tmp[i].equals("fancy1"))
				image = R.drawable.fancy1;
			else if (tmp[i].equals("utilityBelt"))
				image = R.drawable.utilitybelt;
			else if (tmp[i].equals("paraBox"))
				image = R.drawable.parabox;
			else if (tmp[i].equals("sos"))
				image = R.drawable.sos;

			if (image != -1) {
				d = this.getResources().getDrawable(image);
				if (d == null) {
					builder.append(tmp[i]);
					builder.append(" ");
					lengthOfPart1 += tmp[i].length() + 1;
				} else {
					d.setBounds(0, 0, 24, 24); // <---- Very important otherwise
												// your image won't appear
					ImageSpan myImage = new ImageSpan(d);
					builder.setSpan(myImage, lengthOfPart1, lengthOfPart1 + 1,
							Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
					builder.append("");
				}
			} else {
				builder.append(tmp[i]);
				builder.append(" ");
				lengthOfPart1 += tmp[i].length() + 1;
			}
		}
		textView.setText(builder);
	}
}
