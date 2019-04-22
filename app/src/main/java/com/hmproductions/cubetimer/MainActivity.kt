package com.hmproductions.cubetimer

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.hmproductions.cubetimer.data.CubeType
import com.hmproductions.cubetimer.data.Record
import com.hmproductions.cubetimer.data.Statistic
import com.hmproductions.cubetimer.data.StatisticsViewModel
import com.hmproductions.cubetimer.utils.ScrambleGenerator.*
import com.hmproductions.cubetimer.utils.StatisticsRecyclerAdapter
import com.hmproductions.cubetimer.utils.getDateFromTimeInMillis
import com.hmproductions.cubetimer.utils.getTimerFormatString
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.textColor

class MainActivity : AppCompatActivity() {

    private lateinit var model: StatisticsViewModel
    private lateinit var preferences: SharedPreferences
    private lateinit var statisticsRecyclerAdapter: StatisticsRecyclerAdapter

    private lateinit var holdingTimer: CountDownTimer
    private lateinit var actualTimer: CountDownTimer
    private var observer: Observer<List<Statistic>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        model = ViewModelProviders.of(this).get(StatisticsViewModel::class.java)
        preferences = PreferenceManager.getDefaultSharedPreferences(this)
        statisticsRecyclerAdapter = StatisticsRecyclerAdapter(this, null)

        statsRecyclerView.layoutManager = LinearLayoutManager(this)
        statsRecyclerView.setHasFixedSize(false)
        statsRecyclerView.adapter = statisticsRecyclerAdapter

        with(CubeType.values()[preferences.getInt(CUBE_TYPE_KEY, 0)]) {
            model.setCubeTypeFromPreferences(this)
            subscribeToStatistics(this)
        }

        setupTimer()
    }

    private fun subscribeToStatistics(cubeType: CubeType) {

        model.setCubeTypeFromPreferences(cubeType)
        setupNewScramble()

        if (observer != null) {
            model.rubikStatistics.removeObserver(observer!!)
            model.revengeStatistics.removeObserver(observer!!)
            model.professorStatistics.removeObserver(observer!!)
        }

        observer =
            Observer { data ->
                val newData = mutableListOf<Record>()

                for (i in 0 until data.size) {
                    var ao5 = -1L
                    var ao12 = -1L

                    if (data.size - i >= 12) {
                        ao12 = 0
                        for (j in i..i + 11)
                            ao12 += data[j].timeInMillis
                        ao12 /= 12
                    }

                    if (data.size - i >= 5) {
                        ao5 = 0
                        var maxPos = i
                        var minPos = i
                        for (j in i + 1..i + 4) {
                            if (data[j].timeInMillis > maxPos) maxPos = j
                            if (data[j].timeInMillis < minPos) minPos = j
                        }
                        for (j in i..i + 4) {
                            if (j != maxPos && j != minPos)
                                ao5 += data[j].timeInMillis
                        }
                        ao5 /= 3
                    }

                    newData.add(
                        Record(
                            data.size - i, data[i].timeString, getTimerFormatString(ao5),
                            getTimerFormatString(ao12), getDateFromTimeInMillis(data[i].realTimeInMillis)
                        )
                    )
                }

                statisticsRecyclerAdapter.swapData(newData)
            }

        when (cubeType) {
            CubeType.RUBIK -> model.rubikStatistics.observe(this, observer!!)

            CubeType.REVENGE -> model.revengeStatistics.observe(this, observer!!)

            CubeType.PROFESSOR -> model.professorStatistics.observe(this, observer!!)
        }
    }

    private fun setupTimer() {

        holdingTimer = object : CountDownTimer(500, 500) {
            override fun onFinish() {
                setTimerColor(this@MainActivity, R.color.ready)
                model.ready = true
            }

            override fun onTick(millisUntilFinished: Long) {}
        }

        actualTimer = object : CountDownTimer(MAX_TIMER_TIME, 1) {
            override fun onFinish() {
                model.running = false
                model.ready = false
                showConstraintLayout(true)
            }

            override fun onTick(millisUntilFinished: Long) {
                showConstraintLayout(false)
                model.running = true
                model.currentTime = MAX_TIMER_TIME - millisUntilFinished
                timerTextView.text = getTimerFormatString(MAX_TIMER_TIME - millisUntilFinished)
            }
        }

        timerTextView.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                if (model.running) {
                    model.insertStatistic(
                        timerTextView.text.toString(),
                        scrambleTextView.text.toString(),
                        model.currentTime,
                        System.currentTimeMillis()
                    )
                    model.running = false
                    model.ready = false
                    actualTimer.cancel()
                    showConstraintLayout(true)
                } else {
                    timerTextView.text = getTimerFormatString(0)
                    setTimerColor(this, R.color.not_ready)
                    holdingTimer.start()
                }
            } else if (event.action == MotionEvent.ACTION_UP) {

                setTimerColor(this, R.color.headings)
                if (model.ready) {
                    actualTimer.start()
                } else {
                    holdingTimer.cancel()
                }
            }

            true
        }
    }

    private fun setupNewScramble() {
        scrambleTextView.text = when (model.currentCubeType) {
            CubeType.RUBIK -> generate3x3Scramble()
            CubeType.REVENGE -> generate4x4Scramble()
            CubeType.PROFESSOR -> generate5x5Scramble()
        }
    }

    private fun setTimerColor(context: Context, colorId: Int) {
        timerTextView.textColor = ContextCompat.getColor(context, colorId)
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

    override fun onPause() {
        super.onPause()
        preferences.edit().putInt(CUBE_TYPE_KEY, model.currentCubeType.ordinal).apply()
    }

    companion object {
        private const val CUBE_TYPE_KEY = "cube-type-key"
        private const val MAX_TIMER_TIME = 600000L
    }
}
