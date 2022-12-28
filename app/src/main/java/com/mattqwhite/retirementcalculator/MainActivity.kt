package com.mattqwhite.retirementcalculator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.mattqwhite.retirementcalculator.databinding.ActivityMainBinding
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes

class MainActivity : AppCompatActivity() {
//    var calc: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val binding: ActivityMainBinding = ActivityMainBinding.setContentView(R.layout.activity_main)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        AppCenter.start(
            application, "5236b654-839c-4946-a4f0-5c0a2c8c291c",
            Analytics::class.java, Crashes::class.java
        )

        val calc = binding.calculate
        calc.setOnClickListener {
//            throw Exception("Something wrong happened")
//            Crashes.generateTestCrash()
            try {
                val interestRate = binding.interest.text.toString().toFloat()
                val currentAge = binding.age.text.toString().toFloat()
                val retirementAge = binding.retirement.text.toString().toFloat()
                val monthly = binding.monthly.text.toString().toFloat()
                val current = binding.savings.text.toString().toFloat()

                val properties = HashMap<String, String>()
                properties.put("interest_rate", interestRate.toString())
                properties.put("current_age", currentAge.toString())
                properties.put("retirement_age", retirementAge.toString())
                properties.put("monthly_savings", monthly.toString())
                properties.put("current_savings", current.toString())

                if (interestRate <= 0) {
                    Analytics.trackEvent("wrong_interest_rate")
                }
                if (retirementAge <= currentAge) {
                    Analytics.trackEvent("wrong_age")
                }
            } catch (ex: Exception) {
                Analytics.trackEvent(ex.message)
            }
        }
    }
}
