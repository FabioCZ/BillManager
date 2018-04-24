package com.gottlicher.billmanager.home

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.gottlicher.billmanager.R
import com.gottlicher.billmanager.model.Bill
import com.gottlicher.billmanager.utils.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_bill_main.*

/**
 * Created by fabio.gottlicher on 2/19/18.
 */
class HomeBillsAdapter(val items: List<Bill>) : RecyclerView.Adapter<HomeBillsAdapter.BillViewHolder> () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BillViewHolder(parent.inflate(R.layout.item_bill_main))

    override fun onBindViewHolder(holder: BillViewHolder, position: Int) = holder.bind(items[position])
    override fun getItemCount(): Int = items.size

    class BillViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(bill: Bill) {
            billName.text = bill.name
            billDueDate.text = bill.dayOfMonthDue.toString()
        }
    }
}