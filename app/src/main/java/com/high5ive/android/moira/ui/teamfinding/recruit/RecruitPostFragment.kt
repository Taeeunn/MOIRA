package com.high5ive.android.moira.ui.teamfinding.recruit

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.chip.ChipGroup
import com.high5ive.android.moira.R
import kotlinx.android.synthetic.main.recruit_post_fragment.*


//https://mobikul.com/android-chips-dynamicaly-add-remove-tags-chips-view/
class RecruitPostFragment : Fragment() {

    companion object {
        fun newInstance() = RecruitPostFragment()
    }

    private lateinit var viewModel: RecruitPostViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.recruit_post_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RecruitPostViewModel::class.java)
        // TODO: Use the ViewModel

        var tagList = mutableListOf<String>()
        tagList.add("태그명1")
        tagList.add("태그명2")
        tagList.add("태그명3")
        setTag(tagList);
    }


    private fun setTag(tagList: MutableList<String>) {
        for (index in tagList.indices) {
            val tagName = tagList[index]
            //val chip = Chip(ContextThemeWrapper(context, R.style.MaterialChipsAction))
            val chip = Chip(context)
            val drawable = context?.let { ChipDrawable.createFromAttributes(it, null, 0, R.style.MaterialChipsAction) }
            if (drawable != null) {
                chip.setChipDrawable(drawable)
            }

            chip.text = tagName
            chip.setCloseIconResource(R.drawable.ic_baseline_close_24)
            chip.isCloseIconEnabled = true
            //Added click listener on close icon to remove tag from ChipGroup
            chip.setOnCloseIconClickListener {
                tagList.remove(tagName)
                tag_group.removeView(chip)
            }
            tag_group.addView(chip)
        }
    }

}