package android.example.com.challengemarvel.ui

import android.example.com.challengemarvel.databinding.ItemChildComicBinding
import android.example.com.challengemarvel.databinding.ItemComicBinding
import android.example.com.challengemarvel.model.expandables.ComicSummary
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ComicDetailAdapter(private val comicList: MutableList<ComicSummary>) :
    RecyclerView.Adapter<ComicDetailAdapter.ComicDetailViewHolder>() {

    class ComicDetailViewHolder(val view: ItemChildComicBinding) : RecyclerView.ViewHolder(view.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicDetailViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ItemChildComicBinding.inflate(inflater, parent, false)
        return ComicDetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: ComicDetailViewHolder, position: Int) {
        holder.view.childComicName.text = comicList[position].name
    }

    override fun getItemCount(): Int = comicList.size
    fun refreshList(newList: List<ComicSummary>) {
        comicList.clear()
        comicList.addAll(newList)
        notifyDataSetChanged()
    }

}