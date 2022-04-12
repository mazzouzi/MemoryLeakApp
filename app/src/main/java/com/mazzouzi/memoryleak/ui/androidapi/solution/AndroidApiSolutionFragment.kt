package com.mazzouzi.memoryleak.ui.androidapi.solution

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mazzouzi.memoryleak.R
import com.mazzouzi.memoryleak.databinding.FragmentAndroidApiSolutionBinding


class AndroidApiSolutionFragment : Fragment() {

    private var _binding: FragmentAndroidApiSolutionBinding? = null
    private val binding get() = _binding!!

    private lateinit var localBroadcastReceiver: BroadcastReceiver

    var wifiStateChanged = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAndroidApiSolutionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.instruction.text = getString(
            R.string.android_api_reference,
            getString(R.string.no_memory_leak_instructions)
        )
    }

    override fun onStart() {
        super.onStart()
        registerBroadCastReceiver()
    }

    private fun registerBroadCastReceiver() {
        localBroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                wifiStateChanged = true
            }
        }

        activity?.registerReceiver(
            localBroadcastReceiver,
            IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION)
        )
    }

    override fun onStop() {
        super.onStop()
        /**
         * Always unregister to the Android API before the view gets destroyed
         * -> no memory leak ✅
         */
        activity?.unregisterReceiver(localBroadcastReceiver)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        val TAG: String = AndroidApiSolutionFragment::class.java.simpleName
        fun newInstance() = AndroidApiSolutionFragment()
    }
}