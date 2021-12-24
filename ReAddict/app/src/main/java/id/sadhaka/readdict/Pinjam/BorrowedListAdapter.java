package id.sadhaka.readdict.Pinjam;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.sadhaka.readdict.Model.BorrowHandler;
import id.sadhaka.readdict.R;

public class BorrowedListAdapter extends RecyclerView.Adapter<BorrowedListAdapter.ViewHolder> {

    private List<BorrowHandler> borrowHandlerList;
    private Context context;
    private RecyclerView recyclerView;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView headTitle;
        TextView borrowDate;
        TextView returnDate;
        TextView status;

        //mendefinisikan masing-masing item pada recycler view
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            headTitle = itemView.findViewById(R.id.head_list);
            borrowDate = itemView.findViewById(R.id.valueDateBorrow);
            returnDate = itemView.findViewById(R.id.valueDateReturn);
            status = itemView.findViewById(R.id.valueStatus);
        }
    }

    //mengatur parameter pada adapter
    public BorrowedListAdapter(List<BorrowHandler> borrowHandlerList, Context context, RecyclerView recyclerView) {
        this.borrowHandlerList = borrowHandlerList;
        this.context = context;
        this.recyclerView = recyclerView;
    }

        //mengatur layout yang akan ditampilkan di recycler view
        @NonNull
        @Override
        public BorrowedListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.borrowed_books, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        //mengatur nilai yang akan ditampilkan di recycler view
        @Override
        public void onBindViewHolder(@NonNull BorrowedListAdapter.ViewHolder holder, int position) {
            BorrowHandler borrowHandler = borrowHandlerList.get(position);
            holder.headTitle.setText(String.valueOf(borrowHandler.getJudul_buku()));
            holder.borrowDate.setText(String.valueOf(borrowHandler.getTanggal_peminjaman()));
            holder.returnDate.setText(String.valueOf(borrowHandler.getTanggal_kembali()));
            holder.status.setText(String.valueOf(borrowHandler.getStatus()));
            holder.itemView.setOnClickListener(new View.OnClickListener() {

                //ke halaman detail berdasarkan id
                @Override
                public void onClick(View v) {
                    Integer itemId = borrowHandler.getId_peminjaman();
                    Log.d(TAG, "onClick: id: " + itemId);
                    Intent gotoDetail = new Intent(holder.itemView.getContext(), BorrowedListDetailActivity.class);
                    gotoDetail.putExtra("id", itemId);
                    holder.itemView.getContext().startActivity(gotoDetail);
                }
            });
        }

        //mengetahui posisi item pada recycler view
        @Override
        public long getItemId(int position) {
            return position;
        }

        //mengetahui jumlah item pada recycler view
        @Override
        public int getItemCount() {
            return borrowHandlerList.size();
        }
}
