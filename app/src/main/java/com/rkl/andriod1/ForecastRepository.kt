package com.rkl.andriod1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.random.Random

class ForecastRepository {


    private val _weeklyForecast = MutableLiveData<List<DailyForecast>>()
    val weeklyForecast: LiveData<List<DailyForecast>> = _weeklyForecast

    fun loadForecast(zipcode: String){
        val randomValues = List(10){ Random.nextFloat().rem(100) * 100 }
        val forecastItems = randomValues.map {temp ->
            DailyForecast(temp, getTempDescription(temp))
        }

        _weeklyForecast.setValue(forecastItems)
    }

    private fun getTempDescription(temp: Float) : String {
        return when (temp)
        {
            in Float.MIN_VALUE.rangeTo(0f) -> "Anything below zero doesn't make sense"
            in 0f.rangeTo(32f)-> "Way too cold"
            in 32f.rangeTo(55f)-> "Colder than I would prefer"
            in 55f.rangeTo(65f)-> "Getting Better"
            in 65f.rangeTo(80f)-> "This is the sweat spot"
            in 80f.rangeTo(90f)-> "Getting at little warm"
            in 90f.rangeTo(100f)-> "Where's the A/C"
            in 100f.rangeTo(Float.MAX_VALUE)-> "Death Valley"
            else -> "Does not compute"
        }
    }
    
}