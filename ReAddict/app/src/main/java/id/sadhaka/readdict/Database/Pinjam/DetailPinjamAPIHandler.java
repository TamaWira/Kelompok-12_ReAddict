package id.sadhaka.readdict.Database.Pinjam;

import java.util.List;

import id.sadhaka.readdict.Model.BorrowHandler;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DetailPinjamAPIHandler {
    @GET("peminjaman/{id}/lihat_detail")
    Call<List<BorrowHandler>> detailPinjamRetrieveData(
            @Path("id") Integer id_peminjaman
    );

    @FormUrlEncoded
    @PUT("peminjaman/{id}/update_peminjaman")
    Call<BorrowHandler> detailPinjamUpdateData(
            @Path("id") Integer id,
            @Field("tanggal_kembali") String tanggal_kembali
    );

    @FormUrlEncoded
    @PUT("peminjaman/{id}/return_peminjaman")
    Call<BorrowHandler> detailPinjamReturnData(
            @Path("id") Integer id,
            @Field("status") String status
    );

    @DELETE("peminjaman/{id}/delete_peminjaman")
    Call<BorrowHandler> detailPinjamDeleteData(
            @Path("id") Integer id
    );
}
