package no.heroclix.rules;

import heroclix.Rules.R;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

public class Main extends ScrollableTabActivity {

	public Database myDbHelper;
	public Bundle savedInstanceState;
	// private int size = 55;
	private boolean oldRules = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		myDbHelper = new Database(this);
		myDbHelper.open();

		Cursor c = myDbHelper.getTable("screen");
		c.moveToFirst();
		if (c.getString(1).equals("1"))
			getWindow()
					.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		c.close();

		c = myDbHelper.getTable("about");
		c.moveToFirst();
		if (c.getString(1).equals("0")) {
			createAbout();
			myDbHelper.setTable("about", "1");
		}
		c.close();
		myDbHelper.close();

		/*
		 * set this activity as the tab bar delegate so that onTabChanged is
		 * called when users tap on the bar
		 */
		setDelegate(new SliderBarActivityDelegateImpl());

		Intent intent;
		intent = new Intent().setClass(this, ImgListCreator.class);
		intent.putExtra("NAME", "speed_abilities");
		intent.putExtra("GALLERY", "power");
		intent.putExtra("OLD", oldRules);
		this.addTab("Speed", R.drawable.speed, intent);

		intent = new Intent().setClass(this, ImgListCreator.class);
		intent.putExtra("NAME", "attack_abilities");
		intent.putExtra("GALLERY", "power");
		intent.putExtra("OLD", oldRules);
		this.addTab("Attack", R.drawable.attack, intent);

		intent = new Intent().setClass(this, ImgListCreator.class);
		intent.putExtra("NAME", "defense_abilities");
		intent.putExtra("GALLERY", "power");
		intent.putExtra("OLD", oldRules);
		this.addTab("Defense", R.drawable.defense, intent);

		intent = new Intent().setClass(this, ImgListCreator.class);
		intent.putExtra("NAME", "damage_abilities");
		intent.putExtra("GALLERY", "power");
		intent.putExtra("OLD", oldRules);
		this.addTab("Damage", R.drawable.damage, intent);

		intent = new Intent().setClass(this, ImgListCreator.class);
		intent.putExtra("NAME", "team_abilities");
		intent.putExtra("GALLERY", "team");
		this.addTab("Team", R.drawable.team_abilities, intent);

		intent = new Intent().setClass(this, ListCreator.class);
		intent.putExtra("NAME", "ata");
		this.addTab("ATA", R.drawable.ata, intent);

		intent = new Intent().setClass(this, ListCreator.class);
		intent.putExtra("NAME", "abilities");
		intent.putExtra("OLD", oldRules);
		this.addTab("Abilities", R.drawable.abilities, intent);

		intent = new Intent().setClass(this, ListCreator.class);
		intent.putExtra("NAME", "map");
		this.addTab("Map Rules", R.drawable.map_rules, intent);

		intent = new Intent().setClass(this, ListCreator.class);
		intent.putExtra("NAME", "feats");
		this.addTab("Feats", R.drawable.feats, intent);

		intent = new Intent().setClass(this, ListCreator.class);
		intent.putExtra("NAME", "bfc");
		this.addTab("BFC", R.drawable.bfc, intent);

		intent = new Intent().setClass(this, ListCreator.class);
		intent.putExtra("NAME", "objects");
		this.addTab("Objects", R.drawable.objects, intent);

		intent = new Intent().setClass(this, ListCreator.class);
		intent.putExtra("NAME", "glossary");
		this.addTab("Glossary", R.drawable.glossary, intent);

		intent = new Intent().setClass(this, ListCreator.class);
		intent.putExtra("NAME", "unlisted_keywords");
		this.addTab("Keywords", R.drawable.keywords, intent);

		intent = new Intent().setClass(this, ListCreator.class);
		intent.putExtra("NAME", "lotr");
		this.addTab("Lotr", R.drawable.lotr, intent);

		/*
		 * commit is required to redraw the bar after add tabs are added if you
		 * know of a better way, drop me your suggestion please.
		 */
		commit();
	}

	private class SliderBarActivityDelegateImpl extends
			SliderBarActivityDelegate {
		/*
		 * Optional callback method called when users tap on the tab bar button
		 */
		protected void onTabChanged(int tabIndex) {
			Log.d("onTabChanged", "" + tabIndex);
		}
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		boolean result = super.onCreateOptionsMenu(menu);
		menu.add(0, 0, 0, R.string.options);
		menu.add(0, 1, 0, R.string.about);
		menu.add(0, 2, 0, R.string.donate);
		return result;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 0:
			createOption();
			break;
		case 1:
			createAbout();
			break;
		case 2:
			createDonate();
			break;
		}
		return false;
	}

	private void createDonate() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Donation");
		builder.setMessage(R.string.donateText);
		builder.setPositiveButton("Okey",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent browserIntent = new Intent(
								"android.intent.action.VIEW",
								Uri.parse("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=ATWBY2VZ3SW7Q"));
						startActivity(browserIntent);
					}
				});
		builder.setNegativeButton("Later",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});

		AlertDialog alert = builder.create();
		alert.show();
	}

	public void createOption() {
		Intent myIntent = new Intent(this.getApplicationContext(),
				Options.class);
		startActivityForResult(myIntent, 0);
		// setResult(RESULT_OK, myIntent);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			myDbHelper.open();
			Cursor c = myDbHelper.getTable("screen");

			c.moveToFirst();
			if (c.getString(1).equals("1"))
				getWindow().addFlags(
						WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
			c.close();
			myDbHelper.close();

			Intent i = getBaseContext().getPackageManager()
					.getLaunchIntentForPackage(
							getBaseContext().getPackageName());
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
		}
	}

	public void createAbout() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("About");
		builder.setMessage(R.string.intro);
		builder.setPositiveButton("Rate",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent browserIntent = new Intent(
								"android.intent.action.VIEW",
								Uri.parse("market://details?id=heroclix.Rules"));
						startActivity(browserIntent);
					}
				});

		builder.setNeutralButton("Okay", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		builder.setNegativeButton("Donate", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				createDonate();
			}
		});
/*		builder.setNegativeButton("Feedback",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(Intent.ACTION_SEND);
						intent.setType("plain/text");
						intent.putExtra(
								Intent.EXTRA_EMAIL,
								new String[] { "heroclix@fifthfloorstudio.net" });
						String version = "2.*";
						try {
							version = getPackageManager().getPackageInfo(
									"heroclix.Rules", 0).versionName;
						} catch (NameNotFoundException e) {
							e.printStackTrace();
						}
						intent.putExtra(Intent.EXTRA_SUBJECT,
								"Feedback: Heroclix Rules " + version);
						startActivity(Intent.createChooser(intent, ""));
					}
				});*/

		AlertDialog alert = builder.create();
		alert.show();
	}

	/*
	 * public void onStart() { super.onStart(); this.size =
	 * getApplicationContext
	 * ().getResources().getDisplayMetrics().heightPixels/10; if(size > 55) {
	 * HorizontalScrollView hcv = (HorizontalScrollView)
	 * findViewById(R.id.bottomBar); LinearLayout ll = (LinearLayout)
	 * findViewById(R.id.contentViewLayout);
	 * hcv.setHorizontalFadingEdgeEnabled(true); hcv.getLayoutParams().height =
	 * size; ll.setPadding(0, 0, 0, size); } }
	 */
}