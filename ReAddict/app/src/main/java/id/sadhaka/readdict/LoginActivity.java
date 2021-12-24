package id.sadhaka.readdict;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import id.sadhaka.readdict.Database.DBHelper;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameLogin, passwordLogin;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameLogin = (EditText) findViewById(R.id.usernameLogin);
        passwordLogin = (EditText) findViewById(R.id.passwordLogin);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper myDB = new DBHelper(getApplicationContext());
                String strUsername = usernameLogin.getText().toString();
                String strPassword = passwordLogin.getText().toString();

                Log.d(TAG, "onClick: Get it");

                if (strUsername.length() == 0 || strPassword.length() == 0) {
                    Toast.makeText(LoginActivity.this, "Lengkapi Username atau Password!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onClick: No Data");

                } else if (myDB.cekUsername(strUsername)) {
                    Log.d(TAG, "onClick: hore");
                    if (myDB.cekUsernameDanPassword(strUsername, strPassword)>0) {
                        Intent gotoBeranda = new Intent(LoginActivity.this, BerandaActivity.class);
                        gotoBeranda.putExtra("id", String.valueOf(myDB.cekUsernameDanPassword(strUsername, strPassword)));
                        Log.d(TAG, "onClick: Berhasil");
                        startActivity(gotoBeranda);
                    }else{
                        Toast.makeText(LoginActivity.this, "Password Salah!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onClick: password salah");
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Username atau Password salah!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onClick: username atau password salah");
                }
            }
        });
    }
}