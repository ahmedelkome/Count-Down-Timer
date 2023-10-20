package com.ahmed.countdowntimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.PersistableBundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    val starttime : Long = 25 * 60 * 1000
    var reimagining : Long = starttime
    lateinit var pomo: TextView
    var timer : CountDownTimer? = null
    var istimerunning = false
    lateinit var Time: TextView
    lateinit var start:Button
    lateinit var reset:TextView
    lateinit var pb:ProgressBar
     var key = "key"
    private fun id(){
        pomo=findViewById(R.id.pomo_tv)
        Time=findViewById(R.id.time_tv)
        start=findViewById(R.id.start_btn)
        reset=findViewById(R.id.reset_tv)
        pb= findViewById(R.id.progressBar)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        id()


        start.setOnClickListener {
            if (istimerunning == false) {
                starttimer(starttime)
                pomo.text = resources.getText(R.string.keep_going)
            }
        }


        reset.setOnClickListener {
            resettime()
        }
    }


    private fun resettime(){
        timer?.cancel()
        reimagining =starttime
        Updatetime()
        pomo.text = resources.getText(R.string.Take_a_Pomodor)
        istimerunning = false
        pb.progress = 100

    }



    private fun starttimer(Starttime : Long) {
         timer = object : CountDownTimer(Starttime , 1 * 1000) {

             override fun onTick(timelift: Long) {
                reimagining=timelift
                Updatetime()
                 pb.progress = reimagining.toDouble().div(starttime.toDouble()).times(100).toInt()
            }

             override fun onFinish() {
            Toast.makeText(this@MainActivity,"finish",Toast.LENGTH_SHORT).show()
                 istimerunning = false
            }
        }.start()
        istimerunning = true

    }


    private fun Updatetime() {
        val minute = reimagining.div(1000).div(60)
        val second = reimagining.div(1000) % 60
        val formattime = String.format("%02d:%02d", minute, second)
        Time.text = formattime
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(key,reimagining)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
       val savedtime =  savedInstanceState.getLong(key)
        if (savedtime!=starttime)
        starttimer(savedtime)
    }
}