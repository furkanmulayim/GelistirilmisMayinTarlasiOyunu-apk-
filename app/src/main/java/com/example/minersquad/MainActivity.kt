package com.example.minersquad

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var mplayer :MediaPlayer = MediaPlayer()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var timer = object : CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                buton_nasil_oynanir.isEnabled = false
                buton_start.isEnabled = false
            }
            override fun onFinish() {
                buton_nasil_oynanir.isEnabled = true
                buton_start.isEnabled = true
            }
        }
        timer.start()

        //fullscreen yaptık uygulamayı
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)

        //animasyonu deklare ediyoruz
        val ttb = AnimationUtils.loadAnimation(this, R.anim.ttb)
        val ttb2 = AnimationUtils.loadAnimation(this, R.anim.ttb2)
        val ttb3 = AnimationUtils.loadAnimation(this, R.anim.ttb3)
        val ttb4 = AnimationUtils.loadAnimation(this, R.anim.ttb4)


        //animasyonu ayarlıyoruz
        logo_main.startAnimation(ttb)
        game_name_main.startAnimation(ttb2)
        buton_start.startAnimation(ttb3)
        buton_nasil_oynanir.startAnimation(ttb4)


        //AKTİVİTE DEĞİŞTİRME BUTONU
        buton_start.setOnClickListener{

            val intent = Intent(applicationContext, Game::class.java)
            startActivity(intent)
            mplayer.stop()

        }


        buton_nasil_oynanir.setOnClickListener{

            nasil_oynanir_ll.isVisible = true
            buton_back.isVisible = true
            buton_nasil_oynanir.isVisible = false
            buton_start.isVisible = false
            logo_main.isVisible = false
            game_name_main.isVisible = false
            buton_back.startAnimation(ttb3)

            var timer = object : CountDownTimer(2000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    buton_back.isEnabled = false
                    buton_nasil_oynanir.isEnabled = false
                    buton_start.isEnabled = false
                }
                override fun onFinish() {
                    buton_back.isEnabled = true
                    buton_nasil_oynanir.isEnabled = true
                    buton_start.isEnabled = true
                }
            }
            timer.start()

        }

        buton_back.setOnClickListener{

            nasil_oynanir_ll.isVisible = false
            buton_back.isVisible = false
            buton_nasil_oynanir.isVisible = true
            buton_start.isVisible = true
            logo_main.isVisible = true
            game_name_main.isVisible = true

            var timer = object : CountDownTimer(2000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    buton_back.isEnabled = false
                    buton_nasil_oynanir.isEnabled = false
                    buton_start.isEnabled = false
                }
                override fun onFinish() {
                    buton_back.isEnabled = true
                    buton_nasil_oynanir.isEnabled = true
                    buton_start.isEnabled = true
                }
            }
            timer.start()

            logo_main.startAnimation(ttb)
            game_name_main.startAnimation(ttb2)
            buton_start.startAnimation(ttb3)
            buton_nasil_oynanir.startAnimation(ttb4)

        }



    }

    override fun onBackPressed() {
        System.exit(0);
    }

    override fun onStop() {
        super.onStop()
        mplayer.stop()
    }

    override fun onResume() {
        super.onResume()
        mplayer = MediaPlayer.create(this, resources.getIdentifier(R.raw.msc.toString(), "raw", packageName))
        mplayer.start()
    }


}