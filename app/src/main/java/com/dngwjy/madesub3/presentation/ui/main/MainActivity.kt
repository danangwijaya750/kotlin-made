package com.dngwjy.madesub3.presentation.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.dngwjy.madesub3.R
import com.dngwjy.madesub3.presentation.ui.main.bookmarkfragment.BookmarkFragment
import com.dngwjy.madesub3.presentation.ui.main.moviefragment.MovieFragment
import com.dngwjy.madesub3.presentation.ui.main.tvfragment.TvFragment
import com.dngwjy.madesub3.presentation.ui.reminder.SettingReminder
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        var searchOn: String = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(navListener)
        if (null == savedInstanceState) {
            searchOn = "movie"
            changeFragment(MovieFragment.newInstance())
        }
    }

    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener {
        when (it.itemId) {
            R.id.movies -> {
                searchOn = "movie"
                changeFragment(MovieFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.tvs -> {
                searchOn = "tv"
                changeFragment(TvFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.bmk -> {
                changeFragment(BookmarkFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }


    private fun changeFragment(fragment: Fragment) {
        val transact = supportFragmentManager.beginTransaction()
        transact.replace(R.id.frame_container, fragment)
        transact.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.menu_search)?.actionView as? SearchView
        searchView?.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId === R.id.action_change_settings) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        } else if (item?.itemId === R.id.action_remind) {
            startActivity(Intent(this, SettingReminder::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}
