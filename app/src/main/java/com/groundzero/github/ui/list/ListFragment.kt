package com.groundzero.github.ui.list


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.groundzero.github.R
import com.groundzero.github.core.RemoteBaseFragment
import com.groundzero.github.data.remote.Github
import com.groundzero.github.data.response.Status
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : RemoteBaseFragment(), ListItem {

    private lateinit var listViewModel: ListViewModel
    private lateinit var listPageAdapter: ListPageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listViewModel = ViewModelProviders.of(this, this.viewModelFactory).get(ListViewModel::class.java)
        listPageAdapter = ListPageAdapter(context!!, this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listViewModel.itemPagedList.observe(viewLifecycleOwner, Observer { repositories ->
            run {
                listPageAdapter.submitList(repositories)
            }
        })
        mutableRecyclerView(repositories_recycler_view).adapter = listPageAdapter

        listViewModel.liveResponse.observe(viewLifecycleOwner, Observer { response ->
            run {
                when (response.status) {
                    Status.SUCCESS -> {
                        progressBarVisibility(list_progress_bar as ProgressBar, false)
                    }
                    Status.LOADING -> {
                        progressBarVisibility(list_progress_bar as ProgressBar, true)
                    }
                    Status.ERROR -> {
                        progressBarVisibility(list_progress_bar as ProgressBar, false)
                    }
                }
            }
        })
    }

    override fun onItemClickListener(github: Github): View.OnClickListener =
        View.OnClickListener { view ->
            run {
                listViewModel.cacheSelectedItemUsernameAndRepoName(github.owner.login, github.name)
                Navigation.findNavController(view).navigate(R.id.repoFragment)
            }
        }
}