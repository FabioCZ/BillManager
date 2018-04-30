package com.gottlicher.billmanager.home

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gottlicher.billmanager.databinding.ItemBillMainBinding
import com.gottlicher.billmanager.model.Bill
import kotlinx.android.extensions.LayoutContainer

/**
 * Created by fabio.gottlicher on 2/19/18.
 */
class HomeBillsAdapter(val items: List<Bill>) : RecyclerView.Adapter<HomeBillsAdapter.BillViewHolder> () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):BillViewHolder {
        val li = LayoutInflater.from(parent.context);
        val itemBinding = ItemBillMainBinding.inflate(li, parent, false)
        return BillViewHolder (itemBinding)
    }

    override fun onBindViewHolder(holder: BillViewHolder, position: Int) = holder.bind(items[position])
    override fun getItemCount(): Int = items.size

    class BillViewHolder(val binding: ItemBillMainBinding) : RecyclerView.ViewHolder(binding.root), LayoutContainer {
        override val containerView: View?
            get() = binding.root

        fun bind(bill: Bill) {
            binding.bill = bill
            binding.executePendingBindings()
        }
    }
}