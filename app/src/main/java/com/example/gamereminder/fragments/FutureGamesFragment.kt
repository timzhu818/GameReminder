package com.example.gamereminder.fragments

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.WorkManager
import com.example.gamereminder.R
import com.example.gamereminder.repositories.RemoteRepository
import com.example.gamereminder.retrofit.Event
import com.example.gamereminder.ui.GameAdapter
import com.example.gamereminder.utils.Constants
import com.example.gamereminder.viewModels.FutureGamesViewModel

class FutureGamesFragment: BaseFragment() {
    override fun getContainerId() = R.layout.fragment_future_games

    private val futureGames: MutableList<Event?> = mutableListOf()
    private val gameAdapter by lazy {
        GameAdapter(GameAdapter.FUTURE_GAME_TYPE, futureGames, requireContext()) {
            viewModel.buildNotification(it)
            Toast.makeText(requireContext(), getString(R.string.remind_you), Toast.LENGTH_LONG).show()
        }
    }
    private lateinit var textTitle: TextView

    private val viewModel by lazy {
        FutureGamesViewModel(requireArguments(), RemoteRepository(), WorkManager.getInstance(requireContext()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<RecyclerView>(R.id.list_future_games).apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = gameAdapter
        }
        textTitle = view.findViewById<TextView>(R.id.header_title).apply {
            text = resources.getString(R.string.no_game_found)
            visibility = View.VISIBLE
        }
        viewModel.showProgressBar.observe(viewLifecycleOwner, Observer {
            showHideProgressBar(it)
        })
        viewModel.futureGames.observe(viewLifecycleOwner, Observer {
            setUpListView(it)
        })
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            handleError(it)
        })
    }

    private fun setUpListView(it: List<Event?>?) {
        it?.let { list ->
            futureGames.apply {
                clear()
                addAll(list)
            }
            if (futureGames.isNotEmpty()) {
                textTitle.visibility = View.GONE
                gameAdapter.notifyDataSetChanged()
            }

        }
    }

    companion object {
        fun newInstance(id: String?): FutureGamesFragment {
            return FutureGamesFragment().apply { arguments = bundleOf(Constants.TEAM_ID to id) }
        }
    }
}
