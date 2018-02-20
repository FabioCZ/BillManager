package com.gottlicher.billmanager.model

import java.util.*

/**
 * Created by fabio.gottlicher on 2/19/18.
 */

data class Bill (val name: String,
                 val dayOfMonthDue: Int,
                 val dayOfMonthAvail: Int,
                 val appOrWebName: Int,
                 val pastPaid: List<Date>)