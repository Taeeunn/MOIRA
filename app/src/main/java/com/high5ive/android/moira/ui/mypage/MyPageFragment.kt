package com.high5ive.android.moira.ui.mypage

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.high5ive.android.moira.R
import com.high5ive.android.moira.ui.mypage.apply.ApplyListActivity
import com.high5ive.android.moira.ui.mypage.post.PostListActivity
import com.high5ive.android.moira.ui.mypage.scrap.ScrapListActivity
import kotlinx.android.synthetic.main.my_page_fragment.*

class MyPageFragment : Fragment() {

    companion object {
        fun newInstance() = MyPageFragment()
    }

    private lateinit var viewModel: MyPageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.my_page_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MyPageViewModel::class.java)
        // TODO: Use the ViewModel

        post_container.setOnClickListener {
            startActivity(Intent(context, PostListActivity::class.java))
        }

        apply_container.setOnClickListener {
            startActivity(Intent(context, ApplyListActivity::class.java))
        }

        scrap_container.setOnClickListener {
            startActivity(Intent(context, ScrapListActivity::class.java))
        }

        edit_info_btn.setOnClickListener {
            startActivity(Intent(context, EditProfileActivity::class.java))
        }
    }

}