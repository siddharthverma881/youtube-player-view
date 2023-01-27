package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentVideoListBinding

class VideoListFragment : Fragment() {

    private lateinit var mBinding : FragmentVideoListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.e("TAG","onCreateView2")
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_video_list, container, false)
        val videoList = Utils.getVideosList(ArrayList())
        mBinding.rvVideos.layoutManager = LinearLayoutManager(requireActivity())
//        mBinding.rvVideos.adapter = VideoAdapter(videoList)

        return mBinding.root
    }

}