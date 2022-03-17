package pl.edu.pwr.lab1.i242487.ui

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import pl.edu.pwr.lab1.i242487.R

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityUITest {

@get:Rule
val activityRule = ActivityScenarioRule(MainActivity::class.java)

@Test
fun testBMICalculator() {

    val tietMass = onView(withId(R.id.tietMass))
    tietMass.perform(click(), replaceText("50.0"), closeSoftKeyboard())

    val tietHeight = onView(withId(R.id.tietHeight))
    tietHeight.perform(click(), replaceText("150.0"), closeSoftKeyboard())

    onView(withId(R.id.btnCalculate)).perform(click())

    onView(withId(R.id.tvBmiValue)).check(matches(isDisplayed()))
}

}