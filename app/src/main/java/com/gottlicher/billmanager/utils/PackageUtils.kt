package com.gottlicher.billmanager.utils

import android.content.Context
import android.graphics.drawable.Drawable

data class PkgInfo(val packageName: String, val appName:String,  val icon: Drawable)

fun getInstalledPackages(context: Context) : List<PkgInfo> {
    val list = ArrayList<PkgInfo> ()
    val pkgManager = context.packageManager;
    val installed = pkgManager.getInstalledPackages(0)
    for (pkg in installed) {
        val name = pkg.applicationInfo.loadLabel(pkgManager).toString()
        val icon = pkg.applicationInfo.loadIcon(pkgManager)

        list.add(PkgInfo(pkg.packageName, name, icon))
    }
    return list
}