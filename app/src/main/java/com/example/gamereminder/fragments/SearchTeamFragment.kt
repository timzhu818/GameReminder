package com.example.gamereminder.fragments

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gamereminder.R
import com.example.gamereminder.repositories.RemoteRepository
import com.example.gamereminder.retrofit.Team
import com.example.gamereminder.ui.TeamsAdapter
import com.example.gamereminder.utils.Constants.TEAM_DATA
import com.example.gamereminder.utils.navigationLeftToRight
import com.example.gamereminder.viewModels.SearchTeamViewModel

class SearchTeamFragment: BaseFragment() {

    private val teamsList: MutableList<Team?> = mutableListOf()
    private val viewModel by lazy { SearchTeamViewModel(RemoteRepository()) }

    private val teamsAdapter by lazy {
        TeamsAdapter(teamsList, requireContext()) {
            navigateToDetail(it)
        }
    }

    override fun getContainerId() = R.layout.fragment_search_team

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.header_title).apply {
            text = resources.getString(R.string.search_team_hint)
            visibility = View.VISIBLE
        }
        view.findViewById<SearchView>(R.id.team_search_bar).apply {
            setSearchViewListener()
        }

        view.findViewById<RecyclerView>(R.id.recycle_list_teams).run {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = teamsAdapter
        }

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            handleError(it)
        })

        viewModel.teamsList.observe(viewLifecycleOwner, Observer {
            setUpListView(it)
        })

        viewModel.showProgressBar.observe(viewLifecycleOwner, Observer {
            showHideProgressBar(it)
        })

    }

    private fun setUpListView(teams: List<Team?>?) {
        teams?.let {
            teamsList.apply {
                clear()
                addAll(it)
            }
            teamsAdapter.notifyDataSetChanged()
        }
    }

    private fun SearchView.setSearchViewListener() {
        setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                viewModel.searchTeams(p0)
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

        })
    }

    private fun navigateToDetail(it: Team) {
        navigationLeftToRight(R.id.detail_dest, bundleOf(TEAM_DATA to it))
    }

}