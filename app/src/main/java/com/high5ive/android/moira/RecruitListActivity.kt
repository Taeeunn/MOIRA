package com.high5ive.android.moira

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.high5ive.android.moira.databinding.RecruitItemBinding
import kotlinx.android.synthetic.main.activity_recruit_list.*

class RecruitListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recruit_list)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)

        val recruit = arrayListOf<Recruit>()
        for (i in 0..30){
            recruit.add(Recruit("모집글 $i", "닉네임 $i"))
        }

        recycler_view.apply{
            layoutManager = LinearLayoutManager(this@RecruitListActivity)
            adapter = RecruitAdapter(recruit) { person ->
                Toast.makeText(this@RecruitListActivity, "$person", Toast.LENGTH_SHORT).show()
                Log.v("tt", "click");
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}


class RecruitAdapter(val items: List<Recruit>,
                    private val clickListener: (recruit: Recruit) -> Unit) :
    RecyclerView.Adapter<RecruitAdapter.RecruitViewHolder>(){
    class RecruitViewHolder(val binding: RecruitItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecruitViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recruit_item, parent, false)
        val viewHolder = RecruitViewHolder(RecruitItemBinding.bind(view))

        view.setOnClickListener {
            clickListener.invoke(items[viewHolder.adapterPosition])

        }

        return viewHolder
    }

    override fun getItemCount() = items.size
    override fun onBindViewHolder(holder: RecruitViewHolder, position: Int) {
        holder.binding.recruit = items[position]
    }

}