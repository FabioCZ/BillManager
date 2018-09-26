package com.gottlicher.billmanager.views

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.databinding.BaseObservable
import android.databinding.DataBindingUtil
import android.databinding.Observable
import android.databinding.ObservableField
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.gottlicher.billmanager.BR
import com.gottlicher.billmanager.R
import com.gottlicher.billmanager.databinding.ActivityAppPickerBinding
import com.gottlicher.billmanager.databinding.ItemAppListBinding
import com.gottlicher.billmanager.utils.PkgInfo
import com.gottlicher.billmanager.utils.getInstalledApps
import kotlinx.android.extensions.LayoutContainer

import kotlinx.android.synthetic.main.activity_app_picker.*
import kotlinx.android.synthetic.main.item_app_list.*
import kotlinx.coroutines.experimental.DefaultDispatcher
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import org.jetbrains.anko.sdk25.coroutines.onClick

class AppPickerActivity : AppCompatActivity() {

    val model = AppPickerDataModel(ObservableField(""))
    private lateinit var pkgList:List<PkgInfo>
    private val appListAdapter = AppListAdapter(this, ::onItemSelected)

    private var isLoading:Boolean
        get() = spinner.visibility == View.VISIBLE
        set(value){
            spinner.visibility = if(value) View.VISIBLE else View.GONE
            listApps.visibility = if(value) View.GONE else View.VISIBLE
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityAppPickerBinding>(this, R.layout.activity_app_picker)

        binding.model = model
        binding.executePendingBindings()

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        model.searchText.addOnPropertyChangedCallback (object: Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                onSearchTextChanged(sender, propertyId)
            }
        })

        val layoutManager = LinearLayoutManager (this@AppPickerActivity)
        listApps.layoutManager = layoutManager
        listApps.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))
        listApps.adapter = appListAdapter


        launch(UI) {
            loadAppInfos()
            loadPackages("")
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
            return true
        } else {
            return super.onOptionsItemSelected(item)
        }

    }

    private fun onSearchTextChanged(sender: Observable?, propertyId:Int) = launch (UI){
        loadPackages(model.searchText.get() ?: "")
    }

    private suspend fun loadAppInfos() {
        isLoading = true
        try {
            pkgList = withContext(DefaultDispatcher) {
                getInstalledApps(this@AppPickerActivity)
            }
        } finally {
            isLoading = false
        }
    }
    private fun loadPackages(filter:String) = launch(UI) {
        isLoading = true
        try {
            val filtered = if(filter.isBlank()) pkgList else pkgList.filter { p -> p.appName.toLowerCase().contains(filter) }
            appListAdapter.items = filtered
        } finally {
            isLoading = false
        }
    }

    private fun onItemSelected (pkgInfo: PkgInfo) {
        val intent = Intent()
        intent.putExtra(EXTRA_APP_PACKAGE, pkgInfo.packageName)
        this.setResult(Activity.RESULT_OK, intent)
        finish()
    }
}

class AppPickerDataModel (var searchText: ObservableField<String>)

class AppListAdapter (private val context: Context, val callback: (PkgInfo) -> Unit) : RecyclerView.Adapter<AppListAdapter.AppListViewHolder> () {

    private var _items:List<PkgInfo> = ArrayList()
    var items:List<PkgInfo>
        get() = _items
        set(value) {
            _items = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):AppListViewHolder {
        val li = LayoutInflater.from(parent.context);
        val itemBinding = ItemAppListBinding.inflate(li, parent, false)
        return AppListViewHolder (context, itemBinding, callback)
    }

    override fun onBindViewHolder(holder: AppListViewHolder, position: Int) = holder.bind(items[position])
    override fun getItemCount(): Int = items.size

    class AppListViewHolder(private val context: Context,
                            private val binding: ItemAppListBinding,
                            val callback: (PkgInfo) -> Unit) : RecyclerView.ViewHolder(binding.root), LayoutContainer {
        override val containerView: View?
            get() = binding.root

        fun bind(pkgInfo: PkgInfo) {
            appItemContainer.onClick {
                callback (pkgInfo)
            }

            launch (UI) {

                if (!pkgInfo.iconInitialized) {
                    val icon = withContext(DefaultDispatcher) {
                        this@AppListViewHolder.context.packageManager.getApplicationIcon(pkgInfo.packageName)
                    }

                    if (icon != null) {
                        pkgInfo.icon = icon
                    }
                }
                binding.pkgInfo = pkgInfo
                binding.executePendingBindings()
            }
        }
    }
}
