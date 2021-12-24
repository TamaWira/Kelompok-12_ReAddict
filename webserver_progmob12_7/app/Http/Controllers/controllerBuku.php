<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\ModelBuku;

class controllerBuku extends Controller
{
    public function getBuku(){
        return response()->json(ModelBuku::all(), 200);
    }

    public function insertBuku(Request $request){
        $insert_buku = new ModelBuku;
        $insert_buku->id_kategori = $request->idKategori;
        $insert_buku->nama_buku = $request->namaBuku;
        $insert_buku->save();
        return response([
            'status' => 'OK',
            'message' => 'Data Berhasil Ditambahkan',
            'data' => $insert_buku
        ], 200);
    }

    public function updateBuku(Request $request, $id){
        $check_buku = ModelBuku::firstWhere('id_buku', $id);
        if($check_buku){
            $update_buku = ModelBuku::find($id);
            $update_buku->id_kategori = $request->idKategori;
            $update_buku->nama_buku = $request->namaBuku;
            $update_buku->save();
            return response([
                'status' => 'OK',
                'message' => 'Data Berhasil Dirubah',
                'update-data' => $update_buku
            ], 200);
        } else {
            return response([
                'status' => 'Not Found',
                'message' => 'ID Buku Tidak ditemukan'
            ], 404);
        }
    }

    public function deleteBuku($id){
        $check_buku = ModelBuku::firstWhere('id_buku', $id);
        if($check_buku){
            ModelBuku::destroy($id);
            return response([
                'status' => 'OK',
                'message' => 'Data Dihapus'
            ], 200);
        } else {
            return response([
                'status' => 'Not Found',
                'message' => 'ID Buku Tidak ditemukan'
            ], 404);
        }
    }
}
