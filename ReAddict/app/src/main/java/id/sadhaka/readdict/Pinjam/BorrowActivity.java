package id.sadhaka.readdict.Pinjam;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.sadhaka.readdict.Database.Pinjam.PinjamAPIHelper;
import id.sadhaka.readdict.Database.RetroHelper;
import id.sadhaka.readdict.Model.BorrowHandler;
import id.sadhaka.readdict.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BorrowActivity extends AppCompatActivity {

    private EditText uname, bookTitle, borrowDate, returnDate;
    private Button borrowBtn;
    private CheckBox terms;

    private String username, judul_buku, tanggal_peminjaman, tanggal_kembali, status, strTerms;
    private List<BorrowHandler> borrowHandlers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow);

        uname = (EditText) findViewById(R.id.userNameBorrow);
        bookTitle = (EditText) findViewById(R.id.bookTitle);
        borrowDate = (EditText) findViewById(R.id.dateBorrow);
        returnDate = (EditText) findViewById(R.id.dateReturn);
        terms = (CheckBox) findViewById(R.id.checkAgree);
        borrowBtn = (Button) findViewById(R.id.btnBorrow);

        if (!terms.isChecked()){
            borrowBtn.setAlpha(.5f);
            borrowBtn.setEnabled(false);
        }

        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!terms.isChecked()){
                    borrowBtn.setAlpha(.5f);
                    borrowBtn.setEnabled(false);
                } else {
                    borrowBtn.setAlpha(1);
                    borrowBtn.setEnabled(true);
                }
            }
        });

        borrowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = uname.getText().toString();
                judul_buku = bookTitle.getText().toString();
                tanggal_peminjaman = borrowDate.getText().toString();
                tanggal_kembali = returnDate.getText().toString();

                //mengatur status peminjaman
                if(terms.isChecked()){
                    strTerms = "Agree";
                    status = "Pinjam";
                } else {
                    strTerms = "Not Agree";
                }

                dialogAlertBorrow();
            }
        });
    }

    private void dialogAlertBorrow(){
        AlertDialog.Builder dialogAlertBuilder = new AlertDialog.Builder(BorrowActivity.this);

        //membuat judul alert dialog
        dialogAlertBuilder.setTitle("Your Data");

        //mengatur teks dan button pada alert dialog
        dialogAlertBuilder
                .setMessage(
                        "Username : " +username.toString()+ "\n" +
                        "Title : " +judul_buku.toString()+ "\n" +
                        "Borrow Date : " +tanggal_peminjaman.toString()+ "\n" +
                        "Return Date : " +tanggal_kembali.toString() + "\n" +
                        "Terms : "+strTerms.toString()+ "\n")
                .setPositiveButton("Next", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        insertData();

                        //membersihkan inputan
                        uname.getText().clear();
                        bookTitle.getText().clear();
                        borrowDate.getText().clear();
                        returnDate.getText().clear();

                        //ke halaman selanjutnya (list peminjaman)
                        Intent gotoBorrow = new Intent(BorrowActivity.this, BorrowedListActivity.class);
                        startActivity(gotoBorrow);

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

    public void insertData(){
        PinjamAPIHelper pinjamAPIHelper = RetroHelper.connectRetrofit().create(PinjamAPIHelper.class);
        Call<BorrowHandler> insertPinjam = pinjamAPIHelper.insertData(username, judul_buku, tanggal_peminjaman, tanggal_kembali, status);

        insertPinjam.enqueue(new Callback<BorrowHandler>() {
            @Override
            public void onResponse(Call<BorrowHandler> call, Response<BorrowHandler> response) {
                Boolean statusAPI = response.body().isStatusAPI();
                String message = response.body().getMessage();

                if(statusAPI){
                    Toast.makeText(getApplicationContext(), ""+message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Data gagal ditambahkan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BorrowHandler> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Data gagal ditambahkan", Toast.LENGTH_SHORT).show();
            }
        });
    }
}