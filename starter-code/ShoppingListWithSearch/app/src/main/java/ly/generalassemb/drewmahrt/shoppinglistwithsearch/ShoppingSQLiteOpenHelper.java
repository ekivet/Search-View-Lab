package ly.generalassemb.drewmahrt.shoppinglistwithsearch;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class ShoppingSQLiteOpenHelper extends SQLiteOpenHelper{
    private static final String TAG = ShoppingSQLiteOpenHelper.class.getCanonicalName();

    private static final int DATABASE_VERSION = 7;
    public static final String DATABASE_NAME = "SHOPPING_DB";
    public static final String SHOPPING_LIST_TABLE_NAME = "SHOPPING_LIST";

    public static final String COL_ID = "_id";
    public static final String COL_ITEM_NAME = "ITEM_NAME";
    public static final String COL_ITEM_PRICE = "PRICE";
    public static final String COL_ITEM_DESCRIPTION = "DESCRIPTION";
    public static final String COL_ITEM_TYPE = "TYPE";


    public static final String[] SHOPPING_COLUMNS = {COL_ID,COL_ITEM_NAME,COL_ITEM_DESCRIPTION,COL_ITEM_PRICE,COL_ITEM_TYPE};

    private static final String CREATE_SHOPPING_LIST_TABLE =
            "CREATE TABLE " + SHOPPING_LIST_TABLE_NAME +
                    "(" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_ITEM_NAME + " TEXT, " +
                    COL_ITEM_DESCRIPTION + " TEXT, " +
                    COL_ITEM_PRICE + " REAL, " +
                    COL_ITEM_TYPE + " TEXT )";

    //make ShoppingSQLiteOpenHelper Into Singleton So It Can Be Used By SearchResultActivity
    private static ShoppingSQLiteOpenHelper instance;

    public static ShoppingSQLiteOpenHelper getInstance(Context context){
        if(instance == null){
            instance = new ShoppingSQLiteOpenHelper(context);
        }
        return instance;
    }

    public ShoppingSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SHOPPING_LIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SHOPPING_LIST_TABLE_NAME);
        this.onCreate(db);
    }

    // create cursor for searchview
    public Cursor searchList(String query){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(SHOPPING_LIST_TABLE_NAME, SHOPPING_COLUMNS,
                COL_ITEM_NAME+ " LIKE ?", new String[]{query+"%"},null,null,null);
        return cursor;
    }

    //getter for new cursor
    public Cursor getList(){
        SQLiteDatabase db = getReadableDatabase();
        return db.query(SHOPPING_LIST_TABLE_NAME,null,null,null,null,null,null);
    }

}
