package io.github.malvadeza.sandbox.visibility

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import io.github.malvadeza.sandbox.R
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

class VisibilityActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.visibility_activity)

        setSupportActionBar(findViewById(R.id.toolbar) as Toolbar)

        val rootView = findViewById(R.id.root_layout)

        val adapter = UiFlagsAdapter(listOf(
                UiFlag("SYSTEM_UI_FLAG_LAYOUT_STABLE", View.SYSTEM_UI_FLAG_LAYOUT_STABLE),
                UiFlag("SYSTEM_UI_FLAG_FULLSCREEN", View.SYSTEM_UI_FLAG_FULLSCREEN),
                UiFlag("SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN", View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN),
                UiFlag("SYSTEM_UI_FLAG_HIDE_NAVIGATION", View.SYSTEM_UI_FLAG_HIDE_NAVIGATION),
                UiFlag("SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION", View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION),
                UiFlag("SYSTEM_UI_FLAG_LOW_PROFILE", View.SYSTEM_UI_FLAG_LOW_PROFILE)
        ).sortedBy { it.flag })
        adapter.events.subscribe { (description, flag, active) ->
            val flags = rootView.systemUiVisibility

            rootView.systemUiVisibility = if (active) flags or flag else flags xor flag

            Toast.makeText(this, "Seting $description to ${if (active) "Active" else "Inactive"}", Toast.LENGTH_SHORT).show()
        }

        val uiFlagsList = findViewById(R.id.rv_ui_flags) as RecyclerView
        uiFlagsList.addItemDecoration(SpaceItemDecoration())
        uiFlagsList.adapter = adapter
        uiFlagsList.layoutManager = LinearLayoutManager(applicationContext)

    }

    data class UiFlag(val description: String, val flag: Int, var active: Boolean = false)

    class UiFlagsAdapter(val items: List<UiFlag>) : RecyclerView.Adapter<UiFlagsAdapter.UiFlagViewHolder>() {

        private val publisher = PublishSubject.create<UiFlag>()

        val events: Observable<UiFlag>
            get() = publisher

        override fun onBindViewHolder(holder: UiFlagViewHolder, position: Int) {
            holder.bind(items[position])
        }

        override fun getItemCount() = items.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UiFlagViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.visibility_flag_item, parent, false)

            return UiFlagViewHolder(view, publisher)
        }

        class UiFlagViewHolder(
                view: View,
                val publisher: Subject<UiFlag>
        ) : RecyclerView.ViewHolder(view) {
            val textView = view.findViewById(R.id.tv_ui_flag) as TextView
            val switchFlag = view.findViewById(R.id.sw_flag_status) as Switch

            fun bind(flag: UiFlag) {
                textView.text = flag.description
                switchFlag.isChecked = flag.active

                switchFlag.setOnCheckedChangeListener { view, isChecked ->
                    view.isChecked = isChecked
                    flag.active = isChecked


                    publisher.onNext(flag)
                }
            }
        }
    }

}