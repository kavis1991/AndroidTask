package com.kavis.androidtask.usersList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kavis.androidtask.databinding.ActivityUsersListBinding

class UsersListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUsersListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsersListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}