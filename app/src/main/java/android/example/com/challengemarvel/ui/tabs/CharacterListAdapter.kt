package android.example.com.challengemarvel.ui.tabs

import android.example.com.challengemarvel.databinding.ItemCharacterBinding
import android.example.com.challengemarvel.model.character.Character
import android.example.com.challengemarvel.utils.getProgressDrawable
import android.example.com.challengemarvel.utils.loadImage
import android.example.com.challengemarvel.utils.toHTTPS
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class CharacterListAdapter :
    PagingDataAdapter<Character, CharacterListAdapter.CharacterViewHolder>(
        CharacterComparator
    ) {
    class CharacterViewHolder(val view: ItemCharacterBinding) :
        RecyclerView.ViewHolder(view.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ItemCharacterBinding.inflate(inflater, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.view.characterData = getItem(position)
        holder.view.characterImage.loadImage(
            ("${getItem(position)?.thumbnail?.path?.toHTTPS()}/standard_medium.${getItem(position)?.thumbnail?.extension}"),
            getProgressDrawable(holder.view.root.context)
        )
        holder.itemView.setOnClickListener {
            getItem(position)?.let {
                Navigation.findNavController(holder.view.root)
                    .navigate(HomeFragmentDirections.homeToDetail(getItem(position)!!.id!!))
            }
        }
    }

    object CharacterComparator : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }
    }
}