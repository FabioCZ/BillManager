package com.gottlicher.billmanager.home

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.gottlicher.billmanager.model.Bill

/**
 * Created by fabio.gottlicher on 2/19/18.
 */
class HomeBillsAdapter : RecyclerView.Adapter<HomeBillsAdapter.BillViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillViewHolder {

    }

    override fun onBindViewHolder(holder: BillViewHolder?, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int = 5

    class BillViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}