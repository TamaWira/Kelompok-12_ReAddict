package id.sadhaka.readdict;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import id.sadhaka.readdict.Database.DBHelper;
import id.sadhaka.readdict.Pinjam.BorrowActivity;
import id.sadhaka.readdict.Pinjam.BorrowedListActivity;

public class BerandaActivity extends AppCompatActivity {
    private TextView display_name;
    private String nama_display;
    private ImageView profileDisplay, categoryDisplay, borrowDisplay, borrowedListDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda);

        display_name = (TextView) findViewById(R.id.labelName);
        profileDisplay = (ImageView) findViewById(R.id.profilePict);
        categoryDisplay = (ImageView) findViewById(R.id.category);
        borrowDisplay = (ImageView) findViewById(R.id.add);
        borrowedListDisplay = (ImageView) findViewById(R.id.list);

        Intent getData = getIntent();
        String id = getData.getStringExtra("id");
        DBHelper dbHelper = new DBHelper(this);
        Cursor cursor = dbHelper.tampilkanPenggunaDariID(id);

        while(cursor.moveToNext()){
            nama_display = cursor.getString(1);
        }

        display_name.setText(nama_display);

        //jika profil diklik
        profileDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoProfile = new Intent(BerandaActivity.this, ProfileActivity.class);
                gotoProfile.putExtra("id", id);
                startActivity(gotoProfile);
            }
        });

        //jika kategori diklik
        categoryDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoCategory = new Intent(BerandaActivity.this,CategoryActivity.class);
                startActivity(gotoCategory);
            }
        });

        //jika peminjaman diklik
        borrowDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoBorrow = new Intent(BerandaActivity.this, BorrowActivity.class);
                startActivity(gotoBorrow);
            }
        });

        //jika list peminjaman diklik
        borrowedListDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoBorrowList = new Intent(BerandaActivity.this, BorrowedListActivity.class);
                startActivity(gotoBorrowList);
            }
        });

    }

    protected void onResume(){
        Toast.makeText(getApplicationContext(), "Welcome to ReAddict!", Toast.LENGTH_SHORT).show();
        Log.i("Status", "Welcome to ReAddict!");
        super.onResume();
    }

    protected void onStop(){
        Toast.makeText(getApplicationContext(), "Page switched", Toast.LENGTH_SHORT).show();
        Log.i("State", "Page switched");
        super.onStop();
    }

    protected void onDestroy(){
        Toast.makeText(getApplicationContext(), "Bye bye!", Toast.LENGTH_SHORT).show();
        Log.i("State", "Bye bye!");
        super.onDestroy();
    }
}