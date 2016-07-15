package co.alectronic.dbdemo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    public static final String DB_USERS= "Users";
    public static final String TABLE_USERS = "users";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_AGE = "age";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
            SQLiteDatabase myDb = this.openOrCreateDatabase(DB_USERS, MODE_PRIVATE, null);
            myDb.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_USERS+" ("+FIELD_NAME+" VARCHAR, "+FIELD_AGE+" INT(3));");

            myDb.execSQL("INSERT INTO "+TABLE_USERS+" ("+FIELD_NAME+","+FIELD_AGE+") VALUES ('Alec',25),('James',23);");

            Cursor c = myDb.rawQuery("SELECT * FROM "+TABLE_USERS+";",null);

            int nameIndex = c.getColumnIndex(FIELD_NAME);
            int ageIndex = c.getColumnIndex(FIELD_AGE);


            c.moveToFirst();
            while(c != null){
                Log.i("DB","Name: "+c.getString(nameIndex)+ " Age: "+ c.getInt(ageIndex));
                c.moveToNext();
            }

        }catch(Exception e){
            e.printStackTrace();
        }


    }
}
