package kh.edu.rupp.ite.iteforecast.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kh.edu.rupp.ite.iteforecast.databinding.FragmentHomeBinding
import androidx.fragment.app.FragmentManager

class HomeFragment: Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

            binding = FragmentHomeBinding.inflate(inflater, container, false)
            return binding.root
    }

}