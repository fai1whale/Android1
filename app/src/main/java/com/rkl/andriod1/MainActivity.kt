package com.rkl.andriod1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rkl.andriod1.details.ForecastDetailsActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    private val forecastRepository = ForecastRepository()

    // region Setup Methods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val zipcodeEditText : EditText = findViewById(R.id.zipcodeEditText)
        val enterButton: Button = findViewById(R.id.enterButton)


        enterButton.setOnClickListener{
            val zipcode: String = zipcodeEditText.text.toString()

            if(zipcode.length != 5){
                Toast.makeText(this, R.string.zipcode_entry_error, Toast.LENGTH_LONG).show()

            }else{
                forecastRepository.loadForecast(zipcode)
            }
        }


        val forecastList : RecyclerView = findViewById(R.id.forecastList)
        forecastList.layoutManager = LinearLayoutManager(this)


        //Go from one activity to the next
        val dailyForecastAdapter = DailyForecastAdapter(){
            val forecastDetailsIntent = Intent(this, ForecastDetailsActivity::class.java)
            startActivity(forecastDetailsIntent)
        }
        forecastList.adapter = dailyForecastAdapter



        val weeklyForecastObserver = Observer<List<DailyForecast>>{forecastItems ->
            //update our list adapter
            dailyForecastAdapter.submitList(forecastItems)

            //Toast.makeText(this,"Loaded Items", Toast.LENGTH_LONG).show()

        }

        forecastRepository.weeklyForecast.observe(this, weeklyForecastObserver )

    }
}