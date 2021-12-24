package id.sadhaka.readdict;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import id.sadhaka.readdict.Database.DBHelper;
import id.sadhaka.readdict.Model.MemberHandler;

public class RegistrationActivity extends AppCompatActivity {

    private EditText fnameEdit, unameEdit, emailEdit, passwordEdit;
    private RadioGroup radiogroup;
    private RadioButton radiobutton;
    private SeekBar seekbar;
    private CheckBox lifestyle, science, fiction, biography, motivation, business, children, others;
    private Button letsgoBtn;
    private TextView seekbarValue;

    private String strFullname, strUsername, strEmail, strPassword, strRadio, strSeekbar, strCheckbox;
    private int idSelectedRadio, valueSeekbar = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        fnameEdit = (EditText) findViewById(R.id.fullName);
        unameEdit = (EditText) findViewById(R.id.userName);
        emailEdit = (EditText) findViewById(R.id.emailAddress);
        passwordEdit = (EditText) findViewById(R.id.passWord);

        radiogroup = (RadioGroup) findViewById(R.id.radioGroup);

        seekbar = (SeekBar) findViewById(R.id.seekBar);
        seekbarValue = (TextView) findViewById(R.id.valueInterest);

        lifestyle = (CheckBox) findViewById(R.id.checkLifestyle);
        science = (CheckBox) findViewById(R.id.checkScience);
        fiction = (CheckBox) findViewById(R.id.checkFiction);
        biography = (CheckBox) findViewById(R.id.checkBiography);
        motivation = (CheckBox) findViewById(R.id.checkMotivation);
        business = (CheckBox) findViewById(R.id.checkBusiness);
        children = (CheckBox) findViewById(R.id.checkChildren);
        others = (CheckBox) findViewById(R.id.checkOthers);

        letsgoBtn = (Button) findViewById(R.id.btnLetsgo);

        //mengatur nilai yang akan dimasukkan pada seekbar
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                valueSeekbar = progressValue;
                strSeekbar = Integer.toString(valueSeekbar);
                seekbarValue.setText("" + progressValue + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //action jika tombol diklik
        letsgoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strFullname = fnameEdit.getText().toString();
                strUsername = unameEdit.getText().toString();
                strEmail = emailEdit.getText().toString();
                strPassword = passwordEdit.getText().toString();

                idSelectedRadio = radiogroup.getCheckedRadioButtonId();
                radiobutton = findViewById(idSelectedRadio);
                strRadio = radiobutton.getText().toString();
                strCheckbox = "";

                //validasi checkbox
                if(lifestyle.isChecked()){
                    strCheckbox += "" + lifestyle.getText().toString() + ", ";
                } else {
                    strCheckbox = strCheckbox.replace("Lifestyle, ","");
                }
                if(science.isChecked()){
                    strCheckbox += "" + science.getText().toString() + ", ";
                } else {
                    strCheckbox = strCheckbox.replace("Science and Technology, ","");
                }
                if(fiction.isChecked()){
                    strCheckbox += "" + fiction.getText().toString() + ", ";
                } else {
                    strCheckbox = strCheckbox.replace("Fiction, ","");
                }
                if(biography.isChecked()){
                    strCheckbox += "" + biography.getText().toString() + ", ";
                } else {
                    strCheckbox = strCheckbox.replace("Biography, ","");
                }
                if(motivation.isChecked()){
                    strCheckbox += "" + motivation.getText().toString() + ", ";
                } else {
                    strCheckbox = strCheckbox.replace("Motivation and Self Help, ","");
                }
                if(business.isChecked()){
                    strCheckbox += "" + business.getText().toString() + ", ";
                } else {
                    strCheckbox = strCheckbox.replace("Business and Inventing, ","");
                }
                if(children.isChecked()){
                    strCheckbox += "" + children.getText().toString() + ", ";
                } else {
                    strCheckbox = strCheckbox.replace("Children Books, ","");
                }
                if(others.isChecked()){
                    strCheckbox += "" + others.getText().toString();
                } else {
                    strCheckbox = strCheckbox.replace("Others","");
                }
                dialogAlert();
            }
        });
    }

    private void dialogAlert(){
        AlertDialog.Builder dialogAlertBuilder = new AlertDialog.Builder(RegistrationActivity.this);

        //membuat judul alert dialog
        dialogAlertBuilder.setTitle("Your Data");

        //mengatur teks dan button pada alert dialog
        dialogAlertBuilder
                .setMessage(
                        "Fullname : " +strFullname.toString()+ "\n" +
                        "Username : " +strUsername.toString()+ "\n" +
                        "Email : " +strEmail.toString()+ "\n" +
                        "Gender : " +strRadio.toString()+ "\n" +
                        "Interest : " +strSeekbar.toString()+ "%" + "\n" +
                        "Favorite Books : " +strCheckbox.toString() + "\n")
                .setPositiveButton("Next", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(RegistrationActivity.this, "Registration Success!", Toast.LENGTH_SHORT).show();

                        DBHelper myDB = new DBHelper(getApplicationContext());
                        MemberHandler memberHandler = new MemberHandler();
                        memberHandler.setNama(strFullname.toString());
                        memberHandler.setUsername(strUsername.toString());
                        memberHandler.setEmail(strEmail.toString());
                        memberHandler.setPassword(strPassword.toString());
                        memberHandler.setGender(strRadio.toString());
                        memberHandler.setInterest(strSeekbar.toString());
                        memberHandler.setFavorit(strCheckbox.toString());

                        boolean addMember = myDB.addMember(memberHandler);

                        if (addMember) {
                            Toast.makeText(RegistrationActivity.this, "Registrasi Berhasil", Toast.LENGTH_SHORT).show();
                            Intent gotologin = new Intent(RegistrationActivity.this, LoginActivity.class);
                            startActivity(gotologin);
                        } else {
                            Toast.makeText(RegistrationActivity.this, "Registrasi Gagal", Toast.LENGTH_SHORT).show();
                        }
                        myDB.close();
                    }
                })
                .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        //membuat alert dialog dari builder
        AlertDialog dialog = dialogAlertBuilder.create();

        //menampilkan alert dialog
        dialog.show();
    }
}