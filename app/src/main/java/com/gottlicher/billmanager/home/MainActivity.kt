package com.gottlicher.billmanager.home

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.gottlicher.billmanager.BillManagerApplication
import com.gottlicher.billmanager.R
import com.gottlicher.billmanager.model.Bill
import com.gottlicher.billmanager.model.PastPaidBill
import io.objectbox.BoxStore
import io.objectbox.kotlin.boxFor
import org.jetbrains.anko.toast
import java.util.*
import javax.inject.Inject
import kotlinx.coroutines.experimental.android.UI as UI

class MainActivity : AppCompatActivity() {

    @Inject lateinit var objectBox : BoxStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        BillManagerApplication.graph.inject(this)
        var billBox = objectBox.boxFor<Bill>()

        var bill = Bill(name = "Pizza", dayOfMonthDue = 25, dayOfMonthAvail = 10, appOrWebName = "PizzaApp")
        bill.pastPaid?.add(PastPaidBill(paid = Date(2018,1,1)))


        billBox.put(bill)

        var all = billBox.all
        toast(all.first().name)
    }

}
