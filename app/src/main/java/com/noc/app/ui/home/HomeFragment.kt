package com.noc.app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.noc.app.R
import com.noc.app.api.RequestCenter
import com.noc.lib_network.okhttp.listener.DisposeDataListener

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        RequestCenter.login(object : DisposeDataListener {
            override fun onSuccess(responseObj: Any?) {
                Toast.makeText(activity, "onSuccess" + responseObj.toString(), Toast.LENGTH_LONG)
                    .show()
            }

            override fun onFailure(reasonObj: Any?) {
                Toast.makeText(activity, "onFailure" + reasonObj.toString(), Toast.LENGTH_LONG)
                    .show()
            }
        })
    }
}
