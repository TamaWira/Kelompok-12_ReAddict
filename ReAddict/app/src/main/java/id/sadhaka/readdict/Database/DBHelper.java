package id.sadhaka.readdict.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import id.sadhaka.readdict.Model.BorrowHandler;
import id.sadhaka.readdict.Model.MemberHandler;

public class DBHelper extends SQLiteOpenHelper {

    private Context context;
    public static final String DATABASE_NAME = "eLibrary.db";

    // --- Borrow Table
    private static final String TABLE_BORROW = "tb_peminjaman";
    private static final String KOLOM_ID_PINJAM = "id_peminjaman";
    private static final String KOLOM_JUDUL_BUKU = "judul_buku";
    private static final String KOLOM_TGL_PINJAM = "tanggal_peminjaman";
    private static final String KOLOM_TGL_KEMBALI = "tanggal_kembali";
    private static final String KOLOM_STATUS = "status";

    // --- User Table
    private static final String TABLE_MEMBER = "tb_member";
    private static final String KOLOM_ID_MEMBER = "id_member";
    private static final String KOLOM_NAMA = "nama";
    private static final String KOLOM_USERNAME = "username";
    private static final String KOLOM_EMAIL = "email";
    private static final String KOLOM_PASSWORD = "password";
    private static final String KOLOM_GENDER = "gender";
    private static final String KOLOM_INTEREST = "kesukaan_membaca";
    private static final String KOLOM_BUKU_FAVORIT = "buku_favorit";

    //membuat database
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    //membuat tabel
    @Override
    public void onCreate(SQLiteDatabase DB) {
        String create_tb_member = "CREATE TABLE " + TABLE_MEMBER +
                " (" + KOLOM_ID_MEMBER + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KOLOM_NAMA + " TEXT, " +
                KOLOM_USERNAME + " TEXT, " +
                KOLOM_EMAIL + " TEXT, " +
                KOLOM_PASSWORD + " TEXT, " +
                KOLOM_GENDER + " TEXT, " +
                KOLOM_INTEREST + " TEXT, " +
                KOLOM_BUKU_FAVORIT + " TEXT);";

        DB.execSQL(create_tb_member);
        DB.execSQL("create Table borrow(id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, title TEXT, date_borrow TEXT, date_return TEXT, status TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("DROP TABLE IF EXISTS tb_member");
        DB.execSQL("DROP TABLE IF EXISTS borrow");
//        onCreate(DB);
    }

    // ----------------- Tabel Member -----------------

    public boolean addMember(MemberHandler memberHandler){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KOLOM_NAMA, memberHandler.getNama());
        cv.put(KOLOM_USERNAME, memberHandler.getUsername());
        cv.put(KOLOM_EMAIL, memberHandler.getEmail());
        cv.put(KOLOM_PASSWORD, memberHandler.getPassword());
        cv.put(KOLOM_GENDER, memberHandler.getGender());
        cv.put(KOLOM_INTEREST, memberHandler.getInterest());
        cv.put(KOLOM_BUKU_FAVORIT, memberHandler.getFavorit());
        return db.insert(TABLE_MEMBER, null, cv) > 0;
    }

    public boolean cekUsername(String username){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tb_member WHERE username = ?", new String[]{username});
        if (cursor.getCount()>0){
            return true;
        }else {
            return false;
        }
    }

    public int cekUsernameDanPassword(String username, String password){
        int id = -1;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tb_member WHERE username = ? AND password = ?", new String[]{username, password});
        while (cursor.moveToNext()) {
            id = Integer.valueOf(cursor.getString(0));
        }
        return id;
    }

    public Cursor tampilkanPenggunaDariID(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tb_member WHERE id_member = ?", new String[]{id});
        return cursor;
    }

    public boolean updateMember(String row_id, MemberHandler memberHandler){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KOLOM_NAMA, memberHandler.getNama());
        cv.put(KOLOM_USERNAME, memberHandler.getUsername());
        cv.put(KOLOM_EMAIL, memberHandler.getEmail());
        cv.put(KOLOM_PASSWORD, memberHandler.getPassword());
        cv.put(KOLOM_GENDER, memberHandler.getGender());
        cv.put(KOLOM_INTEREST, memberHandler.getInterest());
        cv.put(KOLOM_BUKU_FAVORIT, memberHandler.getFavorit());

        return db.update(TABLE_MEMBER, cv, "id_member=?", new String[]{row_id}) > 0;
    }

    public boolean deleteOneMember(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_MEMBER, "id_member=?", new String[]{row_id}) > 0;
    }

    // ----------------- Tabel Peminjaman -----------------

    //insert data peminjaman
    public Boolean insertborrowdata(BorrowHandler borrowHandler) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", borrowHandler.getUsername());
        contentValues.put("title", borrowHandler.getJudul_buku());
        contentValues.put("date_borrow", borrowHandler.getTanggal_peminjaman());
        contentValues.put("date_return", borrowHandler.getTanggal_kembali());
        contentValues.put("status", borrowHandler.getStatus());
        return DB.insert("borrow", null, contentValues) > 0;
    }

    //mengambil data peminjaman
    public Cursor getBorrowData(){
        SQLiteDatabase DB = getReadableDatabase();
        return DB.rawQuery("select * from borrow", null);
    }

    public Cursor getBorrowDetail(int id_pinjam){
        SQLiteDatabase DB = getReadableDatabase();
        return DB.rawQuery("select * from borrow where id = " + id_pinjam, null);
    }

    //edit data peminjaman
    public boolean editborrowdata(BorrowHandler borrowHandler, int id_pinjam) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("date_return", borrowHandler.getTanggal_kembali());
        return db.update("borrow", values, "id" + "=" + id_pinjam, null) > 0;
    }

    //hapus data peminjaman
    public boolean deleteborrowdata (int id_pinjam) {
        SQLiteDatabase db = getReadableDatabase();
        return db.delete("borrow", "id" + "=" + id_pinjam, null) > 0;
    }

    //edit status peminjaman
    public boolean returnbook(BorrowHandler borrowHandler, int id_pinjam) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status", borrowHandler.getStatus());
        return db.update("borrow", values, "id" + "=" + id_pinjam, null) > 0;
    }

}
