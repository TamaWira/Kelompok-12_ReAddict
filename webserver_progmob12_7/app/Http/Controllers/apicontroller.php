<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\ModelBuku;
use App\ModelKategori;
use App\ModelMember;
use App\ModelPeminjaman;

class apicontroller extends Controller
{
    public function get_all_buku(){
        return response()->json(ModelBuku::all(), 200);
    }

    public function get_all_peminjaman(){
        return response()->json(ModelPeminjaman::all(), 200);
    }

    public function get_all_kategori(){
        return response()->json(ModelKategori::all(), 200);
    }

    public function insert_data_kategori(Request $request){
        $insert_kategori = new ModelKategori;
        $insert_kategori->kategori = $request->kategori;
        
        $insert_kategori->save();
        return response([
            'status' => 'OK',
            'message' => 'kategori disimpan',
            'data' => $insert_kategori
        ], 200);
    }

    public function insert_data_peminjaman(Request $request){
        $insert_peminjaman = new ModelPeminjaman;
        $insert_peminjaman->id_member = $request->idMember;
        $insert_peminjaman->id_buku = $request->idBuku;
        $insert_peminjaman->status = $request->status;
        $insert_peminjaman->tanggal_peminjaman = $request->tanggalPeminjaman;
        $insert_peminjaman->tanggal_kembali = $request->tanggalKembali;
        $insert_peminjaman->save();
        return response([
            'status' => 'OK',
            'message' => 'Barang Disimpan',
            'data' => $insert_peminjaman
        ], 200);
    }
}
