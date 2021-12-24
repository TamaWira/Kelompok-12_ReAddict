<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\ModelPeminjaman;

class controllerPeminjaman extends Controller
{
    public function getPeminjaman(){
        return response()->json(ModelPeminjaman::all(), 200);
    }

    public function getDetailPinjam($id){
        return response()->json(ModelPeminjaman::where('id_peminjaman', $id)->get(), 200);
    }

    public function insertPeminjaman(Request $request){
        $insert_peminjaman = new ModelPeminjaman;
        $insert_peminjaman->username = $request->username;
        $insert_peminjaman->judul_buku = $request->judul_buku;
        $insert_peminjaman->tanggal_peminjaman = $request->tanggal_peminjaman;
        $insert_peminjaman->tanggal_kembali = $request->tanggal_kembali;
        $insert_peminjaman->status = 'Pinjam';
        $insert_peminjaman->save();
        return response([
            'statusAPI' => true,
            'message' => 'Peminjaman Berhasil ditambah'
        ], 200);
    }

    public function returnPinjaman($id){
        $check_peminjaman = ModelPeminjaman::firstWhere('id_peminjaman', $id);
        if($check_peminjaman){
            $return_peminjaman = ModelPeminjaman::find($id);
            $return_peminjaman->status = 'Kembali';
            $return_peminjaman->save();
            return response([
                'statusAPI' => true,
                'message' => 'Peminjaman Berhasil Dikembalikan',
            ], 200);
        } else {
            return response([
                'statusAPI' => false,
                'message' => 'ID Peminjaman Tidak Ditemukan'
            ]);
        }
    }

    public function updatePeminjaman(Request $request, $id){
        $check_peminjaman = ModelPeminjaman::firstWhere('id_peminjaman', $id);
        if($check_peminjaman){
            $update_peminjaman = ModelPeminjaman::find($id);
            $update_peminjaman->tanggal_kembali = $request->tanggal_kembali;
            $update_peminjaman->save();
            return response([
                'statusAPI' => true,
                'message' => 'Peminjaman Disimpan',
            ], 200);
        } else {
            return response([
                'status' => false,
                'message' => 'ID Peminjaman tidak ditemukan'
            ], 404);
        }
    }

    public function deletePeminjaman($id){
        $check_peminjaman = ModelPeminjaman::firstWhere('id_peminjaman', $id);
        if($check_peminjaman){
            ModelPeminjaman::destroy($id);
            return response([
                'statusAPI' => true,
                'message' => 'Data Dihapus'
            ], 200);
        } else {
            return response([
                'statusAPI' => false,
                'message' => 'ID Peminjaman Tidak ditemukan'
            ], 404);
        }
    }
}
