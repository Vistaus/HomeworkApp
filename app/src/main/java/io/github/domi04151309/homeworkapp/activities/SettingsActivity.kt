package io.github.domi04151309.homeworkapp.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import android.net.Uri
import io.github.domi04151309.homeworkapp.R
import io.github.domi04151309.homeworkapp.helpers.Global
import io.github.domi04151309.homeworkapp.helpers.P
import io.github.domi04151309.homeworkapp.helpers.Theme


class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Theme.set(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, GeneralPreferenceFragment())
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class GeneralPreferenceFragment : PreferenceFragmentCompat() {

        private val prefsChangedListener =
            SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
                if (key == P.PREF_THEME) requireActivity().recreate()
            }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(
                prefsChangedListener
            )
        }

        override fun onDestroy() {
            super.onDestroy()
            preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(
                prefsChangedListener
            )
        }
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            addPreferencesFromResource(R.xml.pref_general)
            findPreference<Preference>("reset_json")?.onPreferenceClickListener =
                Preference.OnPreferenceClickListener {
                    AlertDialog.Builder(requireContext())
                        .setTitle(R.string.pref_reset)
                        .setMessage(R.string.pref_reset_question)
                        .setPositiveButton(android.R.string.ok) { _, _ ->
                            PreferenceManager.getDefaultSharedPreferences(context).edit()
                                .putString("planner_json", Global.DEFAULT_JSON).apply()
                            Toast.makeText(context, R.string.pref_reset_toast, Toast.LENGTH_LONG)
                                .show()
                        }
                        .setNegativeButton(android.R.string.cancel) { _, _ -> }
                        .show()
                    true
                }
            findPreference<Preference>("about")?.onPreferenceClickListener =
                Preference.OnPreferenceClickListener {
                    startActivity(Intent(context, AboutActivity::class.java))
                    true
                }
            findPreference<Preference>("header")?.onPreferenceClickListener =
                Preference.OnPreferenceClickListener {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://unsplash.com/photos/duvq92-VCZ4")
                        )
                    )
                    true
                }
        }
    }
}
