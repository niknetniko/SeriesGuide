package com.battlelancer.seriesguide.ui.movies

import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import com.battlelancer.seriesguide.R
import com.battlelancer.seriesguide.provider.SeriesGuideContract
import com.battlelancer.seriesguide.ui.MoviesActivity
import com.battlelancer.seriesguide.ui.MoviesActivity.WATCHED_LOADER_ID

/**
 * Displays the list of movies the user has watched.
 */
class MoviesWatchedFragment: MoviesBaseFragment() {

    private val CONTEXT_WATCHED_REMOVE_ID = 0;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = super.onCreateView(inflater, container, savedInstanceState)
        emptyView.setText(R.string.movies_watched_empty)
        return v
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(context!!, SeriesGuideContract.Movies.CONTENT_URI,
                MoviesCursorAdapter.MoviesQuery.PROJECTION, SeriesGuideContract.Movies.SELECTION_WATCHED, null,
                MoviesDistillationSettings.getSortQuery(context))
    }

    override fun getTabPosition(showingNowTab: Boolean): Int {
        return MoviesActivity.TAB_POSITION_WATCHED
    }

    override fun onPopupMenuClick(v: View?, movieTmdbId: Int) {
        val popupMenu = PopupMenu(v?.context, v)
        popupMenu.menu
                .add(0, CONTEXT_WATCHED_REMOVE_ID, 0, R.string.action_unwatched)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                CONTEXT_WATCHED_REMOVE_ID -> {
                    MovieTools.unwatchedMovie(context, movieTmdbId)
                    true
                }
                else -> false

            }
        }
        popupMenu.show()
    }

    override fun getLoaderId() = WATCHED_LOADER_ID
}