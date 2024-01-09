package kh.edu.rupp.ite.iteforecast.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kh.edu.rupp.ite.iteforecast.databinding.FragmentLocationListBinding

class LocationFragment : Fragment() {

    private lateinit var binging: FragmentLocationListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binging = FragmentLocationListBinding.inflate(inflater, container, false)
        return binging.root
    }

}