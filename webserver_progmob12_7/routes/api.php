<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::middleware('auth:api')->get('/user', function (Request $request) {
    return $request->user();
});

Route::prefix('kategori')->group(function(){
    Route::get('/', 'controllerKategori@getKategori');
    Route::post('/tambah_kategori', 'controllerKategori@insertKategori');
    Route::put('/{id}/update_kategori', 'controllerKategori@updateKategori');
    Route::delete('/{id}/delete_kategori', 'controllerKategori@deleteKategori');
});

Route::prefix('member')->group(function(){
    Route::get('/', 'controllerMember@getMember');
    Route::post('/tambah_member', 'controllerMember@insertMember');
    Route::put('/{id}/update_member', 'controllerMember@updateMember');
    Route::delete('/{id}/delete_member', 'controllerMember@deleteMember');
});

Route::prefix('buku')->group(function(){
    Route::get('/', 'controllerBuku@getBuku');
    Route::post('/tambah_buku', 'controllerBuku@insertBuku');
    Route::put('/{id}/update_buku', 'controllerBuku@updateBuku');
    Route::delete('/{id}/delete_buku', 'controllerBuku@deleteBuku');
});

Route::prefix('peminjaman')->group(function(){
    Route::get('/', 'controllerPeminjaman@getPeminjaman');
    Route::get('/{id}/lihat_detail', 'controllerPeminjaman@getDetailPinjam');
    Route::post('/insert_peminjaman', 'controllerPeminjaman@insertPeminjaman');
    Route::put('/{id}/update_peminjaman', 'controllerPeminjaman@updatePeminjaman');
    Route::put('/{id}/return_peminjaman', 'controllerPeminjaman@returnPinjaman');
    Route::delete('/{id}/delete_peminjaman', 'controllerPeminjaman@deletePeminjaman');
});