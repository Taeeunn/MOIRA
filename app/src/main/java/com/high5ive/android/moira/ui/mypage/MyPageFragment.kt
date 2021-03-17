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
import com.high5ive.android.moira.ui.mypage.cs.AskActivity
import com.high5ive.android.moira.ui.mypage.edit.EditProfileActivity
import com.high5ive.android.moira.ui.mypage.post.PostListActivity
import com.high5ive.android.moira.ui.mypage.scrap.ScrapListActivity
import kotlinx.android.synthetic.main.my_page_fragment.*

class MyPageFragment : Fragment(), View.OnClickListener{

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

        post_container.setOnClickListener(this)

        apply_container.setOnClickListener(this)

        scrap_container.setOnClickListener(this)

        edit_info_btn.setOnClickListener(this)

        account_setting.setOnClickListener(this)

        noti_setting.setOnClickListener(this)

        question.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.post_container -> startActivity(Intent(context, PostListActivity::class.java))
            R.id.apply_container -> startActivity(Intent(context, ApplyListActivity::class.java))
            R.id.scrap_container -> startActivity(Intent(context, ScrapListActivity::class.java))
            R.id.edit_info_btn -> startActivity(Intent(context, EditProfileActivity::class.java))
            R.id.account_setting -> startActivity(Intent(context, EditProfileActivity::class.java))
            R.id.noti_setting -> startActivity(Intent(context, EditProfileActivity::class.java))
            R.id.question -> startActivity(Intent(context, AskActivity::class.java))
        }
    }

}