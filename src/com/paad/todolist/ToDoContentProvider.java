/**
 * 
 */
package com.paad.todolist;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.Selection;
import android.text.TextUtils;
import android.util.Log;

/**
 * @author Harrsh
 * 
 */
public class ToDoContentProvider extends ContentProvider {

	public static final Uri CONTENT_URI = Uri
			.parse("content://com.paad.todoprovider/todoitems");
	public static final String KEY_ID = "_id";
	public static final String KEY_TASK = "task";
	public static final String KEY_CREATION_DATE = "creation_date";
	private MySQLiteOpenHelper myOpenHelper;
	private static final int ALLROWS = 1;
	private static final int SINGLE_ROW = 2;
	private static final UriMatcher uriMatcher;

	// Populate the UriMatcher object, where a URI ending in 'todoitems' will
	// correspond to a request for all items, and 'todoitems/[rowID]'
	// represents a single row.
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI("com.paad.todoprovider", "todoitems", ALLROWS);
		uriMatcher.addURI("com.paad.todoprovider", "todoitems/#", SINGLE_ROW);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.content.ContentProvider#delete(android.net.Uri,
	 * java.lang.String, java.lang.String[])
	 */
	@Override
	public int delete(Uri url, String where, String[] whereArgs) {
		// TODO Auto-generated method stub
		// Open a read / write database to support the transaction.
		SQLiteDatabase db = myOpenHelper.getWritableDatabase();

		// If this is a row URI, limit the deletion to the specified row.
		switch (uriMatcher.match(url)) {
		case SINGLE_ROW:
			String rowID = url.getPathSegments().get(1);
			where = KEY_ID + "=" + rowID
					+ (!TextUtils.isEmpty(where) ? "and (" + where + ")" : "");
			break;
		default:
			break;
		}

		// To return the number of deleted items, you must specify a where
		// clause. To delete all rows and return a value, pass in "1".
		if (where == null) {
			where = "1";
		}

		// Execute the deletion.
		int deleteCount = db.delete(MySQLiteOpenHelper.DATABASE_TABLE, where,
				whereArgs);

		// Notify any observers of the change in the data set.
		getContext().getContentResolver().notifyChange(url, null);

		return deleteCount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.content.ContentProvider#getType(android.net.Uri)
	 */
	@Override
	public String getType(Uri url) {
		// TODO Auto-generated method stub
		// Return a string that identifies the MIME type
		// for a Content Provider URI
		switch (uriMatcher.match(url)) {
		case ALLROWS:
			return "vnd.android.cursor.dir/vnd.paad.todos";
		case SINGLE_ROW:
			return "vnd.android.cursor.item/vnd.paad.todos";
		default:
			throw new IllegalArgumentException("Unsupported URI:" + url);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.content.ContentProvider#insert(android.net.Uri,
	 * android.content.ContentValues)
	 */
	@Override
	public Uri insert(Uri url, ContentValues initialValues) {
		// TODO Auto-generated method stub
		// Open a read / write database to support the transaction.
		SQLiteDatabase db = myOpenHelper.getWritableDatabase();

		// To add empty rows to your database by passing in an empty Content
		// Values
		// object, you must use the null column hack parameter to specify the
		// name of
		// the column that can be set to null.
		String nullColumnHack = null;

		// Insert the values into the table
		long id = db.insert(MySQLiteOpenHelper.DATABASE_TABLE, nullColumnHack,
				initialValues);

		if (id > -1) {
			// Construct and return the URI of the newly inserted row.
			Uri insertedId = ContentUris.withAppendedId(CONTENT_URI, id);

			// Notify any observers of the change in the data set.
			getContext().getContentResolver().notifyChange(insertedId, null);

			return insertedId;
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.content.ContentProvider#onCreate()
	 */
	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		// Construct underlying database.
		// Defer opening the database until you need to perform
		// a query or transaction.
		myOpenHelper = new MySQLiteOpenHelper(getContext(),
				MySQLiteOpenHelper.DATABASE_NAME, null,
				MySQLiteOpenHelper.DATABASE_VERSION);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.content.ContentProvider#query(android.net.Uri,
	 * java.lang.String[], java.lang.String, java.lang.String[],
	 * java.lang.String)
	 */
	@Override
	public Cursor query(Uri url, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		// Open read-only database.
		SQLiteDatabase db = myOpenHelper.getWritableDatabase();

		// Replace these with valid SQL statements if necessary.
		String groupBy = null;
		String having = null;

		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder.setTables(MySQLiteOpenHelper.DATABASE_TABLE);

		// If this is a row query, limit the result set to the passed in row.
		switch (uriMatcher.match(url)) {
		case SINGLE_ROW:
			String rowID = url.getPathSegments().get(1);
			queryBuilder.appendWhereEscapeString(KEY_ID + "=" + rowID);
		default:
			break;
		}
		Cursor cursor = queryBuilder.query(db, projection, selection,
				selectionArgs, groupBy, having, sortOrder);

		return cursor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.content.ContentProvider#update(android.net.Uri,
	 * android.content.ContentValues, java.lang.String, java.lang.String[])
	 */
	@Override
	public int update(Uri url, ContentValues values, String where,
			String[] whereArgs) {
		// TODO Auto-generated method stub
		// Open a read / write database to support transaction.
		SQLiteDatabase db = myOpenHelper.getWritableDatabase();

		// If this is a row URI, limit the updation to the specified row.
		switch (uriMatcher.match(url)) {
		case SINGLE_ROW:
			String rowID = url.getPathSegments().get(1);
			where = KEY_ID + "=" + rowID
					+ (!TextUtils.isEmpty(where) ? "and (" + where + ")" : "");
			break;
		default:
			break;
		}

		// Perform the update.
		int updateCount = db.update(MySQLiteOpenHelper.DATABASE_TABLE, values,
				where, whereArgs);

		// Notify any observers of the change in the data set.
		getContext().getContentResolver().notifyChange(url, null);

		return updateCount;
	}

	/**
	 * @author Harrsh
	 * 
	 */
	private static class MySQLiteOpenHelper extends SQLiteOpenHelper {

		public static final String DATABASE_NAME = "todoDatabase.db";
		public static final int DATABASE_VERSION = 1;
		public static final String DATABASE_TABLE = "todoItemTable";
		public static final String DATABASE_CREATE = "create table "
				+ DATABASE_TABLE + " (" + KEY_ID
				+ " integer primary key autoincrement, " + KEY_TASK
				+ " text not null, " + KEY_CREATION_DATE + "long);";

		public MySQLiteOpenHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
			// TODO Auto-generated constructor stub
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * android.database.sqlite.SQLiteOpenHelper#onCreate(android.database
		 * .sqlite.SQLiteDatabase)
		 */
		// Called when no database exists in disk and the helper class needs
		// to create a new one.
		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Create database tables
			db.execSQL(DATABASE_CREATE);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database
		 * .sqlite.SQLiteDatabase, int, int)
		 */
		// Called when there is a database version mismatch, meaning that the
		// version
		// of the database on disk needs to be upgraded to the current version.
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Upgrade database.
			// Log the version upgrade.
			Log.w("TaskDBAdapter", "Upgrading from version " + oldVersion
					+ "to" + newVersion + ", which will destroy all old data");
			// Upgrade the existing database to conform to the new version.
			// Multiple
			// previous versions can be handled by comparing oldVersion and
			// newVersion
			// values.

			// The simplest case is to drop the old table and create new one.
			db.execSQL("drop table if it exists " + DATABASE_TABLE);
			// Create a new one.
			onCreate(db);
		}

	}
}
