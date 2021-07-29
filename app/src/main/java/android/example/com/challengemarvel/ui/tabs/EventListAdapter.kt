package android.example.com.challengemarvel.ui.tabs


import android.example.com.challengemarvel.R
import android.example.com.challengemarvel.databinding.ItemEventBinding
import android.example.com.challengemarvel.model.events.ComicEvent
import android.example.com.challengemarvel.utils.*
import android.example.com.challengemarvel.utils.show
import android.graphics.drawable.ColorDrawable
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.RecyclerView

class EventListAdapter(private val comicList: MutableList<ComicEvent>) :
    RecyclerView.Adapter<EventListAdapter.EventListViewHolder>() {

    private var expandedItemPosition: Int = -1
    private var expandedViewHolder: EventListViewHolder? = null

    class EventListViewHolder(val view: ItemEventBinding) : RecyclerView.ViewHolder(view.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ItemEventBinding.inflate(inflater, parent, false)
        return EventListViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventListViewHolder, position: Int) {
        holder.view.eventData = comicList[position]
        val eventDate = comicList[position].startDate?.time
        val date = DateUtils.formatDateTime(holder.itemView.context, eventDate!!, DateUtils.FORMAT_SHOW_DATE)
        holder.view.tvEventDate.text = date
        holder.view.characterImage.loadImage(
            "${comicList[position].thumbnail?.path?.toHTTPS()}/standard_medium." +
                    "${comicList[position].thumbnail?.extension}",
            getProgressDrawable(holder.view.root.context)

        )
        setUpComicsAdapter(holder, position)
        holder.itemView.setOnClickListener {
            onEventClicked(holder, position)
        }
    }


    private fun onEventClicked(holder: EventListViewHolder, position: Int) {
        when (comicList[position].isExpanded) {
            true -> {
                holder.view.expandableLayout.hide()
                holder.view.iconArrowUp.animate().rotation(0f).start()
                expandedItemPosition = -1
                comicList[position].isExpanded = false

            }
            false -> {
                if (expandedItemPosition == -1) {
                    expandedItemPosition = position
                    expandedViewHolder = holder
                    holder.view.expandableLayout.show()
                    holder.view.iconArrowUp.animate().rotation(180f).start()
                    comicList[position].isExpanded = true

                } else {
                    expandedViewHolder?.view?.expandableLayout?.hide()
                    expandedViewHolder?.view?.iconArrowUp?.animate()?.rotation(0f)?.start()
                    comicList[expandedItemPosition].isExpanded = false
                    expandedItemPosition = position
                    expandedViewHolder = holder
                    holder.view.expandableLayout.show()
                    holder.view.iconArrowUp.animate().rotation(180f).start()
                    comicList[position].isExpanded = true

                }
            }
        }
    }

    private fun setUpComicsAdapter(holder: EventListViewHolder, position: Int) {
        if(comicList[position].comics.items.isEmpty()) {
            holder.view.tvNoInfo.show()
            holder.view.recyclerViewEventComics.hide()
        } else {
            val childAdapter = ChildComicAdapter(comicList[position].comics.items)
            holder.view.recyclerViewEventComics.adapter = childAdapter
            val itemDecoration = DividerItemDecoration(
                holder.itemView.context,
                LinearLayoutManager.VERTICAL
            )
            itemDecoration.setDrawable(ColorDrawable(holder.itemView.context.resources.getColor(R.color.black)))
            holder.view.recyclerViewEventComics.addItemDecoration(itemDecoration)
            holder.view.tvNoInfo.hide()
            holder.view.recyclerViewEventComics.show()
        }
    }

    override fun getItemCount(): Int = comicList.size


    fun refreshList(newList: List<ComicEvent>) {
        comicList.clear()
        comicList.addAll(newList)
        notifyDataSetChanged()
    }

}