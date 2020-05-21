package lib.frame.module.db.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import lib.frame.module.db.utils.TableHelper;


public class SmartDBHelper extends SQLiteOpenHelper {
	private Class<?>[] modelClasses;

	public SmartDBHelper(Context context, String databaseName,
			SQLiteDatabase.CursorFactory factory, int databaseVersion,
			Class<?>[] modelClasses) {
		super(context, databaseName, factory, databaseVersion);

		this.modelClasses = modelClasses;
	}

	public void onCreate(SQLiteDatabase db) {
		TableHelper.createTablesByClasses(db, this.modelClasses);
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		TableHelper.dropTablesByClasses(db, this.modelClasses);
		onCreate(db);
	}
}
