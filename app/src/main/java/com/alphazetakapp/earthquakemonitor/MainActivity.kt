package com.alphazetakapp.earthquakemonitor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alphazetakapp.earthquakemonitor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.eqRecycler.layoutManager = LinearLayoutManager(this)
        //A ESTO SE LE LLAMA LIVE DATA CON LO QUE HAY EN MAINVIEWMODEL
        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val adapter = EqAdapter(this)
        binding.eqRecycler.adapter = adapter

        viewModel.eqList.observe(this, Observer {
            eqList ->
            adapter.submitList(eqList)

            handleEmptyView(eqList)
        })

        adapter.onItemClickListener = {
            Toast.makeText(this, it.place, Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleEmptyView(eqList: MutableList<Earthquake>) {
        if (eqList.isEmpty()) {
            binding.eqEmptyView.visibility = View.VISIBLE
        } else {
            binding.eqEmptyView.visibility = View.GONE
        }
    }
}
