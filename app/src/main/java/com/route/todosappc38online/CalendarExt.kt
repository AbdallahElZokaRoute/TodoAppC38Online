package com.route.todosappc38online

import java.util.Calendar


fun Calendar.clearTime() {
    set(Calendar.HOUR, 0)
    set(Calendar.HOUR_OF_DAY, 0)
    set(Calendar.MINUTE, 0)
    set(Calendar.SECOND, 0)
    set(Calendar.MILLISECOND, 0)

}