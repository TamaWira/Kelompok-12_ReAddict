package id.sadhaka.readdict;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import id.sadhaka.readdict.Database.DBHelper;
import id.sadhaka.readdict.Model.MemberHandler;

public class TestingActivity extends AppCompatActivity {

    private Button btnTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);

        btnTest = (Button) findViewById(R.id.btnTest);

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper myDB = new DBHelper(getApplicationContext());
                MemberHandler memberHandler = new MemberHandler();
                memberHandler.setNama("Ifentius Ciputra");
                memberHandler.setUsername("ifntus");
                memberHandler.setEmail("ifntus@gmail.com");
                memberHandler.setPassword("ifntus88");
                memberHandler.setGender("Male");
                memberHandler.setInterest("100");
                memberHandler.setFavorit("Science and Technology, Health");

                myDB.addMember(memberHandler);

                Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_SHORT).show();
            }
        });
    }
}