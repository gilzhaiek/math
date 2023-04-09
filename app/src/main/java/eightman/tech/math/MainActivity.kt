package eightman.tech.math

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import eightman.tech.math.MainActivity.MainActivity.ERROR
import eightman.tech.math.MainActivity.MainActivity.LAST_DIGIT
import eightman.tech.math.MainActivity.MainActivity.SUCCESS
import eightman.tech.math.databinding.ActivityMainBinding
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val sharedPref by lazy { getPreferences(Context.MODE_PRIVATE) }
    private var lastDigit: Int = 1
    private var clearColor: Int = 0
    private var setColor: Int = 0
    private var twoSelected: Boolean = false
    private var threeSelected: Boolean = false
    private var fiveSelected: Boolean = false
    private var sixSelected: Boolean = false
    private var nineSelected: Boolean = false
    private var tenSelected: Boolean = false

    private fun onClicked(it: View, selected:Boolean) : Boolean {
        if (selected)
            it.setBackgroundColor(clearColor)
        else
            it.setBackgroundColor(setColor)
        binding.statusTV.text = ""
        return !selected
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        clearColor = ContextCompat.getColor(this,  androidx.appcompat.R.color.material_grey_100)
        setColor = ContextCompat.getColor(this, R.color.teal_700)

        lastDigit = sharedPref.getInt(LAST_DIGIT, 1)

        binding.numberTV.text = lastDigit.toString()

        clearColors()

        binding.twoBtn.setOnClickListener {
            twoSelected = onClicked(it, twoSelected)
        }
        binding.threeBtn.setOnClickListener {
            threeSelected = onClicked(it, threeSelected)
        }
        binding.fiveBtn.setOnClickListener {
            fiveSelected = onClicked(it, fiveSelected)
        }
        binding.sixBtn.setOnClickListener {
            sixSelected = onClicked(it, sixSelected)
        }
        binding.nineBtn.setOnClickListener {
            nineSelected = onClicked(it, nineSelected)
        }
        binding.tenBtn.setOnClickListener {
            tenSelected = onClicked(it, tenSelected)
        }
        binding.testBtn.setOnClickListener {
            if (twoSelected && lastDigit % 2 != 0)
                binding.statusTV.text = ERROR
            else if (threeSelected && lastDigit % 3 != 0)
                binding.statusTV.text = ERROR
            else if (fiveSelected && lastDigit % 5 != 0)
                binding.statusTV.text = ERROR
            else if (sixSelected && lastDigit % 6 != 0)
                binding.statusTV.text = ERROR
            else if (nineSelected && lastDigit % 9 != 0)
                binding.statusTV.text = ERROR
            else if (tenSelected && lastDigit % 10 != 0)
                binding.statusTV.text = ERROR
            else {
                binding.statusTV.text = SUCCESS
                // save the task list to preference
                with(sharedPref.edit()) {
                    try {
                        lastDigit++
                        putInt(LAST_DIGIT, lastDigit)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    commit()
                }
            }
            if (binding.statusTV.text == ERROR)
                clearColors()
        }
    }

    private fun clearColors() {
        binding.twoBtn.setBackgroundColor(clearColor)
        binding.threeBtn.setBackgroundColor(clearColor)
        binding.fiveBtn.setBackgroundColor(clearColor)
        binding.sixBtn.setBackgroundColor(clearColor)
        binding.nineBtn.setBackgroundColor(clearColor)
        binding.tenBtn.setBackgroundColor(clearColor)
        twoSelected = false
        threeSelected = false
        fiveSelected = false
        sixSelected = false
        nineSelected = false
        tenSelected = false
    }

    object MainActivity {
        const val LAST_DIGIT = "LAST-DIGIT"
        const val ERROR = "ERROR"
        const val SUCCESS = "SUCCESS"
    }
}