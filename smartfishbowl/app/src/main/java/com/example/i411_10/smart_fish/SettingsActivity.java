package com.example.i411_10.smart_fish;


import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.text.TextUtils;
import android.widget.DatePicker;

import java.util.Calendar;

public class SettingsActivity extends PreferenceActivity implements DatePickerDialog.OnDateSetListener{

    private long r;
    private long t;
    private long d;
    private int resultNumber = 0;



    Calendar calendar = Calendar.getInstance();
    Calendar tcalendar = Calendar.getInstance();              //현재 날짜 불러옴

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.pref_settings);
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        Preference btnDateFilter = findPreference("btnDateFilter");
        if(pref.getString("histring", "") != "")
            btnDateFilter.setSummary(pref.getString("histring", "")); // get string);

        btnDateFilter.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public boolean onPreferenceClick(Preference preference) {
                showDateDialog();
                return false;
            }
        });


        setOnPreferenceChange(findPreference("autoUpdate_ringtone"));




    }

    private void setOnPreferenceChange(Preference mPreference) {
        mPreference.setOnPreferenceChangeListener(onPreferenceChangeListener);

        onPreferenceChangeListener.onPreferenceChange(
                mPreference,
                PreferenceManager.getDefaultSharedPreferences(
                        mPreference.getContext()).getString(
                        mPreference.getKey(), ""));
    }

    private Preference.OnPreferenceChangeListener onPreferenceChangeListener = new Preference.OnPreferenceChangeListener() {

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String stringValue = newValue.toString();


            if (preference instanceof EditTextPreference) {
                preference.setSummary(stringValue);

            } else if (preference instanceof ListPreference) {
                /**
                 * ListPreference의 경우 stringValue가 entryValues이기 때문에 바로 Summary를
                 * 적용하지 못한다 따라서 설정한 entries에서 String을 로딩하여 적용한다
                 */

                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);

                preference
                        .setSummary(index >= 0 ? listPreference.getEntries()[index]
                                : null);

            } else if (preference instanceof RingtonePreference) {
                /**
                 * RingtonePreference의 경우 stringValue가
                 * content://media/internal/audio/media의 형식이기 때문에
                 * RingtoneManager을 사용하여 Summary를 적용한다
                 *
                 * 무음일경우 ""이다
                 */

                if (TextUtils.isEmpty(stringValue)) {
                    // Empty values correspond to 'silent' (no ringtone).
                    preference.setSummary("무음으로 설정됨");

                } else {
                    Ringtone ringtone = RingtoneManager.getRingtone(
                            preference.getContext(), Uri.parse(stringValue));

                    if (ringtone == null) {
                        // Clear the summary if there was a lookup error.
                        preference.setSummary(null);

                    } else {
                        String name = ringtone
                                .getTitle(preference.getContext());
                        preference.setSummary(name);
                    }
                }
            }

            return true;
        }

    };

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {

        calendar.set(i, i2, i3);
        t = tcalendar.getTimeInMillis();                 //오늘 날짜를 밀리타임으로 바꿈
        d = calendar.getTimeInMillis();              //디데이날짜를 밀리타임으로 바꿈
        r = (t - d) / (24 * 60 * 60 * 1000);                 //디데이 날짜에서 오늘 날짜를 뺀 값을 '일'단위로 바꿈

        resultNumber = (int) r;

        String s = String.valueOf(resultNumber);
        Preference p = findPreference("btnDateFilter");
        p.setSummary(s);
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("histring", s);
        editor.apply();
    }

    private void showDateDialog() {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(this, this, year, month, day).show();

    }


}
