package com.example.gamereminder.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.gamereminder.R
import com.example.gamereminder.repositories.LocalRepository
import com.example.gamereminder.utils.navigationInAndOut
import com.example.gamereminder.viewModels.TeamDetailViewModel

class TeamDetailFragment: BaseFragment() {

    private lateinit var teamName: String

    private val viewModel by lazy {
        TeamDetailViewModel(requireArguments(), LocalRepository(requireContext()))
    }

    override fun getContainerId() = R.layout.fragment_team_detail

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.teamName.observe(viewLifecycleOwner, Observer {
            view.findViewById<TextView>(R.id.name_team).text = it
            teamName = it
        })
        viewModel.stadiumImage.observe(viewLifecycleOwner, Observer {
            val radius = resources.getDimensionPixelSize(R.dimen.corner_radius)
            Glide.with(requireContext()).load(it).transform(CenterCrop(), RoundedCorners(radius))
                .placeholder(android.R.drawable.stat_sys_download).into(view.findViewById(R.id.image_stadium))
        })
        viewModel.badgeImage.observe(viewLifecycleOwner, Observer {
            Glide.with(requireContext()).load(it).centerCrop().placeholder(android.R.drawable.stat_sys_download).into(view.findViewById(R.id.badge_image))
        })
        viewModel.leagueName.observe(viewLifecycleOwner, Observer {
            view.findViewById<TextView>(R.id.league_team).text = it
        })
        viewModel.stadiumName.observe(viewLifecycleOwner, Observer {
            view.findViewById<TextView>(R.id.stadium_name).text = it
        })
        viewModel.teamDesc.observe(viewLifecycleOwner, Observer {
            view.findViewById<TextView>(R.id.team_desc).text = it
        })

        viewModel.showProgressBar.observe(viewLifecycleOwner, Observer {
            showHideProgressBar(it)
        })

        view.findViewById<Button>(R.id.btn_add_to_list).setOnClickListener {
            handleAdd()
        }

    }

    private fun handleAdd() {
        handleAction(
            getString(R.string.confirm_add_title, teamName),
            getString(R.string.confirm_remove_body, teamName),
            getString(R.string.confirm_add_action)
        ) {
            viewModel.addTeam()
            navigationInAndOut(R.id.main_dest)
        }
    }
}