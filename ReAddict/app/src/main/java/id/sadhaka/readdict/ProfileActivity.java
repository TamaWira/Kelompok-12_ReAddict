package id.sadhaka.readdict;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import id.sadhaka.readdict.Database.DBHelper;
import id.sadhaka.readdict.Model.MemberHandler;

public class ProfileActivity extends AppCompatActivity {
    private TextView fullnameProfile, usernameProfile, emailProfile, genderProfile, interestProfile, favbooksProfile;
    private String id, strFullname, strUsername, strEmail, strPassword, strRadio, strSeekbar, strCheckbox;
    private ImageView profileImage;
    private Button btnEditProfil, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileImage = (ImageView) findViewById(R.id.profilePictPage);
        fullnameProfile = (TextView) findViewById(R.id.fullNameProfile);
        usernameProfile = (TextView) findViewById(R.id.userNameProfile);
        emailProfile = (TextView) findViewById(R.id.emailAddressProfile);
        genderProfile = (TextView) findViewById(R.id.genderProfile);
        interestProfile = (TextView) findViewById(R.id.interestProfile);
        favbooksProfile = (TextView)findViewById(R.id.favBooksProfile);

        btnEditProfil = (Button) findViewById(R.id.btnEditProfile);
        btnDelete = (Button) findViewById(R.id.btnDeleteAcc);

        //mendapatkan data dari inputan sebelumnya untuk ditampilkan di halaman profil
        Intent getData = getIntent();
        id = getData.getStringExtra("id");
        DBHelper dbHelper = new DBHelper(this);
        Cursor cursor = dbHelper.tampilkanPenggunaDariID(id);

        while(cursor.moveToNext()){
            strFullname = cursor.getString(1);
            strUsername = cursor.getString(2);
            strEmail = cursor.getString(3);
            strPassword = cursor.getString(4);
            strRadio = cursor.getString(5);
            strSeekbar = cursor.getString(6);
            strCheckbox = cursor.getString(7);
        }

        //mengatur hasil inputan yang ditampilkan pada halaman profil
        fullnameProfile.setText(strFullname);
        usernameProfile.setText(strUsername);
        emailProfile.setText(strEmail);
        genderProfile.setText(strRadio);
        interestProfile.setText(strSeekbar);
        favbooksProfile.setText(strCheckbox);

        btnEditProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper myDB = new DBHelper(ProfileActivity.this);
                MemberHandler memberHandler = new MemberHandler();
                memberHandler.setNama(fullnameProfile.getText().toString());
                memberHandler.setUsername(usernameProfile.getText().toString());
                memberHandler.setEmail(emailProfile.getText().toString());
                memberHandler.setPassword(strPassword.toString());
                memberHandler.setGender(genderProfile.getText().toString());
                memberHandler.setInterest(interestProfile.getText().toString());
                memberHandler.setFavorit(favbooksProfile.getText().toString());

                Log.d(TAG, "onClick: berhasil");

                boolean addMember = myDB.updateMember(id, memberHandler);
//
                if (addMember) {
                    Toast.makeText(ProfileActivity.this, "Update Profil Berhasil", Toast.LENGTH_SHORT).show();
                    Intent gotoberanda = new Intent(ProfileActivity.this, BerandaActivity.class);
                    gotoberanda.putExtra("id", id);
                    startActivity(gotoberanda);
                    finish();
                } else {
                    Toast.makeText(ProfileActivity.this, "Update Profil Gagal", Toast.LENGTH_SHORT).show();
                }
                myDB.close();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + fullnameProfile.getText().toString());
        builder.setMessage("Are you sure you want to delete " + fullnameProfile.getText().toString() + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DBHelper myDb = new DBHelper(ProfileActivity.this);
                myDb.deleteOneMember(id);
                Intent gotolanding = new Intent(ProfileActivity.this, LandingPage.class);
                startActivity(gotolanding);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

    protected void onResume(){
        Toast.makeText(getApplicationContext(), "This is profile page", Toast.LENGTH_SHORT).show();
        Log.i("Status", "This is profile page");
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