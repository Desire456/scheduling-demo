package ru.tbank.schedulingdemo.quartz

import org.quartz.impl.calendar.BaseCalendar
import org.springframework.stereotype.Component
import ru.tbank.schedulingdemo.quartz.ChristmasHolidayCalendar.Companion.BEAN_NAME
import java.util.*
import java.util.GregorianCalendar.DECEMBER

/**
 * Календарь, который позволяет не запускать задания в рождество.
 */
@Component(BEAN_NAME)
@QuartzProfile
class ChristmasHolidayCalendar : BaseCalendar() {

    override fun isTimeIncluded(timeStamp: Long): Boolean {
        val cal = GregorianCalendar()
        cal.time = Date(timeStamp)

        // Check if the date is Christmas (December 25th)
        val month = cal[GregorianCalendar.MONTH]
        val day = cal[GregorianCalendar.DAY_OF_MONTH]

        if (month == DECEMBER && day == 25) {
            return false // Exclude Christmas
        }
        return super.isTimeIncluded(timeStamp)
    }

    companion object {
        const val BEAN_NAME = "christmasCalendar"
    }
}