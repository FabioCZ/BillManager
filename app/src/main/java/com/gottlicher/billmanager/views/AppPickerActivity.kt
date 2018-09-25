package com.gottlicher.billmanager.views

import android.content.IntentSender
import android.databinding.BaseObservable
import android.databinding.DataBindingUtil
import android.databinding.Observable
import android.databinding.ObservableField
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gottlicher.billmanager.R
import com.gottlicher.billmanager.databinding.ActivityAddBillBinding
import com.gottlicher.billmanager.databinding.ActivityAppPickerBinding
import com.gottlicher.billmanager.databinding.ItemAppListBinding
import com.gottlicher.billmanager.utils.PkgInfo
import com.gottlicher.billmanager.utils.getInstalledPackages
import kotlinx.android.extensions.LayoutContainer

import kotlinx.android.synthetic.main.activity_app_picker.*
import kotlinx.coroutines.experimental.DefaultDispatcher
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext

class AppPickerActivity : AppCompatActivity() {

    val model = AppPickerDataModel(ObservableField(""))
    var isLoading:Boolean
        get() = spinner.visibility == View.VISIBLE
        set(value){
            spinner.visibility = if (value) View.VISIBLE else View.GONE
            listApps.visibility = if(value) View.GONE else View.VISIBLE
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityAppPickerBinding>(this, R.layout.activity_app_picker)

        binding.model = model
        binding.executePendingBindings()

        setSupportActionBar(toolbar)
        loadPackages()
        model.searchText.addOnPropertyChangedCallback (object: Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                onSearchTextChanged(sender, propertyId)
            }
        })
    }

    private fun onSearchTextChanged(sender: Observable?, propertyId:Int) = launch (UI){
        //TODO
    }

    private fun loadPackages () = launch(UI) {
        isLoading = true
        try {
            val packages = withContext(DefaultDispatcher) {
                getInstalledPackages(this@AppPickerActivity)
            }
            listApps.layoutManager = LinearLayoutManager (this@AppPickerActivity)
            listApps.adapter = AppListAdapter(packages)
            (listApps.adapter as AppListAdapter).notifyDataSetChanged()
        } finally {
            isLoading = false
        }
    }
}

class AppPickerDataModel (var searchText: ObservableField<String>)

class AppListAdapter(val items: List<PkgInfo>) : RecyclerView.Adapter<AppListAdapter.AppListViewHolder> () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):AppListViewHolder {
        val li = LayoutInflater.from(parent.context);
        val itemBinding = ItemAppListBinding.inflate(li, parent, false)
        return AppListViewHolder (itemBinding)
    }

    override fun onBindViewHolder(holder: AppListViewHolder, position: Int) = holder.bind(items[position])
    override fun getItemCount(): Int = items.size

    class AppListViewHolder(val binding: ItemAppListBinding) : RecyclerView.ViewHolder(binding.root), LayoutContainer {
        override val containerView: View?
            get() = binding.root

        fun bind(pkgInfo: PkgInfo) {
            binding.pkgInfo = pkgInfo
            binding.executePendingBindings()
        }
    }
}
