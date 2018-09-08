package com.gottlicher.billmanager.home

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.gottlicher.billmanager.BillManagerApplication
import com.gottlicher.billmanager.R
import com.gottlicher.billmanager.dialog.AsyncAlertDialog
import com.gottlicher.billmanager.model.Bill
import com.gottlicher.billmanager.model.PastPaidBill
import io.objectbox.BoxStore
import io.objectbox.kotlin.boxFor
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast
import java.util.*
import javax.inject.Inject
import kotlinx.coroutines.experimental.android.UI as UI

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var objectBox: BoxStore

    var isLoading:Boolean
        get() = mainProgress.visibility == View.VISIBLE
        set(value){
            mainProgress.visibility = if (value) View.VISIBLE else View.GONE
            mainBillList.visibility = if(value) View.GONE else View.VISIBLE
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        BillManagerApplication.graph.inject(this)

        mainPlusFab.onClick { onPlusClick() }
        mainBillList.layoutManager = LinearLayoutManager (this)
        loadBillsIntoView()
    }

    private fun loadBillsIntoView() = launch(UI) {
        isLoading = true
        delay(500)
        val allBills = async {
            val billBox = objectBox.boxFor<Bill>()
            delay(1000)
            billBox.all
        }.await()
        mainBillList.adapter = HomeBillsAdapter(allBills)
        mainBillList.adapter.notifyDataSetChanged()
        isLoading = false
    }

    private fun onPlusClick() = launch(UI) {
        loadBillsIntoView()
        val res = AsyncAlertDialog (R.string.discard_this_bill, positiveButtonText = R.string.discard, negativeButtonText = R.string.cancel).show(this@MainActivity)
        toast(res.toString())
//        val intent = Intent(this@MainActivity, AddBillActivity::class.java)
//        this@MainActivity.startActivity(intent)
    }

    fun test(){
        var billBox = objectBox.boxFor<Bill>()

        var bill = Bill(name = "Pizza", dayOfMonthDue = 25, dayOfMonthAvail = 10, appOrWebName = "PizzaApp")
        bill.pastPaid?.add(PastPaidBill(paid = Date(2018,1,1)))


        billBox.put(bill)

        var all = billBox.all
        toast(all.first().name)
    }

}
