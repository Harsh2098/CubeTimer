package com.hmproductions.cubetimer

import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.preference.PreferenceManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.hmproductions.cubetimer.utils.StatisticsRecyclerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.textColor

class MainActivity : AppCompatActivity() {

    private lateinit var model: StatisticsViewModel
    private lateinit var preferences: SharedPreferences
    private lateinit var statisticsRecyclerAdapter: StatisticsRecyclerAdapter

    private var running = false
    private var ready = false
    private lateinit var holdingTimer: CountDownTimer
    private lateinit var actualTimer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        model = ViewModelProviders.of(this).get(StatisticsViewModel::class.java)
        preferences = PreferenceManager.getDefaultSharedPreferences(this)
        statisticsRecyclerAdapter = StatisticsRecyclerAdapter(this, null)

        statsRecyclerView.layoutManager = LinearLayoutManager(this)
        statsRecyclerView.setHasFixedSize(false)
        statsRecyclerView.adapter = statisticsRecyclerAdapter

        subscribeToStatistics(CubeType.values()[preferences.getInt(CUBE_TYPE_KEY, 0)])

        setupTimer()
    }

    private fun subscribeToStatistics(cubeType: CubeType) {
        when (cubeType) {
            CubeType.RUBIK -> model.rubikStatistics

            CubeType.REVENGE -> model.revengeStatistics

            CubeType.PROFESSOR -> model.professorStatistics
        }
    }

    private fun setupTimer() {

        holdingTimer = object : CountDownTimer(500, 500) {
            override fun onFinish() {
                timerTextView.textColor = ContextCompat.getColor(this@MainActivity, R.color.ready)
                ready = true
            }

            override fun onTick(millisUntilFinished: Long) {}
        }

        actualTimer = object : CountDownTimer(600000, 1) {
            override fun onFinish() {
                running = false
                ready = false
                showConstraintLayout(true)
            }

            override fun onTick(millisUntilFinished: Long) {
                showConstraintLayout(false)
                running = true
                val elapsed = 600000 - millisUntilFinished

                var temp = if (elapsed / 60000 < 10) "0" else ""
                temp += (elapsed / 60000).toString() + ":"
                temp += (if (elapsed % 60000 / 1000 < 10) "0" else "") + (elapsed % 60000 / 1000).toString() + ":"
                temp += if (elapsed % 1000 < 10) "0" else ""
                temp += (if (elapsed % 1000 < 100) "0" else "") + elapsed % 1000
                timerTextView.text = temp
            }
        }

        timerTextView.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                if (running) {
                    saveToDb(timerTextView.text.toString())
                    running = false
                    ready = false
                    actualTimer.cancel()
                    showConstraintLayout(true)
                } else {
                    timerTextView.text = getString(R.string.timer_place_holder)
                    timerTextView.textColor = ContextCompat.getColor(this@MainActivity, R.color.not_ready)
                    holdingTimer.start()
                }
            } else if (event.action == MotionEvent.ACTION_UP) {

                timerTextView.textColor = ContextCompat.getColor(this, R.color.headings)
                if (ready) {
                    actualTimer.start()
                } else {
                    holdingTimer.cancel()
                }
            }

            Log.v(":::", event.toString())

            true
        }
    }

    private fun saveToDb(currentTime: String) {
        // TODO: Implement this
    }

    private fun showConstraintLayout(showLayout: Boolean) {
        statisticsConstraintLayout.visibility = if (showLayout) View.VISIBLE else View.INVISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_activity_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {

            R.id.rubik_action -> subscribeToStatistics(CubeType.RUBIK)

            R.id.revenge_action -> subscribeToStatistics(CubeType.REVENGE)

            R.id.professor_action -> subscribeToStatistics(CubeType.PROFESSOR)
        }

        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val CUBE_TYPE_KEY = "cube-type-key"
    }
}
