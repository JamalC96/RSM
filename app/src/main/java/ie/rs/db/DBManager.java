package ie.rs.db;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import ie.rs.models.Food;

public class DBManager {

	private SQLiteDatabase database;
	private DBDesigner dbHelper;

	public DBManager(Context context) {
		dbHelper = new DBDesigner(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		database.close();
	}

	public void insert(Food c) {
		ContentValues values = new ContentValues();
		values.put("foodname", c.foodName);
		values.put("shop", c.shop);
		values.put("price", c.price);
		values.put("rating", c.rating);
		values.put("favourite", c.favourite == true ? 1 : 0);

		database.insert("table_food", null, values);
	}

	public void delete(int id) {
		Log.v("DB", "Food deleted with id: " + id);
		database.delete("table_food", "foodid = " + id, null);
	}

	public void update(Food c) {
		ContentValues values = new ContentValues();
		values.put("foodname", c.foodName);
		values.put("shop", c.shop);
		values.put("price", c.price);
		values.put("rating", c.rating);
		values.put("favourite", c.favourite == true ? 1 : 0);

		database.update("table_food", values,
				"foodid = " + c.foodId, null);

	}

	public List<Food> getAll() {
		List<Food> foods = new ArrayList<Food>();
		Cursor cursor = database.rawQuery("SELECT * FROM table_food", null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			foods.add(toFood(cursor));
			cursor.moveToNext();
		}
		cursor.close();
		return foods;
	}

	public Food get(int id) {
		Food pojo = null;

		Cursor cursor = database.rawQuery("SELECT * FROM table_food"
				+ " WHERE foodid = " + id, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Food temp = toFood(cursor);
			pojo = temp;
			cursor.moveToNext();
		}
		cursor.close();
		return pojo;
	}

	public List<Food> getFavourites() {
		List<Food> foods = new ArrayList<Food>();
		Cursor cursor = database.rawQuery("SELECT * FROM table_food"
				+ " WHERE favourite = 1", null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			foods.add(toFood(cursor));
			cursor.moveToNext();
		}
		cursor.close();
		return foods;
	}
	
	private Food toFood(Cursor cursor) {
		Food pojo = new Food();
		pojo.foodId = cursor.getInt(0);
		pojo.foodName = cursor.getString(1);
		pojo.shop = cursor.getString(2);
		pojo.price = cursor.getDouble(3);
		pojo.rating = cursor.getDouble(4);
		pojo.favourite = cursor.getInt(5) == 1 ? true : false;

		return pojo;
	}

	public void setupFoods() {
		Food c1 = new Food("Mocca Latte", "Ardkeen Stores", 4, 2.99, false);
		Food c2 = new Food("Espresso", "Tescos Stores",3.5, 1.99, true);
		Food c3 = new Food("Standard Black", "Ardkeen Stores",2.5, 1.99, true);
		Food c4 = new Food("Cappuccino", "Spar Shop",2.5, 1.49, false);

		insert(c1);
		insert(c2);
		insert(c3);
		insert(c4);
	}
}
