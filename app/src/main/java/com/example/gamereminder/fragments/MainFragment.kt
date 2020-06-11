package com.example.gamereminder.fragments

import android.os.Bundle
import android.util.Log
import android.view.DragEvent
import android.view.View
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gamereminder.R
import com.example.gamereminder.repositories.LocalRepository
import com.example.gamereminder.retrofit.Team
import com.example.gamereminder.ui.TeamsAdapter
import com.example.gamereminder.utils.Constants.TEAM_ID
import com.example.gamereminder.utils.Constants.TEAM_NAME
import com.example.gamereminder.utils.navigationLeftToRight
import com.example.gamereminder.viewModels.MainViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainFragment : BaseFragment() {

    private val teamsList: MutableList<Team?> = mutableListOf()
    private val viewModel by lazy { MainViewModel(LocalRepository(requireContext())) }

    private val teamsAdapter by lazy {
        TeamsAdapter(teamsList, requireContext()) { team ->
            navigationLeftToRight(R.id.games_dest, bundleOf(TEAM_ID to team.idTeam, TEAM_NAME to team.strTeam))
        }
    }

    override fun getContainerId() = R.layout.fragment_main

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.showWelcome.observe(viewLifecycleOwner, Observer {
            if (it) view.findViewById<TextView>(R.id.header_title).apply {
                text = resources.getString(R.string.welcome_title)
                visibility = View.VISIBLE
            }
        })

        view.findViewById<RecyclerView>(R.id.recycle_list_teams).run {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = teamsAdapter
        }

        viewModel.teamList.observe(viewLifecycleOwner, Observer {
            setUpListView(it)
        })

        viewModel.showProgressBar.observe(viewLifecycleOwner, Observer {
            showHideProgressBar(it)
        })

        view.findViewById<FloatingActionButton>(R.id.fab_add).setOnClickListener(Navigation.createNavigateOnClickListener(R.id.find_new_team))
    }


    private fun setUpListView(teams: List<Team>) {
        teams.let {
            teamsList.apply {
                clear()
                addAll(it)
            }
            teamsAdapter.notifyDataSetChanged()
        }
    }

}