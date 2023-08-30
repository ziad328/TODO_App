package com.example.todo.home

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.todo.R
import com.example.todo.databinding.ActivityHomeBinding
import com.example.todo.home.fragments.settings.SettingsFragment
import com.example.todo.home.fragments.tasks.AddTaskFragment
import com.example.todo.home.fragments.tasks.TasksListFragment

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    var tasksListFragmentRef: TasksListFragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBottomNav()
    }

    private fun initBottomNav() {
        binding.bottomNav
            .setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.tasks -> {
                        tasksListFragmentRef = TasksListFragment()
                        pushFragment(tasksListFragmentRef!!)
                        binding.title.text = getString(R.string.to_do_list)
                    }

                    R.id.settings -> {
                        pushFragment(SettingsFragment())
                        binding.title.text = getString(R.string.settings)
                    }
                }
                return@setOnItemSelectedListener true
            }
        binding.addBtn.setOnClickListener {
            showAddTaskBottomSheet()
        }
        binding.bottomNav.selectedItemId = R.id.tasks
    }

    override fun onResume() {
        super.onResume()
        binding.bottomNav.selectedItemId = R.id.tasks
    }

    private fun showAddTaskBottomSheet() {
        val addTaskSheet = AddTaskFragment()
        addTaskSheet.onTaskAddedListener = AddTaskFragment.OnTaskAddedListener {
            Toast.makeText(this, "Task Added", Toast.LENGTH_SHORT)
                .show()
            tasksListFragmentRef?.loadAllTasks()
        }
        addTaskSheet.show(supportFragmentManager, "")
    }

    private fun pushFragment(fragment: Fragment) {

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            .commit()
    }

}