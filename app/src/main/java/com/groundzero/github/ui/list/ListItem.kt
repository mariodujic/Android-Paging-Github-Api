package com.groundzero.github.ui.list

import android.view.View
import com.groundzero.github.data.remote.Github

interface ListItem {
    fun onItemClickListener(github: Github): View.OnClickListener
}