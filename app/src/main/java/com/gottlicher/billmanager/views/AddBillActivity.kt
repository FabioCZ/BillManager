package com.gottlicher.billmanager.views

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.gottlicher.billmanager.BillManagerApplication
import com.gottlicher.billmanager.R
import com.gottlicher.billmanager.databinding.ActivityAddBillBinding
import com.gottlicher.billmanager.databinding.AddBillModel
import com.gottlicher.billmanager.model.Bill
import io.objectbox.BoxStore
import io.objectbox.kotlin.boxFor

import kotlinx.android.synthetic.main.activity_add_bill.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast
import javax.inject.Inject

const val REQUEST_CODE_PICK_APP = 1
const val EXTRA_APP_PACKAGE = "EXTRA_APP_PACKAGE"
class AddBillActivity : AppCompatActivity() {

    @Inject
    lateinit var objectBox: BoxStore

    val billToAdd: AddBillModel = AddBillModel()
    var isLoading:Boolean
        get() = button_save.visibility == View.VISIBLE
        set(value){
            progress_save.visibility = if (value) View.VISIBLE else View.GONE
            button_save.visibility = if(value) View.GONE else View.VISIBLE
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        BillManagerApplication.graph.inject(this)

        val binding = DataBindingUtil.setContentView<ActivityAddBillBinding>(this, R.layout.activity_add_bill)

        binding.bill = billToAdd
        binding.executePendingBindings()
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        billToAdd.app.appName = resources.getString(R.string.tap_here_to_select_app)

        button_save.onClick { onSaveClick() }
        button_exit.onClick { finish() }
        appInfoContainer.onClick { onAppClick() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_PICK_APP) {
            val packageName = data?.getStringExtra(EXTRA_APP_PACKAGE)
            toast(packageName ?: "NA")
        }
    }

    private fun onSaveClick() = launch(UI) {
        isLoading = true

        try {
            launch {
            val billDb = Bill(name = billToAdd.name, dayOfMonthDue = billToAdd.dayOfMonthDue.toInt(), dayOfMonthAvail = billToAdd.dayOfMonthAvail.toInt(), appPackage = billToAdd.app.appPackage)
            val billBox = objectBox.boxFor<Bill>()
            billBox.put(billDb)
            }.join()
        } catch (e:Exception) {
            toast(R.string.error_saving_bill)
        } finally {
            isLoading = false
        }
        this@AddBillActivity.finish()

    }

    private fun onAppClick() {
        val intent = Intent(this, AppPickerActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE_PICK_APP)
    }

}
