package io.github.malvadeza.sandbox

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.github.malvadeza.sandbox.customviews.CustomViewActivity
import io.github.malvadeza.sandbox.fragmenttransition.FragmentTransitionActivity

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val activities = listOf(
                Sandbox("Tally Counter View", -1, CustomViewActivity::class.java),
                Sandbox("Fragment Transition", -1, FragmentTransitionActivity::class.java)
        )
        val sandboxesList = findViewById(R.id.rv_sandboxes) as RecyclerView
        val sandboxAdapter = SandboxAdapter(activities) { sandbox, sharedView ->
            val intent = Intent(this, sandbox.activity)
            ViewCompat.setTransitionName(sharedView, "title")
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, sharedView, "title")
            startActivity(intent, options.toBundle())
        }

        sandboxesList.adapter = sandboxAdapter
        sandboxesList.setHasFixedSize(true)
    }

    data class Sandbox(val name: String, val drawable: Int, val activity: Class<*>)

    class SandboxAdapter(
            val sandboxes: List<Sandbox>,
            val clickListener: (Sandbox, View) -> Unit
    ) : RecyclerView.Adapter<SandboxAdapter.SandboxViewholder>() {

        override fun getItemCount() = sandboxes.size

        override fun onBindViewHolder(holder: SandboxViewholder, position: Int) {
            holder.bind(sandboxes[position])
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SandboxViewholder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.sandbox_list_item, parent, false)

            return SandboxViewholder(view, clickListener)
        }

        class SandboxViewholder(
                view: View,
                val clickListener: (Sandbox, View) -> Unit
        ) : RecyclerView.ViewHolder(view) {
            val sandboxTitle = view.findViewById(R.id.tv_sandbox_title) as TextView

            fun bind(sandbox: Sandbox) {
                sandboxTitle.text = sandbox.name
                itemView.setOnClickListener {
                    clickListener(sandbox, sandboxTitle)
                }
            }
        }

    }

}