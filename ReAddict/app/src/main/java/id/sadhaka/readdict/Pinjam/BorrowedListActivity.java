package id.sadhaka.readdict.Pinjam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.sadhaka.readdict.Database.DBHelper;
import id.sadhaka.readdict.Database.Pinjam.PinjamAPIHelper;
import id.sadhaka.readdict.Database.RetroHelper;
import id.sadhaka.readdict.Model.BorrowHandler;
import id.sadhaka.readdict.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BorrowedListActivity extends AppCompatActivity {

    private DBHelper readdict;
    protected RecyclerView recyclerView;
    protected RecyclerView.Adapter borrowedList;

    //menyimpan data dari model
    private List<BorrowHandler> borrowHandler = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrowed_list);

        readdict = new DBHelper(this);
        recyclerView = (RecyclerView) findViewById(R.id.borrowedList);

        //untuk memasukkan nilai ke recycler view melalui adapter
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        retrieveData();
//        borrowedList = new BorrowedListAdapter(borrowHandler, BorrowedListActivity.this, recyclerView);
//        recyclerView.setAdapter(borrowedList);
    }

    public void retrieveData(){
        PinjamAPIHelper pinjamAPIHelper = RetroHelper.connectRetrofit().create(PinjamAPIHelper.class);
        Call<List<BorrowHandler>> getListBorrowed = pinjamAPIHelper.pinjamRetrieveData();

        getListBorrowed.enqueue(new Callback<List<BorrowHandler>>() {
            @Override
            public void onResponse(Call<List<BorrowHandler>> call, Response<List<BorrowHandler>> response) {
                borrowHandler = response.body();
                borrowedList = new BorrowedListAdapter(borrowHandler, BorrowedListActivity.this, recyclerView);
                recyclerView.setAdapter(borrowedList);
                borrowedList.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<BorrowHandler>> call, Throwable t) {
                Toast.makeText(BorrowedListActivity.this, "Gagal mengambil data pinjaman : "+ t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}