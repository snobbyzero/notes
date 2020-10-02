package com.example.notes.activity.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.notes.R
import com.example.notes.databinding.ActivityMainBinding
import com.example.notes.di.ViewModelFactory
import com.example.notes.fragment.notes_list.NotesListFragment
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject lateinit var modelFactory: ViewModelFactory
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel = ViewModelProvider(this, modelFactory)[MainViewModel::class.java]

        with(viewModel) {
            saveToken()
            saveSession()
        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, NotesListFragment.newInstance())
            .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onBackPressed()
        return super.onOptionsItemSelected(item)
    }

}