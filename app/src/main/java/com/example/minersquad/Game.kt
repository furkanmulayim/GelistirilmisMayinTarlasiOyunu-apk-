package com.example.minersquad

import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View.TEXT_ALIGNMENT_TEXT_END
import android.view.ViewGroup
import android.view.ViewManager
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.activity_main.*


class Game : AppCompatActivity() {

    var mplayer :MediaPlayer = MediaPlayer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_game)


        dinamik_buton()
        sayac()
        kazandin_kaybettin()

    }

    override fun onBackPressed() {
        finish()
        mplayer.stop()
    }

    var kayip = 0
    var bomba_sayar = 10
    var kazanma_adimi = 0
    var rand_array = Array<Int>(10) { 0 }
    var id = 0
    protected var ll_main : LinearLayout? = null


    fun dinamik_buton() {
        val mp_gameover: MediaPlayer = MediaPlayer.create(this, resources.getIdentifier(R.raw.msc_gameover.toString(), "raw", packageName))
        val mp_bos_sayi_tik: MediaPlayer = MediaPlayer.create(this, resources.getIdentifier(R.raw.msc_tiklanmis_bos_sayi.toString(), "raw", packageName))
        val mp_imha_edildi: MediaPlayer = MediaPlayer.create(this, resources.getIdentifier(R.raw.msc_imha_edildi.toString(), "raw", packageName))
        val mp_imha_edilemedi: MediaPlayer = MediaPlayer.create(this, resources.getIdentifier(R.raw.msc_imha_edilemedi.toString(), "raw", packageName))

        for (i in 0..9) {
            do {
                rand_array[i] = (1..81).random()
            } while (rand_array.equals(rand_array[i]))
        }

        var id2 = 1
        val ll_main2 = findViewById(R.id.sayac_layout) as LinearLayout
        val buton_bom_sayac = Button(this)
        buton_bom_sayac.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        buton_bom_sayac.getLayoutParams().width = 350
        buton_bom_sayac.getLayoutParams().height = 150
        buton_bom_sayac.x = 656F
        buton_bom_sayac.y = 180F
        buton_bom_sayac.id = id2
        buton_bom_sayac.isClickable = false
        buton_bom_sayac.setBackgroundResource(R.drawable.flag_bomb)
        buton_bom_sayac.text = bomba_sayar.toString() + "     "
        buton_bom_sayac.textSize = 22F
        buton_bom_sayac.textAlignment = TEXT_ALIGNMENT_TEXT_END
        buton_bom_sayac.setTextColor(Color.parseColor("#FFF0F9"))
        ll_main2.addView(buton_bom_sayac)

        for (sutun in 1..9) {
            for (satir in 1..9) {

                //buton idlerini almak için id dögü içinde artırılır
                id++
                ll_main = findViewById(R.id.ll2) as LinearLayout

                // buton oluşturdum
                val buton = Button(this)

                val gamettb = AnimationUtils.loadAnimation(this, R.anim.game_ttb)
                val gamettb2press = AnimationUtils.loadAnimation(this, R.anim.game_ttb2_press)
                buton.startAnimation(gamettb)

                val hareket1 = AnimationUtils.loadAnimation(this, R.anim.solhareket)
                val hareket2 = AnimationUtils.loadAnimation(this, R.anim.saghareket)
                val hareket3 = AnimationUtils.loadAnimation(this, R.anim.sonsolhareket)

                // düzen parametrelerini kullanarak genişlik ve yükseklik ayarlama
                buton.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

                //buton genişlik ve yükseklik ayarlandı
                buton.getLayoutParams().width = 120
                buton.getLayoutParams().height = 120
                buton.setBackgroundResource(R.drawable.buton_tiklanmamis)


                if (sutun == 1) {
                    buton.x = 10F
                    buton.y = 160F

                } else if (sutun == 2) {
                    buton.x = -1070F
                    buton.y = 274F

                } else if (sutun == 3) {
                    buton.x = -2150F
                    buton.y = 388F

                } else if (sutun == 4) {
                    buton.x = -3230F
                    buton.y = 502F

                } else if (sutun == 5) {
                    buton.x = -4310F
                    buton.y = 616F

                } else if (sutun == 6) {
                    buton.x = -5390F
                    buton.y = 730F

                } else if (sutun == 7) {
                    buton.x = -6470F
                    buton.y = 844F

                } else if (sutun == 8) {
                    buton.x = -7550F
                    buton.y = 958F

                } else if (sutun == 9) {
                    buton.x = -8630F
                    buton.y = 1072F

                }

                //butonlara birer id verdik
                buton.id = id
                var butonid = buton.id

                buton.setOnLongClickListener{
                    bomba_sayar -= 1

                    for (i in 0..9) {
                        if(kayip!=1){

                            var bombamiz = rand_array[i].toString()
                            if (bombamiz == butonid.toString() && kayip ==0 && bomba_sayar >= 0) {
                                buton.text = "☠"
                                mp_imha_edildi.start()
                                buton.setBackgroundResource(R.drawable.buton_tiklanmis_bomba_imha)
                                buton.isClickable = false
                                buton.y = buton.y - 1f
                                buton.isEnabled = false
                                buton_bom_sayac.text = ""
                                buton_bom_sayac.text = bomba_sayar.toString() + "     "
                                kazanma_adimi += 1

                                if (kazanma_adimi == 7){
                                    ll_main!!.isVisible = false
                                    ll_kaybettin.setBackgroundResource(R.drawable.winner)
                                    ll_kaybettin.isVisible = true
                                }
                            }
                            else if (bomba_sayar >= 0) {
                                mp_imha_edilemedi.start()
                                buton_bom_sayac.text = ""
                                buton_bom_sayac.text = bomba_sayar.toString() + "     "

                                var timer = object : CountDownTimer(100, 100) {
                                    override fun onTick(millisUntilFinished: Long) {
                                        buton.startAnimation(hareket1)
                                    }
                                    override fun onFinish() {
                                        buton.startAnimation(hareket2)
                                    }
                                }
                                timer.start()

                                var timer2 = object : CountDownTimer(100, 100) {
                                    override fun onTick(millisUntilFinished: Long) {
                                        buton.startAnimation(hareket3)
                                    }
                                    override fun onFinish() {
                                    }
                                }
                                timer2.start()

                            }
                        }

                    }
                    true
                }

                buton.setOnClickListener {

                    //random sayılarla denk gelen idlere bomba yerleştiriyoruz
                    for (i in 0..9) {

                        if(kayip!=1){

                            var bombamiz = rand_array[i].toString()
                            if (bombamiz == butonid.toString() && kayip ==0) {
                                buton.text = "☠"
                                buton.y = buton.y - 1f
                                buton.setBackgroundResource(R.drawable.buton_tiklanmis_bomba)
                                buton.isClickable = false
                                mp_gameover.start()

                                // zaman oluşturduum eğer bombaya basarsak
                                //bi  saniye bekleyecek ve geçecek
                                var timer = object : CountDownTimer(1000, 1000) {
                                    override fun onTick(millisUntilFinished: Long) {

                                    }
                                    override fun onFinish() {
                                        ll_main!!.isVisible = false
                                        ll_kaybettin.isVisible = true
                                    }
                                }
                                timer.start()

                                kayip = 1;

                            } else if (kayip == 0) {
                                buton.isClickable = false
                                mp_bos_sayi_tik.start()
                                var sol_ust_kose = 1;
                                var sag_ust_kose = 9;
                                var sol_alt_kose = 73;
                                var sag_alt_kose = 81;

                                if (buton.id == sol_ust_kose) {
                                    if (buton.text == "") {
                                        buton.isClickable = false
                                        var varan1 = sol_ust_kose + 1;
                                        var varan2 = sol_ust_kose + 9;
                                        var varan3 = sol_ust_kose + 10;
                                        var flag = 0;

                                        for (i in 0..9) {

                                            var bombamiz = rand_array[i].toString()
                                            if (bombamiz == varan1.toString()) {
                                                flag += 1;
                                            } else if (bombamiz == varan2.toString()) {
                                                flag += 1;
                                            } else if (bombamiz == varan3.toString()) {
                                                flag += 1;
                                            }
                                        }

                                        if (flag != 0) {
                                            if (buton.text != "☠") {
                                                buton.y = buton.y - 1.58f
                                                buton.isEnabled = false
                                                if (flag == 1){
                                                    buton.setBackgroundResource(R.drawable.buton_tiklanmis_1)
                                                }else if (flag == 2){
                                                    buton.setBackgroundResource(R.drawable.buton_tiklanmis_2)
                                                }else if (flag == 3){
                                                    buton.setBackgroundResource(R.drawable.buton_tiklanmis_3)
                                                }

                                            }
                                        } else {
                                            if (buton.text != "☠") {
                                                buton.y = buton.y - 1.90f
                                                buton.isEnabled = false
                                                buton.setBackgroundResource(R.drawable.buton_tiklanmis_bos)
                                            }
                                        }

                                    }
                                } else if (buton.id == sag_ust_kose) {
                                    buton.isClickable = false
                                    if (buton.text == "") {
                                        var varan1 = sag_ust_kose - 1;
                                        var varan2 = sag_ust_kose + 9;
                                        var varan3 = sag_ust_kose + 8;
                                        var flag = 0;
                                        for (i in 0..9) {
                                            var bombamiz = rand_array[i].toString()
                                            if (bombamiz == varan1.toString()) {
                                                flag += 1;
                                            } else if (bombamiz == varan2.toString()) {
                                                flag += 1;
                                            } else if (bombamiz == varan3.toString()) {
                                                flag += 1;
                                            }
                                            if (flag != 0) {
                                                if (buton.text != "☠") {
                                                    buton.y = buton.y - 1.58f
                                                    buton.isEnabled = false
                                                    if (flag == 1){
                                                        buton.setBackgroundResource(R.drawable.buton_tiklanmis_1)
                                                    }else if (flag == 2){
                                                        buton.setBackgroundResource(R.drawable.buton_tiklanmis_2)
                                                    }else if (flag == 3){
                                                        buton.setBackgroundResource(R.drawable.buton_tiklanmis_3)
                                                    }
                                                }

                                            } else {
                                                if (buton.text != "☠") {
                                                    buton.y = buton.y - 1.90f
                                                    buton.isEnabled = false
                                                    buton.text = "•"
                                                    buton.setBackgroundResource(R.drawable.buton_tiklanmis_bos)
                                                }
                                            }
                                        }
                                    }
                                } else if (buton.id == sol_alt_kose) {
                                    buton.isClickable = false
                                    if (buton.text == "") {
                                        var varan1 = sol_alt_kose - 9;
                                        var varan2 = sol_alt_kose - 8;
                                        var varan3 = sol_alt_kose + 1;
                                        var flag = 0;

                                        for (i in 0..9) {
                                            var bombamiz = rand_array[i].toString()
                                            if (bombamiz == varan1.toString()) {
                                                flag += 1;
                                            } else if (bombamiz == varan2.toString()) {
                                                flag += 1;
                                            } else if (bombamiz == varan3.toString()) {
                                                flag += 1;
                                            }
                                            if (flag != 0) {
                                                if (buton.text != "☠") {
                                                    buton.y = buton.y - 1.58f
                                                    buton.isEnabled = false
                                                    if (flag == 1){
                                                        buton.setBackgroundResource(R.drawable.buton_tiklanmis_1)
                                                    }else if (flag == 2){
                                                        buton.setBackgroundResource(R.drawable.buton_tiklanmis_2)
                                                    }else if (flag == 3){
                                                        buton.setBackgroundResource(R.drawable.buton_tiklanmis_3)
                                                    }
                                                }

                                            } else {
                                                if (buton.text != "☠") {
                                                    buton.y = buton.y - 1.90f
                                                    buton.isEnabled = false
                                                    buton.text = "•"
                                                    buton.setBackgroundResource(R.drawable.buton_tiklanmis_bos)
                                                }
                                            }
                                        }
                                    }
                                } else if (buton.id == sag_alt_kose) {

                                    buton.isClickable = false
                                    if (buton.text == "") {
                                        var varan1 = sag_alt_kose - 9;
                                        var varan2 = sag_alt_kose - 10;
                                        var varan3 = sag_alt_kose - 1;
                                        var flag = 0;

                                        for (i in 0..9) {
                                            var bombamiz = rand_array[i].toString()
                                            if (bombamiz == varan1.toString()) {
                                                flag += 1;
                                            } else if (bombamiz == varan2.toString()) {
                                                flag += 1;
                                            } else if (bombamiz == varan3.toString()) {
                                                flag += 1;
                                            }
                                            if (flag != 0) {
                                                if (buton.text != "☠") {
                                                    buton.y = buton.y - 1.58f
                                                    buton.isEnabled = false
                                                    if (flag == 1){
                                                        buton.setBackgroundResource(R.drawable.buton_tiklanmis_1)
                                                    }else if (flag == 2){
                                                        buton.setBackgroundResource(R.drawable.buton_tiklanmis_2)
                                                    }else if (flag == 3){
                                                        buton.setBackgroundResource(R.drawable.buton_tiklanmis_3)
                                                    }
                                                }
                                            } else {
                                                if (buton.text != "☠") {
                                                    buton.y = buton.y - 1.90f
                                                    buton.isEnabled = false
                                                    buton.text = "•"
                                                    buton.setBackgroundResource(R.drawable.buton_tiklanmis_bos)
                                                }
                                            }
                                        }
                                    }
                                }
                                //UST MID
                                else if (buton.id >= 2 && buton.id <= 8) {
                                    buton.isClickable = false
                                    var varan1 = buton.id - 1
                                    var varan2 = buton.id + 1
                                    var varan3 = buton.id + 8
                                    var varan4 = buton.id + 9
                                    var varan5 = buton.id + 10
                                    var flag = 0;

                                    for (i in 0..9) {
                                        var bombamiz = rand_array[i].toString()
                                        if (bombamiz == varan1.toString()) {
                                            flag += 1;
                                        } else if (bombamiz == varan2.toString()) {
                                            flag += 1;
                                        } else if (bombamiz == varan3.toString()) {
                                            flag += 1;
                                        } else if (bombamiz == varan4.toString()) {
                                            flag += 1;
                                        } else if (bombamiz == varan5.toString()) {
                                            flag += 1;
                                        }
                                        if (flag != 0) {
                                            if (buton.text != "☠") {
                                                buton.y = buton.y - 0.18f
                                                buton.isEnabled = false
                                                if (flag == 1){
                                                    buton.setBackgroundResource(R.drawable.buton_tiklanmis_1)
                                                }else if (flag == 2){
                                                    buton.setBackgroundResource(R.drawable.buton_tiklanmis_2)
                                                }else if (flag == 3){
                                                    buton.setBackgroundResource(R.drawable.buton_tiklanmis_3)
                                                }else if (flag == 4){
                                                    buton.setBackgroundResource(R.drawable.buton_tiklanmis_4)
                                                }else if (flag == 5){
                                                    buton.setBackgroundResource(R.drawable.buton_tiklanmis_5)
                                                }
                                            }
                                        } else {
                                            if (buton.text != "☠") {
                                                buton.y = buton.y - 0.18f
                                                buton.isEnabled = false
                                                buton.text = "•"
                                                buton.setBackgroundResource(R.drawable.buton_tiklanmis_bos)
                                            }
                                        }
                                    }
                                }
                                // ALT MID
                                else if (buton.id >= 74 && buton.id <= 80) {
                                    buton.isClickable = false
                                    var varan1 = buton.id - 1
                                    var varan2 = buton.id + 1
                                    var varan3 = buton.id - 8
                                    var varan4 = buton.id - 9
                                    var varan5 = buton.id - 10
                                    var flag = 0;

                                    for (i in 0..9) {
                                        var bombamiz = rand_array[i].toString()
                                        if (bombamiz == varan1.toString()) {
                                            flag += 1;
                                        } else if (bombamiz == varan2.toString()) {
                                            flag += 1;
                                        } else if (bombamiz == varan3.toString()) {
                                            flag += 1;
                                        } else if (bombamiz == varan4.toString()) {
                                            flag += 1;
                                        } else if (bombamiz == varan5.toString()) {
                                            flag += 1;
                                        }
                                        if (flag != 0) {
                                            if (buton.text != "☠") {
                                                buton.y = buton.y - 0.18f
                                                buton.isEnabled = false
                                                if (flag == 1){
                                                    buton.setBackgroundResource(R.drawable.buton_tiklanmis_1)
                                                }else if (flag == 2){
                                                    buton.setBackgroundResource(R.drawable.buton_tiklanmis_2)
                                                }else if (flag == 3){
                                                    buton.setBackgroundResource(R.drawable.buton_tiklanmis_3)
                                                }else if (flag == 4){
                                                    buton.setBackgroundResource(R.drawable.buton_tiklanmis_4)
                                                }else if (flag == 5){
                                                    buton.setBackgroundResource(R.drawable.buton_tiklanmis_5)
                                                }
                                            }
                                        } else {
                                            if (buton.text != "☠") {
                                                buton.y = buton.y - 0.18f
                                                buton.isEnabled = false
                                                buton.text = "•"
                                                buton.setBackgroundResource(R.drawable.buton_tiklanmis_bos)
                                            }
                                        }
                                    }
                                }
                                //SOL MID
                                else if (buton.id == 10 || buton.id == 19 || buton.id == 28 || buton.id == 37 || buton.id == 46 || buton.id == 55 || buton.id == 64) {
                                    buton.isClickable = false
                                    var varan1 = buton.id - 9
                                    var varan2 = buton.id - 8
                                    var varan3 = buton.id + 1
                                    var varan4 = buton.id + 9
                                    var varan5 = buton.id + 10
                                    var flag = 0;

                                    for (i in 0..9) {
                                        var bombamiz = rand_array[i].toString()
                                        if (bombamiz == varan1.toString()) {
                                            flag += 1;
                                        } else if (bombamiz == varan2.toString()) {
                                            flag += 1;
                                        } else if (bombamiz == varan3.toString()) {
                                            flag += 1;
                                        } else if (bombamiz == varan4.toString()) {
                                            flag += 1;
                                        } else if (bombamiz == varan5.toString()) {
                                            flag += 1;
                                        }
                                        if (flag != 0) {
                                            if (buton.text != "☠") {
                                                buton.y = buton.y - 0.18f
                                                buton.isEnabled = false
                                                if (flag == 1){
                                                    buton.setBackgroundResource(R.drawable.buton_tiklanmis_1)
                                                }else if (flag == 2){
                                                    buton.setBackgroundResource(R.drawable.buton_tiklanmis_2)
                                                }else if (flag == 3){
                                                    buton.setBackgroundResource(R.drawable.buton_tiklanmis_3)
                                                }else if (flag == 4){
                                                    buton.setBackgroundResource(R.drawable.buton_tiklanmis_4)
                                                }else if (flag == 5){
                                                    buton.setBackgroundResource(R.drawable.buton_tiklanmis_5)
                                                }
                                            }
                                        } else {
                                            if (buton.text != "☠") {
                                                buton.y = buton.y - 0.18f
                                                buton.isEnabled = false
                                                buton.text = "•"
                                                buton.setBackgroundResource(R.drawable.buton_tiklanmis_bos)
                                            }
                                        }
                                    }
                                }
                                //SAG MID
                                else if (buton.id == 18 || buton.id == 27 || buton.id == 36 || buton.id == 45 || buton.id == 54 || buton.id == 63 || buton.id == 72) {
                                    buton.isClickable = false
                                    var varan1 = buton.id - 9
                                    var varan2 = buton.id - 10
                                    var varan3 = buton.id - 1
                                    var varan4 = buton.id + 9
                                    var varan5 = buton.id + 8
                                    var flag = 0;

                                    for (i in 0..9) {
                                        var bombamiz = rand_array[i].toString()
                                        if (bombamiz == varan1.toString()) {
                                            flag += 1;
                                        } else if (bombamiz == varan2.toString()) {
                                            flag += 1;
                                        } else if (bombamiz == varan3.toString()) {
                                            flag += 1;
                                        } else if (bombamiz == varan4.toString()) {
                                            flag += 1;
                                        } else if (bombamiz == varan5.toString()) {
                                            flag += 1;
                                        }
                                        if (flag != 0) {
                                            if (buton.text != "☠") {
                                                buton.y = buton.y - 0.18f
                                                buton.isEnabled = false
                                                if (flag == 1){
                                                    buton.setBackgroundResource(R.drawable.buton_tiklanmis_1)
                                                }else if (flag == 2){
                                                    buton.setBackgroundResource(R.drawable.buton_tiklanmis_2)
                                                }else if (flag == 3){
                                                    buton.setBackgroundResource(R.drawable.buton_tiklanmis_3)
                                                }else if (flag == 4){
                                                    buton.setBackgroundResource(R.drawable.buton_tiklanmis_4)
                                                }else if (flag == 5){
                                                    buton.setBackgroundResource(R.drawable.buton_tiklanmis_5)
                                                }
                                            }
                                        } else {
                                            if (buton.text != "☠") {
                                                buton.y = buton.y - 0.18f
                                                buton.isEnabled = false
                                                buton.text = "•"
                                                buton.setBackgroundResource(R.drawable.buton_tiklanmis_bos)
                                            }
                                        }
                                    }
                                }
                                // GOBEK MID
                                else {
                                    buton.isClickable = false
                                    val varan1 = buton.id - 1
                                    val varan2 = buton.id + 1
                                    val varan3 = buton.id + 9
                                    val varan4 = buton.id - 9
                                    val varan5 = buton.id - 10
                                    val varan6 = buton.id - 8
                                    val varan7 = buton.id + 10
                                    val varan8 = buton.id + 8
                                    var flag = 0;

                                    for (i in 0..9) {
                                        val bombamiz = rand_array[i].toString()
                                        if (bombamiz == varan1.toString()) {
                                            flag += 1;
                                        } else if (bombamiz == varan2.toString()) {
                                            flag += 1;
                                        } else if (bombamiz == varan3.toString()) {
                                            flag += 1;
                                        } else if (bombamiz == varan4.toString()) {
                                            flag += 1;
                                        } else if (bombamiz == varan5.toString()) {
                                            flag += 1;
                                        } else if (bombamiz == varan6.toString()) {
                                            flag += 1;
                                        } else if (bombamiz == varan7.toString()) {
                                            flag += 1;
                                        } else if (bombamiz == varan8.toString()) {
                                            flag += 1;
                                        }
                                        if (flag != 0) {
                                            if (buton.text != "☠") {
                                                buton.y = buton.y - 0.18f
                                                buton.isEnabled = false
                                                if (flag == 1){
                                                    buton.setBackgroundResource(R.drawable.buton_tiklanmis_1)
                                                }else if (flag == 2){
                                                    buton.setBackgroundResource(R.drawable.buton_tiklanmis_2)
                                                }else if (flag == 3){
                                                    buton.setBackgroundResource(R.drawable.buton_tiklanmis_3)
                                                }else if (flag == 4){
                                                    buton.setBackgroundResource(R.drawable.buton_tiklanmis_4)
                                                }else if (flag == 5){
                                                    buton.setBackgroundResource(R.drawable.buton_tiklanmis_5)
                                                }else if (flag == 6){
                                                    buton.setBackgroundResource(R.drawable.buton_tiklanmis_6)
                                                }else if (flag == 7){
                                                    buton.setBackgroundResource(R.drawable.buton_tiklanmis_7)
                                                }else if (flag == 8){
                                                    buton.setBackgroundResource(R.drawable.buton_tiklanmis_8)
                                                }
                                            }
                                        } else {
                                            if (buton.text != "☠") {
                                                buton.y = buton.y - 0.18f
                                                buton.isEnabled = false
                                                buton.text = "•"
                                                buton.isClickable = false
                                                buton.setBackgroundResource(R.drawable.buton_tiklanmis_bos)

                                            }
                                        }
                                    }
                                }
                            }
                        }


                        buton.textSize = 0f
                        if (buton.text == "." && kayip == 0){
                            buton.y = buton.y - 0.18f
                            buton.isClickable = false
                            buton.setBackgroundResource(R.drawable.buton_tiklanmis_bos)
                        }




                    }
                }
                // Layout'a Button ekle
                ll_main!!.addView(buton)
            }
        }
    }

    fun sayac() {

            var id = 0
            val sayac_ll = findViewById(R.id.sayac_layout) as LinearLayout
            val buton_sayac = Button(this)
            buton_sayac.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            buton_sayac.getLayoutParams().width = 350
            buton_sayac.getLayoutParams().height = 150
            buton_sayac.x = 71F
            buton_sayac.y = 30F
            buton_sayac.id = id
            buton_sayac.isClickable = false
            buton_sayac.setBackgroundResource(R.drawable.sayac)
            buton_sayac.textSize = 22F
            buton_sayac.textAlignment = TEXT_ALIGNMENT_TEXT_END
            buton_sayac.setTextColor(Color.parseColor("#FFF0F9"))
            sayac_ll.addView(buton_sayac)

            var dakika = 2
            var saniye = 60
            var timer = object : CountDownTimer(180000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    saniye--
                    buton_sayac.text = dakika.toString() + ":" + saniye.toString() + "   "

                    if (saniye < 10){
                        buton_sayac.text = dakika.toString() + ":" + "0" + saniye.toString() + "   "
                        if (saniye == 0) {
                            dakika--
                            saniye = 60
                        }
                    }
                    if (kayip == 1){
                        cancel()
                    }
                }
                override fun onFinish() {
                    sayac_ll.isVisible = false
                    ll_kaybettin.isVisible = true
                    kazandin_kaybettin()
                }
            }
            timer.start()

    }

    fun kazandin_kaybettin(){

                //////////////KAYBETME SAYFASI BURADADIR/////////////////////

        var id = 1;
        val ll_kayip = findViewById(R.id.ll_kaybettin) as LinearLayout
        val buton = ImageView(this)
        buton.id = id
        buton.isClickable = true
        ll_kaybettin.isClickable = false
        buton.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        buton.getLayoutParams().width = 800
        buton.getLayoutParams().height = 350
        buton.setBackgroundResource(R.drawable.looser_winner_button)
        buton.x = 150F
        buton.y = 1080F
        ll_kayip.addView(buton)

        buton.setOnClickListener{
            finish()
            mplayer.stop()
        }

    }

    override fun onResume() {
        super.onResume()
        mplayer = MediaPlayer.create(this, resources.getIdentifier(R.raw.game_music.toString(), "raw", packageName))
        mplayer.start()
    }
}

