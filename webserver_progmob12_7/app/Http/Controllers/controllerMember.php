<?php

namespace App\Http\Controllers;

use App\ModelMember;
use Illuminate\Http\Request;

class controllerMember extends Controller
{
    public function getMember(){
        return response()->json(ModelMember::all(), 200);
    }

    public function insertMember(Request $request){
        $insert_member = new ModelMember;
        $insert_member->nama_lengkap = $request->namaMember;
        $insert_member->username = $request->username;
        $insert_member->email = $request->email;
        $insert_member->gender = $request->gender;
        $insert_member->password = $request->password;
        $insert_member->kesukaan_membaca = $request->kesukaanMembaca;
        $insert_member->buku_favorit = $request->bukuFavorit;
        $insert_member->save();
        return response([
            'status' => 'OK',
            'message' => 'Data Berhasil Ditambahkan',
            'data' => $insert_member
        ], 200);
    }

    public function updateMember(Request $request, $id){
        $check_member = ModelMember::firstWhere('id_member', $id);
        if($check_member){
            $update_member = ModelMember::find($id);
            $update_member->nama_lengkap = $request->namaMember;
            $update_member->username = $request->username;
            $update_member->email = $request->email;
            $update_member->gender = $request->gender;
            $update_member->password = $request->password;
            $update_member->kesukaan_membaca = $request->kesukaanMembaca;
            $update_member->buku_favorit = $request->bukuFavorit;
            $update_member->save();
            return response([
                'status' => 'OK',
                'message' => 'Data Berhasil Dirubah',
                'update-data' => $update_member
            ], 200);
        } else {
            return response([
                'status' => 'Not Found',
                'message' => 'ID Member Tidak ditemukan'
            ], 404);
        }
    }

    public function deleteMember($id){
        $check_member = ModelMember::firstWhere('id_member', $id);
        if($check_member){
            ModelMember::destroy($id);
            return response([
                'status' => 'OK',
                'message' => 'Data Dihapus'
            ], 200);
        } else {
            return response([
                'status' => 'Not Found',
                'message' => 'ID Member Tidak ditemukan'
            ], 404);
        }
    }
}
