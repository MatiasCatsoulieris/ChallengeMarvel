package android.example.com.challengemarvel.ui.tabs

import android.example.com.challengemarvel.databinding.ItemChildComicBinding
import android.example.com.challengemarvel.model.expandables.ComicSummary
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

//Adapter for RecyclerView inside Event item

class ChildComicAdapter(private val comicList : List<ComicSummary>) : RecyclerView.Adapter <ChildComicAdapter.ChildComicViewHolder>(){
    class ChildComicViewHolder (val view: ItemChildComicBinding): RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildComicViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ItemChildComicBinding.inflate(inflater, parent, false)
        return ChildComicViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChildComicViewHolder, position: Int) {
        holder.view.childComicName.text = comicList[position].name

    }

    override fun getItemCount(): Int = comicList.size
}