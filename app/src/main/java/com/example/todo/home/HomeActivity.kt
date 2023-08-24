package com.example.todo.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.todo.R
import com.example.todo.databinding.ActivityHomeBinding
import com.example.todo.home.fragments.settings.SettingsFragment
import com.example.todo.home.fragments.tasks.AddTaskFragment
import com.example.todo.home.fragments.tasks.TasksListFragment

class HomeActivity : AppCompatActivity() {
    lateinit var binding:ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNav
            .setOnItemSelectedListener {
                when(it.itemId){
                    R.id.tasks->{
                        pushFragment(TasksListFragment())
                    }
                    R.id.settings->{
                        pushFragment(SettingsFragment())
                    }
                }
                return@setOnItemSelectedListener true
            }
        binding.addBtn.setOnClickListener{
            showAddTaskBottomSheet()
        }
        binding.bottomNav.selectedItemId = R.id.tasks
    }

    private fun showAddTaskBottomSheet() {
        val addTaskSheet = AddTaskFragment()
        addTaskSheet.show(supportFragmentManager,"")
    }

    private fun pushFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container,fragment)
            .addToBackStack("")
            .setCustomAnimations(R.anim.fade_in,R.anim.fade_out)
            .commit()




    }
}