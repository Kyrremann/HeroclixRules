package no.heroclix.rules;

import heroclix.Rules.R;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

public class ListCreator extends Activity {

	private String NAME;
	private ListView lw;
	private ArrayAdapter<String> adapter;
	private EditText search;
	private TextWatcher watcher;
	private ArrayList<String> rules;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);

		NAME = getIntent().getExtras().getString("NAME");
		Log.d("List", NAME);

		if (NAME.equals("ata"))
			rules = new ArrayList<String>(Arrays.asList(getResources()
					.getStringArray(R.array.ata_names)));
		else if (NAME.equals("abilities"))
			rules = new ArrayList<String>(Arrays.asList(getResources()
					.getStringArray(R.array.abilities_names)));
		else if (NAME.equals("map"))
			rules = new ArrayList<String>(Arrays.asList(getResources()
					.getStringArray(R.array.map_rules_names)));
		else if (NAME.equals("feats"))
			rules = new ArrayList<String>(Arrays.asList(getResources()
					.getStringArray(R.array.feats_names)));
		else if (NAME.equals("bfc"))
			rules = new ArrayList<String>(Arrays.asList(getResources()
					.getStringArray(R.array.bfc_names)));
		else if (NAME.equals("objects"))
			rules = new ArrayList<String>(Arrays.asList(getResources()
					.getStringArray(R.array.objects_names)));
		else if (NAME.equals("glossary"))
			rules = new ArrayList<String>(Arrays.asList(getResources()
					.getStringArray(R.array.glossary_names)));
		else if (NAME.equals("lotr"))
			rules = new ArrayList<String>(Arrays.asList(getResources()
					.getStringArray(R.array.lotr_names)));
		else {
			rules = new ArrayList<String>(Arrays.asList(getResources()
					.getStringArray(R.array.unlisted_keywords_1)));
			rules.addAll(Arrays.asList(getResources().getStringArray(
					R.array.unlisted_keywords_2)));
			rules.addAll(Arrays.asList(getResources().getStringArray(
					R.array.unlisted_keywords_3)));
			rules.addAll(Arrays.asList(getResources().getStringArray(
					R.array.unlisted_keywords_4)));
			rules.addAll(Arrays.asList(getResources().getStringArray(
					R.array.unlisted_keywords_5)));

			search = new EditText(this);
			search.setSingleLine(true);
			search.setHint("Type to search");
			search.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

			watcher = new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {

				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {

				}

				@Override
				public void afterTextChanged(Editable s) {

					adapter.getFilter().filter(s);
				}
			};

			search.addTextChangedListener(watcher);

			((LinearLayout) findViewById(R.id.listLayout)).addView(search, 0);
		}

		lw = (ListView) findViewById(android.R.id.list);
		adapter = new ArrayAdapter<String>(this, R.layout.listrow, rules);
		lw.setAdapter(adapter);
		// lw.setAdapter(new MyAdapter(this, R.layout.listrow, rules));
		// lw.setTextFilterEnabled(true);
		if (!NAME.equals("unlisted_keywords")) {
			lw.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> av, View v,
						int position, long id) {
					Intent myIntent = new Intent(getApplicationContext(),
							ListInfo.class);
					myIntent.putExtra("NAME", NAME);
					myIntent.putExtra("POSITION", position);
					myIntent.putExtra("SIZE", rules.size());
					startActivityForResult(myIntent, 0);
				}

			});
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (NAME.equals("unlisted_keywords"))
			search.removeTextChangedListener(watcher);
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
}
