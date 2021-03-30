package com.high5ive.android.moira.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.high5ive.android.moira.R
import com.high5ive.android.moira.data.retrofit.CareerItem
import com.high5ive.android.moira.data.retrofit.CertificateItem
import com.high5ive.android.moira.data.retrofit.UserLicenseResponseDto
import com.high5ive.android.moira.databinding.CareerItemBinding
import com.high5ive.android.moira.databinding.CertificateItemBinding

/**
 * @author Taeeun Kim
 * @email xodms8713@gmail.com
 * @created 2021-03-31
 */
class CertificateAdapter(val items: List<UserLicenseResponseDto>) :
    RecyclerView.Adapter<CertificateAdapter.CertificateViewHolder>(){
    class CertificateViewHolder(val binding: CertificateItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CertificateViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.certificate_item, parent, false)
        val viewHolder =
            CertificateViewHolder(
                CertificateItemBinding.bind(view)
            )

        return viewHolder
    }

    override fun getItemCount() = items.size
    override fun onBindViewHolder(holder: CertificateViewHolder, position: Int) {
        holder.binding.certificate = items[position]
    }

}