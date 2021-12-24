<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class ModelBuku extends Model
{
    protected $table = 'tb_buku';
    protected $primaryKey = 'id_buku';
    public $timestamps = false;
}
