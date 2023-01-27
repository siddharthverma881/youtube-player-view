package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.myapplication.Utils.YOUTUBE_API_KEY
import com.example.myapplication.databinding.ActivityVideoBinding
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer

class VideoActivity : YouTubeBaseActivity() {

    private lateinit var mBinding : ActivityVideoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_video)

    }

    override fun onResume() {
        super.onResume()
        val position : Int = intent.getIntExtra("position",0)
        val videoLink = Utils.getVideosList(ArrayList())[position].videoId
        mBinding.tvDescription.text = Utils.getVideosList(ArrayList())[position].description
        mBinding.ytPlayer.initialize(YOUTUBE_API_KEY, object : YouTubePlayer.OnInitializedListener{
            override fun onInitializationSuccess(
                provider: YouTubePlayer.Provider?,
                player: YouTubePlayer?,
                p2: Boolean
            ) {
                player?.loadVideo(videoLink)
            }

            override fun onInitializationFailure(
                provider: YouTubePlayer.Provider?,
                result: YouTubeInitializationResult?
            ) {
                Log.e("TAG","result is ${result!!}")
            }

        })
    }
}