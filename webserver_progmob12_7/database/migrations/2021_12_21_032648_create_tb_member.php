<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateTbMember extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('tb_member', function (Blueprint $table) {
            $table->bigIncrements('id_member');
            $table->string('nama_lengkap');
            $table->string('username');
            $table->string('email');
            $table->enum('gender', ['P', 'L']);
            $table->string('password');
            $table->integer('kesukaan_membaca');
            $table->string('buku_favorit');
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('tb_member');
    }
}
