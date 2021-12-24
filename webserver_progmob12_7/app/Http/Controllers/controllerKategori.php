<?php

namespace App\Http\Controllers;

use App\ModelKategori;
use Illuminate\Http\Request;

class controllerKategori extends Controller
{
    public function getKategori(){
        return response()->json(ModelKategori::all(), 200);
    }

    public function insertKategori(Request $request){
        $insert_kategori = new ModelKategori;
        $insert_kategori->kategori = $request->kategori;
        $insert_kategori->save();
        return response([
            'status' => 'OK',
            'message' => 'Kategori Disimpan',
            'data' => $insert_kategori
        ], 200);
    }

    public function updateKategori(Request $request, $id){
        $check_kategori = ModelKategori::firstWhere('id_kategori', $id);
        if($check_kategori){
            $update_kategori = ModelKategori::find($id);
            $update_kategori->kategori = $request->kategori;
            $update_kategori->save();
            return response([
                'status' => 'OK',
                'message' => 'Data Berhasil Dirubah',
                'update-data' => $update_kategori
            ], 200);
        } else {
            return response([
                'status' => 'Not Found',
                'message' => 'Kode Barang Tidak ditemukan'
            ], 404);
        }
    }

    public function deleteKategori($id){
        $check_kategori = ModelKategori::firstWhere('id_kategori', $id);
        if($check_kategori){
            ModelKategori::destroy($id);
            return response([
                'status' => 'OK',
                'message' => 'Data Dihapus'
            ], 200);
        } else {
            return response([
                'status' => 'Not Found',
                'message' => 'Kode Barang Tidak ditemukan'
            ], 404);
        }
    }
}
