package com.route.todosappc38online.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Spinner
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.Fragment
import com.route.todosappc38online.R
import com.route.todosappc38online.databinding.FragmentSettingsBinding
import java.util.Locale

class SettingsFragment : Fragment() {
    lateinit var languageSpinner: Spinner
    lateinit var modeSpinner: Spinner
    lateinit var binding : FragmentSettingsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        languageSpinner = view.findViewById(R.id.language_spinner)
        modeSpinner = view.findViewById(R.id.mode_spinner)

        languageSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position == 1) {
                    val localeListCompat = LocaleListCompat.create(Locale("en"))

                    AppCompatDelegate.setApplicationLocales(localeListCompat)
                } else if (position == 2) {
                    val localeListCompat = LocaleListCompat.create(Locale("ar"))

                    AppCompatDelegate.setApplicationLocales(localeListCompat)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
        modeSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position == 1) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                } else if (position == 2) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
    }
}