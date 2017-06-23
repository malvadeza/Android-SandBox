package io.github.malvadeza.sandbox

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.SharedElementCallback
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.github.malvadeza.sandbox.cardview.recyclerview.RecyclerActivity
import io.github.malvadeza.sandbox.coordinator.CoordinatorActivity
import io.github.malvadeza.sandbox.customviews.TallyCounterActivity
import io.github.malvadeza.sandbox.customviews.TouchViewActivity
import io.github.malvadeza.sandbox.fragmenttransition.FragmentTransitionActivity
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        Timber.d("Application ${SandBoxApplication.instance}");

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val activities = listOf(
                Sandbox("Tally Counter View", -1, TallyCounterActivity::class.java),
                Sandbox("Fragment Transition", -1, FragmentTransitionActivity::class.java),
                Sandbox("Touch View", -1, TouchViewActivity::class.java),
                Sandbox("Coordinator Activity", -1, CoordinatorActivity::class.java),
                Sandbox("Recycler Card Activity", -1, RecyclerActivity::class.java)

        )
        val sandboxesList = findViewById(R.id.rv_sandboxes) as RecyclerView
        val sandboxAdapter = SandboxAdapter(activities) { sandbox, sharedView, position ->
            val intent = Intent(this, sandbox.activity)
            intent.putExtra("POSITION_ON_RV", position)

            ViewCompat.setTransitionName(sharedView, "title")

            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, sharedView, "title")
            startActivity(intent, options.toBundle())
        }

        setExitSharedElementCallback(object : SharedElementCallback() {
            override fun onMapSharedElements(names: MutableList<String>, sharedElements: MutableMap<String, View>) {
                super.onMapSharedElements(names, sharedElements)

                if (names.size != sharedElements.size) {

                }

                Log.d("MainActivity", "setExitSharedElementCallback##onMapSharedElements")
            }
        })

        sandboxesList.adapter = sandboxAdapter
        sandboxesList.setHasFixedSize(true)
    }

    override fun onActivityReenter(resultCode: Int, data: Intent?) {
        super.onActivityReenter(resultCode, data)

        if (data == null || resultCode != Activity.RESULT_OK) return

        val position = data.getIntExtra("POSITION_ON_RV", -1)
        if (position != -1) {
            /*
             * Look for it in adapter
             * Then postponeEnterTransition()
             * Then addOnLayoutChangeLister on RecyclerView and
             * startPostponedEnterRansition() #onLayoutChange
             *
             * Scroll RecyclerView to Position
             * Then go to SharedElementCallback#onMapSharedElements
             */
        }

    }

    data class Sandbox(val name: String, val drawable: Int, val activity: Class<*>)

    class SandboxAdapter(
            val sandboxes: List<Sandbox>,
            val clickListener: (Sandbox, View, Int) -> Unit
    ) : RecyclerView.Adapter<SandboxAdapter.SandboxViewholder>() {

        override fun getItemCount() = sandboxes.size

        override fun onBindViewHolder(holder: SandboxViewholder, position: Int) {
            holder.bind(sandboxes[position], position)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SandboxViewholder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.sandbox_list_item, parent, false)

            return SandboxViewholder(view, clickListener)
        }

        class SandboxViewholder(
                view: View,
                val clickListener: (Sandbox, View, Int) -> Unit
        ) : RecyclerView.ViewHolder(view) {
            val sandboxTitle = view.findViewById(R.id.tv_sandbox_title) as TextView

            fun bind(sandbox: Sandbox, position: Int) {
                sandboxTitle.text = sandbox.name
                itemView.setOnClickListener {
                    clickListener(sandbox, sandboxTitle, position)
                }
            }
        }

    }

}