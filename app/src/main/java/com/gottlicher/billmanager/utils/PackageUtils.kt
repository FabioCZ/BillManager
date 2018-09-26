package com.gottlicher.billmanager.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.content.Intent
import android.content.pm.PackageManager


data class PkgInfo(val packageName: String, val appName:String, var icon: Drawable, var iconInitialized: Boolean)

fun getInstalledPackages(context: Context) : List<PkgInfo> {
    val list = ArrayList<PkgInfo> ()
    val pkgManager = context.packageManager
    val installed = pkgManager.getInstalledPackages(0)
    for (pkg in installed) {
        val name = pkg.applicationInfo.loadLabel(pkgManager).toString()
        val icon = pkg.applicationInfo.loadIcon(pkgManager)

        list.add(PkgInfo(pkg.packageName, name, icon, true))
    }
    return list
}

fun getInstalledApps(context: Context) : List<PkgInfo> {
    val list = ArrayList<PkgInfo>()
    val pkgManager = context.packageManager


    val intent = Intent(Intent.ACTION_MAIN, null)
    intent.addCategory(Intent.CATEGORY_LAUNCHER)
    val resInfos = pkgManager.queryIntentActivities(intent, 0).distinctBy { ri -> ri.activityInfo.packageName }

    for(resIn in resInfos) {
        val pInfo = pkgManager.getApplicationInfo(resIn.activityInfo.packageName, PackageManager.GET_META_DATA)
        val name = pInfo.loadLabel(pkgManager).toString()
        //val icon = pInfo.loadIcon(pkgManager)
        list.add(PkgInfo(pInfo.packageName, name, pkgManager.defaultActivityIcon, false))
    }

    return list.sortedBy { p -> p.appName }
}