package id.sadhaka.readdict.Database.Pinjam;

import java.util.List;

import id.sadhaka.readdict.Model.BorrowHandler;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PinjamAPIHelper {
    @GET("peminjaman")
    Call<List<BorrowHandler>> pinjamRetrieveData();

    @FormUrlEncoded
    @POST("peminjaman/insert_peminjaman")
    Call<BorrowHandler> insertData(
            @Field("username") String username,
            @Field("judul_buku") String judul_buku,
            @Field("tanggal_peminjaman") String tanggal_peminjaman,
            @Field("tanggal_kembali") String tanggal_kembali,
            @Field("status") String status
    );
}
