package com.example.gamereminder.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.gamereminder.R
import com.example.gamereminder.repositories.LocalRepository
import com.example.gamereminder.utils.Constants.TEAM_ID
import com.example.gamereminder.utils.Constants.TEAM_NAME
import com.example.gamereminder.utils.navigationInAndOut
import com.example.gamereminder.viewModels.GamesViewModel
import com.google.android.material.tabs.TabLayout

class GamesFragment : BaseFragment() {
    override fun getContainerId() = R.layout.fragment_games

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager

    val pastGameFragment by lazy {
        PastGamesFragment.newInstance(
            requireArguments().getString(
                TEAM_ID
            )
        )
    }
    val futureGamesFragment by lazy {
        FutureGamesFragment.newInstance(
            requireArguments().getString(
                TEAM_ID
            )
        )
    }

    private val viewModel by lazy {
        GamesViewModel(LocalRepository(requireContext()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager =
            view.findViewById<ViewPager>(R.id.vp_past_future_games).apply { setUpViewPager(this) }
        tabLayout =
            view.findViewById<TabLayout>(R.id.tab_past_future_games).apply { setUpTabLayout(this) }
    }

    private fun setUpViewPager(vp: ViewPager) = vp.apply {
        adapter = object :
            FragmentPagerAdapter(childFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            override fun getItem(position: Int) = when (position) {
                POS_FUTURE -> futureGamesFragment
                else -> pastGameFragment
            }

            override fun getCount(): Int {
                return TAB_COUNT
            }
        }
    }

    private fun setUpTabLayout(tl: TabLayout) = tl.apply {
        addTab(newTab().setText(R.string.future_games), POS_FUTURE)
        addTab(newTab().setText(R.string.past_games), POS_PAST)
        tabGravity = TabLayout.GRAVITY_CENTER
    }.apply {
        addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let { viewPager.currentItem = it.position }
            }

        })
    }.apply { viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(this)) }

    companion object {
        const val TAB_COUNT = 2
        const val POS_PAST = 1
        const val POS_FUTURE = 0
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_game, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete) {
            handleRemove()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun handleRemove() {
        val teamName = requireArguments().getString(TEAM_NAME)
        handleAction(
            getString(R.string.confirm_remove_title, teamName),
            getString(R.string.confirm_remove_body, teamName),
            getString(R.string.confirm_remove_action)
        ) {
            viewModel.deleteTeam(requireArguments().getString(TEAM_ID))
            navigationInAndOut(R.id.main_dest)
        }
    }

}