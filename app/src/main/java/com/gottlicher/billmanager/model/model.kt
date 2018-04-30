package com.gottlicher.billmanager.model

import android.databinding.BaseObservable
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne
import io.objectbox.relation.ToMany
import io.objectbox.annotation.Backlink
import java.util.*


/**
 * Created by fabio.gottlicher on 2/19/18.
 */

@Entity data class Bill(
        @Id var id: Long = 0,
        val name: String,
        val dayOfMonthDue: Int,
        val dayOfMonthAvail: Int,
        val appOrWebName: String) : BaseObservable (){
    constructor() : this(0,"",0,0,"")

    @Backlink  val pastPaid: ToMany<PastPaidBill>? = null

    var _isPaid = false;
    var isPaidThisMonth:Boolean
        get() = _isPaid
        set(value) { _isPaid = value }

    fun getNextDueDateString(): String {
        return "4/$dayOfMonthDue/2018"
    }
}

@Entity class PastPaidBill(
        @Id var id: Long = 0,
        var paid: Date) : BaseObservable (){
    constructor() : this(0,Date())
    lateinit var bill: ToOne<Bill>
}