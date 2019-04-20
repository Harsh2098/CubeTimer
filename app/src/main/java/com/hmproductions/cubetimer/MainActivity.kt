package com.hmproductions.cubetimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hmproductions.cubetimer.utils.ScrambleGenerator.generate4x4Scramble
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        scrambleTextView.text = generate4x4Scramble()
    }
}
