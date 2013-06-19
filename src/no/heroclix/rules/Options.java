package no.heroclix.rules;

import heroclix.Rules.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class Options extends Activity {

	private String size;
	private CheckBox screenBox, listBox;
	private TextView fontSize;
	private Database myDbHelper;
	private Cursor c;
	private final CharSequence[] items = { "X-Small", "Small", "Medium",
			"Large", "X-Large" };
	private final int[] itemsInt = { 10, 12, 14, 16, 18 };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.options);

		screenBox = (CheckBox) findViewById(R.id.checkedScreen);
		listBox = (CheckBox) findViewById(R.id.checkedList);
		fontSize = (TextView) findViewById(R.id.changeFont);

		myDbHelper = new Database(this);
		myDbHelper.open();
		c = myDbHelper.getTable("screen");
		c.moveToFirst();
		if (c.getString(1).equals("1")) {
			screenBox.setChecked(true);
		}
		c.close();

		c = myDbHelper.getTable("centerList");
		c.moveToFirst();
		if (c.getString(1).equals("0")) {
			listBox.setChecked(false);
		}
		c.close();

		c = myDbHelper.getTable("size");
		c.moveToFirst();
		size = c.getString(1);
		c.close();
		myDbHelper.close();

		fontSize.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int count = 0;

				for (int i = 0; i < itemsInt.length; i++) {
					if (size.equals("" + itemsInt[i])) {
						count = i;
						break;
					}
				}

				AlertDialog.Builder builder = new AlertDialog.Builder(v
						.getContext());
				builder.setTitle("Size of text");
				builder.setSingleChoiceItems(items, count,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								size = Integer.toString(itemsInt[which]);
							}
						});
				builder.setNeutralButton("Done",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.cancel();
							}
						});

				AlertDialog alert = builder.create();
				alert.show();
			}
		});

		Button done = (Button) findViewById(R.id.Done);
		Button cancel = (Button) findViewById(R.id.Cancel);

		done.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				myDbHelper = new Database(v.getContext());
				myDbHelper.open();
				// c = myDbHelper.getTable("screen");
				// c.moveToFirst();
				if (screenBox.isChecked()) {// && c.getString(1).equals("0")) {
					myDbHelper.setTable("screen", "1");
				} else if (!screenBox.isChecked()) {// &&
													// c.getString(1).equals("1"))
													// {
					myDbHelper.setTable("screen", "0");
				}
				if (listBox.isChecked()) {// && c.getString(1).equals("0")) {
					myDbHelper.setTable("centerList", "1");
				} else if (!listBox.isChecked()) {// &&
													// c.getString(1).equals("1"))
													// {
					myDbHelper.setTable("centerList", "0");
				}
				// c.close();
				myDbHelper.setTable("size", size);
				myDbHelper.close();
				setResult(RESULT_OK);
				finish();
			}
		});

		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});
	}
}
