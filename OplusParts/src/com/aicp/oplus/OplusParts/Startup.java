/*
 * Copyright (C) 2018-2022 crDroid Android Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.aicp.oplus.OplusParts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import androidx.preference.PreferenceManager;

import com.aicp.oplus.OplusParts.Utils;

public class Startup extends BroadcastReceiver {

    private static final String TAG = "BootReceiver";

    @Override
    public void onReceive(final Context context, final Intent bootintent) {
            OplusParts.restoreSliderStates(context);
            OplusParts.restoreFastChargeSetting(context);
            OplusParts.restoreVibStrengthSetting(context);
    }

    private void restore(String file, String value) {
        if (file == null) {
            return;
        }
        Utils.writeValue(file, value);
    }
}
