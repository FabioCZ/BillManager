package com.gottlicher.billmanager.home

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.gottlicher.billmanager.R

import kotlinx.android.synthetic.main.activity_add_bill.*

class AddBillActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_bill)
        setSupportActionBar(toolbar)

    }

}
