package com.cyris.dailynews;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public class SettingPreferences extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.setting_preferenee);
        findPreference("privacyPolicy").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(getContext(),WebViewActivity.class);
                intent.putExtra("url",Links.PRIVACY_POLICY);
                startActivity(intent);

                return true;
            }
        });

    findPreference("contactUs").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(getContext(),WebViewActivity.class);
                intent.putExtra("url",Links.CONTACT_US);
                startActivity(intent);

                return true;
            }
        });

    findPreference("termsAndConditions").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(getContext(),WebViewActivity.class);
                intent.putExtra("url",Links.TERMS_AND_CONDITIONS);
                startActivity(intent);

                return true;
            }
        });

    }
}
