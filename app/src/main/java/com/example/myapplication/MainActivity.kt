package com.example.myapplication

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.Utils.YOUTUBE_API_KEY
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView

class MainActivity : YouTubeBaseActivity(), VideoAdapter.YoutubeVideo {
    private lateinit var mBinding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

//        supportFragmentManager.commit {
//            add<VideoListFragment>(R.id.fragment_container_view)
//        }

        val videoList = Utils.getVideosList(ArrayList())
        val layoutManager = LinearLayoutManager(this)
//        var oldPlayer : YouTubePlayer?= null
        mBinding.rvVideos.layoutManager = layoutManager
        mBinding.rvVideos.adapter = VideoAdapter(videoList, this, this)

        var oldPosition = -1

        mBinding.rvVideos.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visiblePosition: Int = layoutManager.findFirstCompletelyVisibleItemPosition()
                Log.e("Item","old position is $oldPosition -------------")
                if(oldPosition != visiblePosition){
                    if(oldPosition != -1){
                        val v: View? = layoutManager.findViewByPosition(oldPosition)
                        val ivThumb : ImageView? = v?.findViewById(R.id.iv_thumbnail)
                        val clPlayer : ConstraintLayout? = v?.findViewById(R.id.cl_video)
                        ivThumb?.let{
                            it.visibility = View.VISIBLE
                            Glide.with(this@MainActivity).load(Utils.getVideosList(ArrayList())[oldPosition].thumbnail).into(it)
                        }
                        clPlayer?.visibility = View.INVISIBLE
                    }
                    oldPosition = visiblePosition
                }
                Log.e("Item","position visible is $visiblePosition -------------")
                if (visiblePosition > -1) {
                    val v: View? = layoutManager.findViewByPosition(visiblePosition)
//                    Handler().postDelayed({
                        val youtube  : YouTubePlayerView? = v?.findViewById(R.id.yt_player)
                        val ivThumb : ImageView? = v?.findViewById(R.id.iv_thumbnail)
                        val clPlayer : ConstraintLayout? = v?.findViewById(R.id.cl_video)
//                        youtube?.id = Utils.getVideosList(ArrayList())[visiblePosition].id
//                        if(oldPlayer != null){
//                            oldPlayer?.release()
//                            oldPlayer = null
//                        }
                        youtube?.initialize(YOUTUBE_API_KEY, object : YouTubePlayer.OnInitializedListener{
                            override fun onInitializationSuccess(
                                provider: YouTubePlayer.Provider?,
                                player: YouTubePlayer?,
                                p2: Boolean
                            ) {
//                                oldPlayer = player
                                ivThumb!!.visibility = View.INVISIBLE
                                clPlayer!!.visibility = View.VISIBLE
                                player?.loadVideo(Utils.getVideosList(ArrayList())[visiblePosition].videoId)
                                player?.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS)
                            }

                            override fun onInitializationFailure(
                                p0: YouTubePlayer.Provider?,
                                p1: YouTubeInitializationResult?
                            ) { }

                        })
                    clPlayer?.setOnClickListener { onVideoSelected(visiblePosition) }
//                    },1000)
                }
            }
        })
    }

    override fun onVideoSelected(position : Int) {
        val intent = Intent(this, VideoActivity::class.java)
        intent.putExtra("position",position)
        startActivity(intent)
    }
}