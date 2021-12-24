<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class ModelMember extends Model
{
    protected $table = 'tb_member';
    protected $primaryKey = 'id_member';
    public $timestamps = false;
}
