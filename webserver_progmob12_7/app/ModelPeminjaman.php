<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class ModelPeminjaman extends Model
{
    protected $table = 'tb_peminjaman';
    protected $primaryKey = 'id_peminjaman';
    public $timestamps = false;
}
