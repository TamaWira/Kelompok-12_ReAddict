package id.sadhaka.readdict.Pinjam;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.sadhaka.readdict.Database.Pinjam.DetailPinjamAPIHandler;
import id.sadhaka.readdict.Database.RetroHelper;
import id.sadhaka.readdict.Model.BorrowHandler;
import id.sadhaka.readdict.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BorrowedListDetailActivity extends AppCompatActivity {

    private TextView uname, bookTitle, borrowDate;
    private EditText returnDate;
    private Button returnBtn, editBtn, deleteBtn;
    private Integer id;
    private String tanggal_kembali, strStatus, status = "Kembali";
    private List<BorrowHandler> borrowHandlers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrowed_list_detail);

        uname = (TextView) findViewById(R.id.usernameBorrow);
        bookTitle = (TextView) findViewById(R.id.titleBorrow);
        borrowDate = (TextView) findViewById(R.id.borrowDate);
        returnDate = (EditText) findViewById(R.id.returnDate);

        returnBtn = (Button) findViewById(R.id.btnReturn);
        editBtn = (Button) findViewById(R.id.btnEdit);
        deleteBtn = (Button) findViewById(R.id.btnDelete);

        Intent getId = getIntent();
        id = getId.getIntExtra("id", 0);

        if (id > 0) {
            retrieveData();
            Log.d(TAG, "onCreate: id : " + id);
        }

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tanggal_kembali = returnDate.getText().toString();

                if (returnDate.length() == 0) {
                    Toast.makeText(BorrowedListDetailActivity.this, "Date cannot be empty!", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder dialogAlertBuilder = new AlertDialog.Builder(BorrowedListDetailActivity.this);
                    dialogAlertBuilder.setTitle("Confirmation");
                    dialogAlertBuilder
                            .setMessage(
                                    "Username : " + uname.getText().toString() + "\n" +
                                            "Title : " + bookTitle.getText().toString() + "\n" +
                                            "Borrow Date : " + borrowDate.getText().toString() + "\n" +
                                            "Return Date : " + tanggal_kembali.toString() + "\n")
                            .setPositiveButton("Next", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    updateData();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog dialog = dialogAlertBuilder.create();
                    dialog.show();
                }
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogAlertBuilder = new AlertDialog.Builder(BorrowedListDetailActivity.this);
                dialogAlertBuilder.setTitle("Confirmation");
                dialogAlertBuilder
                        .setMessage("Are you sure?")
                        .setPositiveButton("Next", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteData();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog dialog = dialogAlertBuilder.create();
                dialog.show();
            }
        });

        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogAlertBuilder = new AlertDialog.Builder(BorrowedListDetailActivity.this);
                dialogAlertBuilder.setTitle("Confirmation");
                dialogAlertBuilder
                        .setMessage("Return this book?")
                        .setPositiveButton("Next", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                returnData();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog dialog = dialogAlertBuilder.create();
                dialog.show();
            }
        });
    }

    public void retrieveData() {
        DetailPinjamAPIHandler detailPinjamAPIHandler = RetroHelper.connectRetrofit().create(DetailPinjamAPIHandler.class);
        Call<List<BorrowHandler>> getDetailPinjam = detailPinjamAPIHandler.detailPinjamRetrieveData(id);

        getDetailPinjam.enqueue(new Callback<List<BorrowHandler>>() {
            @Override
            public void onResponse(Call<List<BorrowHandler>> call, Response<List<BorrowHandler>> response) {
                borrowHandlers = response.body();
                uname.setText(borrowHandlers.get(0).getUsername());
                bookTitle.setText(borrowHandlers.get(0).getJudul_buku());
                borrowDate.setText(borrowHandlers.get(0).getTanggal_peminjaman());
                returnDate.setText(borrowHandlers.get(0).getTanggal_kembali());
            }

            @Override
            public void onFailure(Call<List<BorrowHandler>> call, Throwable t) {
                Toast.makeText(BorrowedListDetailActivity.this, "Gagal mengambil data detail peminjaman : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void returnData() {
        DetailPinjamAPIHandler detailPinjamAPIHandler = RetroHelper.connectRetrofit().create(DetailPinjamAPIHandler.class);
        Call<BorrowHandler> getDetailPinjaman = detailPinjamAPIHandler.detailPinjamReturnData(id, status);

        getDetailPinjaman.enqueue(new Callback<BorrowHandler>() {
            @Override
            public void onResponse(Call<BorrowHandler> call, Response<BorrowHandler> response) {
                Boolean statusAPI = response.body().isStatusAPI();
                String message = response.body().getMessage();

                if (statusAPI) {
                    Toast.makeText(getApplicationContext(), "" + message, Toast.LENGTH_SHORT).show();
                    Intent goListPinjam = new Intent(getApplicationContext(), BorrowedListActivity.class);
                    startActivity(goListPinjam);
                } else {
                    Toast.makeText(getApplicationContext(), "Gagal merubah status peminjaman", Toast.LENGTH_SHORT).show();
                }
                finish();
            }

            @Override
            public void onFailure(Call<BorrowHandler> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Gagal merubah status peminjaman", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateData() {
        DetailPinjamAPIHandler detailPinjamAPIHandler = RetroHelper.connectRetrofit().create(DetailPinjamAPIHandler.class);
        Call<BorrowHandler> getDetailPinjam = detailPinjamAPIHandler.detailPinjamUpdateData(id, tanggal_kembali);

        getDetailPinjam.enqueue(new Callback<BorrowHandler>() {
            @Override
            public void onResponse(Call<BorrowHandler> call, Response<BorrowHandler> response) {
                Boolean statusAPI = response.body().isStatusAPI();
                String message = response.body().getMessage();

                if(statusAPI){
                    Toast.makeText(getApplicationContext(), ""+message, Toast.LENGTH_SHORT).show();
                    Intent goListPinjam = new Intent(getApplicationContext(), BorrowedListActivity.class);
                    startActivity(goListPinjam);
                } else {
                    Toast.makeText(getApplicationContext(), "Gagal mengupdate data peminjaman", Toast.LENGTH_SHORT).show();
                }
                finish();
            }

            @Override
            public void onFailure(Call<BorrowHandler> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Gagal mengupdate data peminjaman", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteData(){
        DetailPinjamAPIHandler detailPinjamAPIHandler = RetroHelper.connectRetrofit().create(DetailPinjamAPIHandler.class);
        Call<BorrowHandler> getDetailPinjam = detailPinjamAPIHandler.detailPinjamDeleteData(id);

        getDetailPinjam.enqueue(new Callback<BorrowHandler>() {
            @Override
            public void onResponse(Call<BorrowHandler> call, Response<BorrowHandler> response) {
                Boolean statusAPI = response.body().isStatusAPI();
                String message = response.body().getMessage();

                if(statusAPI){
                    Toast.makeText(getApplicationContext(), ""+message, Toast.LENGTH_SHORT).show();
                    Intent gotoListPinjam = new Intent(getApplicationContext(), BorrowedListActivity.class);
                    startActivity(gotoListPinjam);
                } else {
                    Toast.makeText(getApplicationContext(), "Data gagal dihapus", Toast.LENGTH_SHORT).show();
                }
                finish();
            }

            @Override
            public void onFailure(Call<BorrowHandler> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Data gagal dihapus", Toast.LENGTH_SHORT).show();
            }
        });
    }
}