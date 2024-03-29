/*
 * Copyright (C) 2016 The OmniROM Project
 *               2022 The Evolution X Project
 * SPDX-License-Identifier: GPL-2.0-or-later
 */

package com.aicp.oplus.OplusParts.modeswitch;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.preference.Preference;
import androidx.preference.Preference.OnPreferenceChangeListener;

import com.aicp.oplus.OplusParts.Utils;
import com.aicp.oplus.OplusParts.R;

public class USB2FastChargeModeSwitch implements OnPreferenceChangeListener {

    private static final int NODE = R.string.node_usb2_fast_charge_mode_switch;

    public static String getFile(Context context) {
        String file = context.getString(NODE);
        if (Utils.fileWritable(file)) {
            return file;
        }
        return null;
    }

    public static boolean isSupported(Context context) {
        return Utils.fileWritable(getFile(context));
    }

    public static boolean isCurrentlyEnabled(Context context) {
        return Utils.getFileValueAsBoolean(getFile(context), false,
            context.getResources().getString(R.string.node_usb2_fast_charge_mode_switch_true),
            context.getResources().getString(R.string.node_usb2_fast_charge_mode_switch_false));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        Boolean enabled = (Boolean) newValue;
        Utils.writeValue(getFile(preference.getContext()), enabled ? "1" : "0");
        return true;
    }
}
