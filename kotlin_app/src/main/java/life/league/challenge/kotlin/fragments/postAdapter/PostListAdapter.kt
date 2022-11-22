package life.league.challenge.kotlin.fragments.postAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import life.league.challenge.kotlin.base.BaseRecyclerViewAdapter
import life.league.challenge.kotlin.databinding.ItemUsersBinding
import life.league.challenge.kotlin.model.Users

class PostListAdapter(users: List<Users>) :
    BaseRecyclerViewAdapter<Users, PostListAdapter.MovieItemViewHolder>(users.toMutableList()) {


    class MovieItemViewHolder(val binding: ItemUsersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(movie: Users) {
            binding.items=movie
            /* binding.textView.text = movie.name
             Glide.with(binding.imageView.context)
                 .load(Uri.parse("file:///android_asset/${movie.posterImage}"))
                 .placeholder(R.drawable.placeholder_for_missing_posters)
                 .into(binding.imageView);*/
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemViewHolder {
        val binding: ItemUsersBinding = ItemUsersBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieItemViewHolder(binding);
    }


    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        holder.bindData(mList[position])
    }

    override fun setData(list: List<Users>) {
        mList.clear()
        mList.addAll(list)
    }
}