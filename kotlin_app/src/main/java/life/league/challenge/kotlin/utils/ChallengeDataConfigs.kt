package life.league.challenge.kotlin.utils

import android.util.Log
import androidx.databinding.BindingAdapter
import com.facebook.drawee.view.SimpleDraweeView

class ChallengeDataConfigs {
    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun imageLoading(view: SimpleDraweeView, url: String?) {
            url?.let { _it ->
                view.setImageURI(_it!!)
                Log.e("TAG", "image: ${_it!!}")
            } ?: kotlin.run {

            }
        }
    }
}