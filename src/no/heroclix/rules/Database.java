package no.heroclix.rules;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database {// extends SQLiteOpenHelper {

	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;
	private Context context;
	private static final int DATABASE_VERSION = 8;
	private static final String DB_NAME = "HCRules";
	private static final String TABLE_NAME = "options";
	private static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME
			+ " (_id TEXT, value TEXT);";

	private static class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context context) {
			super(context, DB_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(TABLE_CREATE);
			populate(db);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// Logs that the database is being upgraded
			Log.w("UPGRADE", "Upgrading database from version " + oldVersion
					+ " to " + newVersion + ", which will destroy all old data");

			// Kills the table and existing data
			db.execSQL("DROP TABLE IF EXISTS options");

			// Recreates the database with a new version
			onCreate(db);
		}

		private void populate(SQLiteDatabase db) {
			db.execSQL("INSERT INTO options VALUES ('screen', '0')");
			db.execSQL("INSERT INTO options VALUES ('about', '0')");
			db.execSQL("INSERT INTO options VALUES ('size', '14')");
			// db.execSQL("INSERT INTO options VALUES ('listSize', '14')");
			db.execSQL("INSERT INTO options VALUES ('centerList', '1')");
		}
	}

	public Database(Context context) {
		this.context = context;
	}

	public Database open() throws SQLException {
		mDbHelper = new DatabaseHelper(context);
		mDb = mDbHelper.getWritableDatabase();
		if (mDb.getVersion() != DATABASE_VERSION)
			mDbHelper.onUpgrade(mDb, mDb.getVersion(), DATABASE_VERSION);
		return this;
	}

	public void close() {
		mDbHelper.close();
	}

	public Cursor getTable(String rowID) {
		try {
			return mDb.rawQuery("SELECT * FROM " + TABLE_NAME
					+ " WHERE _id = '" + rowID + "'", null);
		} catch (SQLiteException s) {
			Log.d("SQL: " + "Options", s.toString());
		}
		return null;
	}

	public void setTable(String rowID, String update) {
		mDb.execSQL("UPDATE " + TABLE_NAME + " SET value ='" + update
				+ "' WHERE _id = '" + rowID + "'");
	}
}
