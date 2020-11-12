package com.hzw.supertextview.demo

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 *author:HZWei
 *date:  2020/11/12
 *desc:
 */
class ListAdapter(data: MutableList<String>) : BaseQuickAdapter<String,BaseViewHolder>(R.layout.list_item,data = data){

    override fun convert(holder: BaseViewHolder, item: String) {
       holder.setText(R.id.text,"position: ${holder.layoutPosition}")
    }
}