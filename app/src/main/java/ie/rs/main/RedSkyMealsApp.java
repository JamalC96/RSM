package ie.rs.main;

import ie.rs.db.DBManager;

import android.app.Application;
import android.util.Log;

public class RedSkyMealsApp extends Application
{
    //public List <Food> foodList = new ArrayList<>();
    public DBManager dbManager = new DBManager(this);

    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.v("coffeemate", "CoffeeMate App Started");
        dbManager.open();
    }

    @Override
    public void onTerminate()
    {
        super.onTerminate();
        dbManager.close();
    }
}