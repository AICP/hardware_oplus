/*
 * Copyright (C) 2018-2022 crDroid Android Project
 * Copyright (C) 2022 The Evolution X Project
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

package com.aicp.oplus.OplusParts.slider;

import android.content.Context;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;

public final class RefreshController extends SliderControllerBase {

    public static final int ID = 7;

    private static final String TAG = "BrightnessController";

    private static final int REFRESH_AUTO = 66;
    private static final int REFRESH_MIN = 67;
    private static final int REFRESH_MAX = 68;

    private static final int AUTO = -1f;
    private static final int MIN = 60;
    private static final int MAX = 120;

    public RefreshController(Context context) {
        super(context);
    }

    @Override
    protected int processAction(int action) {
        Log.d(TAG, "slider action: " + action);
        switch (action) {
            case REFRESH_MIN:
                if (writeSettings(writeSettings(Settings.System.MIN_REFRESH_RATE, AUTO))
                    return SliderConstants.MODE_REFRESH_AUTO;
                break;
            case REFRESH_MIN:
                if (writeSettings(writeSettings(Settings.System.MIN_REFRESH_RATE, MIN))
                    return SliderConstants.MODE_REFRESH_MIN;
                break;
            case REFRESH_MAX:
                if (writeSettings(Settings.System.PEAK_REFRESH_RATE, MAX))
                    return SliderConstants.MODE_REFRESH_MAX;
                break;
            default:
                return 0;
        }
        return 0;
    }

    @Override
    public void reset() {
        writeSettings(Settings.System.MIN_REFRESH_RATE,
                Settings.System.PEAK_REFRESH_RATE);
    }

    private boolean writeSettings(String key, int value) {
        return Settings.System.putIntForUser(mContext.getContentResolver(),
                    key, value, UserHandle.USER_CURRENT);
    }
}
