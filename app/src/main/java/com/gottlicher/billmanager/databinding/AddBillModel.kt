package com.gottlicher.billmanager.databinding

import android.databinding.BaseObservable



data class BillApp(
        var appPackage: String,
        var appName: String) {
    constructor() : this("","")
}

data class AddBillModel(
        var name: String,
        var dayOfMonthDue: String,
        var dayOfMonthAvail: String,
        var app: BillApp) : BaseObservable() {
    constructor() : this("","","",BillApp())

}