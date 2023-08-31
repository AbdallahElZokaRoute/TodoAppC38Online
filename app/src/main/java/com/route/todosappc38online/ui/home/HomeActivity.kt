package com.route.todosappc38online.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.route.todosappc38online.R
import com.route.todosappc38online.databinding.ActivityHomeBinding
import com.route.todosappc38online.fragments.AddTodoBottomSheetFragment
import com.route.todosappc38online.fragments.SettingsFragment
import com.route.todosappc38online.fragments.TodosListFragment

class HomeActivity : AppCompatActivity() {

    lateinit var binding : ActivityHomeBinding
     var todosListFragment  : TodosListFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_list -> {
                    todosListFragment = TodosListFragment()
                    pushFragment(todosListFragment!!)
                }
                R.id.navigation_settings -> {
                    pushFragment(SettingsFragment())
                }
            }
            return@setOnItemSelectedListener true
        }
        binding.bottomNavigationView.selectedItemId = R.id.navigation_list

        binding.addTodoButton.setOnClickListener {
            showBottomSheet()
        }

    }

    private fun showBottomSheet() {
        val bottomSheetFragment = AddTodoBottomSheetFragment()
        bottomSheetFragment.onTaskAddedListener = AddTodoBottomSheetFragment.OnTaskAddedListener {
            Snackbar.make(binding.root,"Task Added Successfully",Snackbar.LENGTH_LONG).show()
            todosListFragment?.loadDateTasks()
        }
        bottomSheetFragment.show(supportFragmentManager, "Add-Todo")


    }

    private fun pushFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .setCustomAnimations(R.anim.fade_in,R.anim.fade_out)
            .commit()
    }
}