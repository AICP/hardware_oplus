/*
 * Copyright (C) 2016 The OmniROM Project
 *               2022 The Evolution X Project
 * SPDX-License-Identifier: GPL-2.0-or-later
 */

package com.aicp.oplus.OplusParts.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceViewHolder;
import android.util.AttributeSet;

import com.aicp.oplus.OplusParts.kcal.KcalSettings;
import com.aicp.oplus.OplusParts.Utils;
import com.aicp.oplus.OplusParts.R;

public class ContrastPreference extends CustomSeekBarPreference {

    private static int mDefVal;

    private static final int NODE = R.string.node_contrast_preference;

    public ContrastPreference(Context context, AttributeSet attrs) {
        super(context, attrs);

        mInterval = context.getResources().getInteger(R.integer.kcal_contrast_preference_interval);
        mShowSign = false;
        mUnits = "";
        mContinuousUpdates = false;

        int[] mAllValues = context.getResources().getIntArray(R.array.kcal_contrast_preference_array);
        mMinValue = mAllValues[1];
        mMaxValue = mAllValues[2];
        mDefaultValueExists = true;
        mDefVal = mAllValues[0];
        mDefaultValue = mDefVal;
        mValue = Integer.parseInt(loadValue(context));

        setPersistent(false);
    }

    private static String getFile(Context context) {
        String file = context.getString(NODE);
        if (Utils.fileWritable(file)) {
            return file;
        }
        return null;
    }

    public static boolean isSupported(Context context) {
        return Utils.fileWritable(getFile(context));
    }

    public static void restore(Context context) {
        if (!isSupported(context)) {
            return;
        }

        int[] mAllValues = context.getResources().getIntArray(R.array.kcal_contrast_preference_array);
        mDefVal = mAllValues[0];
        String storedValue = PreferenceManager.getDefaultSharedPreferences(context).getString(KcalSettings.KEY_CONTRAST, String.valueOf(mDefVal));
        Utils.writeValue(getFile(context), storedValue);
    }

    public static String loadValue(Context context) {
        return Utils.getFileValue(getFile(context), String.valueOf(mDefVal));
    }

    private void saveValue(String newValue) {
        Utils.writeValue(getFile(getContext()), newValue);
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
        editor.putString(KcalSettings.KEY_CONTRAST, newValue);
        editor.apply();
    }

    @Override
    protected void changeValue(int newValue) {
        saveValue(String.valueOf(newValue));
    }
}
