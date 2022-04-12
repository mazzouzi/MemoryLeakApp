package com.mazzouzi.memoryleak.ui.androidapi.leak

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
import com.mazzouzi.memoryleak.databinding.FragmentAndroidApiLeakBinding


class AndroidApiLeakFragment : Fragment() {

    private var _binding: FragmentAndroidApiLeakBinding? = null
    private val binding get() = _binding!!

    private lateinit var localBroadcastReceiver: BroadcastReceiver

    var wifiStateChanged = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAndroidApiLeakBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.instruction.text = getString(
            R.string.android_api_reference,
            getString(R.string.memory_leak_instructions)
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

        /**
         * We are registering a receiver that is called whenever the WIFI state changes.
         * As we do not unregister during onStop(), the system keeps a reference of the Activity
         * even after it gets destroyed -> Memory leak ❌
         */
        activity?.registerReceiver(
            localBroadcastReceiver,
            IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        val TAG: String = AndroidApiLeakFragment::class.java.simpleName
        fun newInstance() = AndroidApiLeakFragment()
    }
}