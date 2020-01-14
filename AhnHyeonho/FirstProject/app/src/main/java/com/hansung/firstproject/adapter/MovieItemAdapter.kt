package com.hansung.firstproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hansung.firstproject.data.MovieModel
import com.hansung.firstproject.databinding.MovieItemBinding

class MovieItemAdapter : BaseAdapter<MovieModel, MovieItemBinding>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<MovieModel> =
        MovieHolder(MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    // //영화 아이템의 갯수 가져오기
    // override fun getItemCount() = items.size

    // // ViewHolder를 생성하고 View를 붙여주는 method
    // override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder =
    //     MovieHolder(T,MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    //     //재활용 되는 View가 호출하여 실행되는 메소드, 뷰 홀더를 전달하고 Adapter는 position 의 데이터를 결합
    //     @RequiresApi(Build.VERSION_CODES.N)
    //     override fun onBindViewHolder(holder: MovieHolder, position: Int) {
    //         holder.bindItems(items[position])
    //     }
}