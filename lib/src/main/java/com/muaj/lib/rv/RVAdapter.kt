package com.muaj.lib.rv

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import java.util.ArrayList

/**
 * Created by muaj on 2018/6/6
 *
 */

open class RVAdapter : RecyclerView.Adapter<RVAdapter.RVHolder>() {

    private val itemList = ArrayList<RVItem>()

    /**=====================================================================================
     * default items operation
     * you also can inherit RVAdapter to implement more methods to operate items
     * ================================================================================== */

    var items: List<RVItem>
        get() = itemList
        set(items) {
            clearItems()
            addItems(items)
        }

    interface RVItem {
        /**
         * get the xml layout this type item used in
         * @return
         */
        @get:LayoutRes
        val layout: Int

        /**
         * get the variable name in the xml
         * @return
         */
        val variableId: Int
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVHolder {
        return RVHolder.create(parent, viewType)
    }

    override fun onBindViewHolder(holder: RVHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun getItemViewType(position: Int): Int {
        return itemList[position].layout
    }

    fun findPos(item: RVItem): Int {
        return itemList.indexOf(item)
    }

    fun setItem(item: RVItem) {
        clearItems()
        addItem(item)
    }

    fun addItem(item: RVItem) {
        itemList.add(item)
    }

    fun addItem(item: RVItem, index: Int) {
        itemList.add(index, item)
    }

    fun addItems(items: List<RVItem>) {
        this.itemList.addAll(items)
    }

    fun removeItem(item: RVItem): Int {
        val pos = findPos(item)
        itemList.remove(item)
        return pos
    }

    fun clearItems() {
        itemList.clear()
    }

    /**==================================== RVAdapter ViewHolder ================================================== */
    class RVHolder(private val mBinding: ViewDataBinding) : RecyclerView.ViewHolder(mBinding.root) {

        fun bind(item: RVItem) {
            mBinding.setVariable(item.variableId, item)
            mBinding.executePendingBindings()
        }

        companion object {

            internal fun create(parent: ViewGroup, viewType: Int): RVHolder {
                val binding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context),
                        viewType, parent, false)
                return RVHolder(binding)
            }
        }
    }


}
