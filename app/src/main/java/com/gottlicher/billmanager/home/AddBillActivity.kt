package com.gottlicher.billmanager.home

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.gottlicher.billmanager.R
import io.objectbox.BoxStore

import kotlinx.android.synthetic.main.activity_add_bill.*
import javax.inject.Inject

class AddBillActivity : AppCompatActivity() {

    @Inject
    lateinit var objectBox: BoxStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_bill)
        setSupportActionBar(toolbar)
    }

}
