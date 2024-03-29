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

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.input.InputManager;
import android.os.FileObserver;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.Vibrator;
import android.provider.Settings;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.KeyEvent;

import androidx.annotation.Keep;

import com.android.internal.os.DeviceKeyHandler;

import java.util.Arrays;

import com.aicp.oplus.OplusParts.Constants;
import com.aicp.oplus.OplusParts.SliderControllerBase;
import com.aicp.oplus.OplusParts.slider.NotificationController;
import com.aicp.oplus.OplusParts.slider.FlashlightController;
import com.aicp.oplus.OplusParts.slider.BrightnessController;
import com.aicp.oplus.OplusParts.slider.RotationController;
import com.aicp.oplus.OplusParts.slider.RingerController;
import com.aicp.oplus.OplusParts.slider.NotificationRingerController;
import com.aicp.oplus.OplusParts.slider.RefreshController;
import com.aicp.oplus.OplusParts.slider.ExtraDimController;
import com.aicp.oplus.OplusParts.slider.NightLightController;
import com.aicp.oplus.OplusParts.slider.ColorSpaceController;

@Keep
public class KeyHandler implements DeviceKeyHandler {
    private static final String TAG = KeyHandler.class.getSimpleName();
    private static final boolean DEBUG = false;

    private final Context mContext;
    private final NotificationController mNotificationController;
    private final FlashlightController mFlashlightController;
    private final BrightnessController mBrightnessController;
    private final RotationController mRotationController;
    private final RingerController mRingerController;
    private final NotificationRingerController mNotificationRingerController;
    private final RefreshController mRefreshController;
    private final ExtraDimController mExtraDimController;
    private final NightLightController mNightLightController;
    private final ColorSpaceController mColorSpaceController;

    private SliderControllerBase mSliderController;

    private final InputManager mInputManager;

    private final BroadcastReceiver mSliderUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int usage = intent.getIntExtra(Constants.EXTRA_SLIDER_USAGE, 0);
            int[] actions = intent.getIntArrayExtra(Constants.EXTRA_SLIDER_ACTIONS);

            Log.d(TAG, "update usage " + usage + " with actions " +
                    Arrays.toString(actions));

            if (mSliderController != null) {
                mSliderController.reset();
            }

            switch (usage) {
                case NotificationController.ID:
                    mSliderController = mNotificationController;
                    mSliderController.update(actions);
                    break;
                case FlashlightController.ID:
                    mSliderController = mFlashlightController;
                    mSliderController.update(actions);
                    break;
                case BrightnessController.ID:
                    mSliderController = mBrightnessController;
                    mSliderController.update(actions);
                    break;
                case RotationController.ID:
                    mSliderController = mRotationController;
                    mSliderController.update(actions);
                    break;
                case RingerController.ID:
                    mSliderController = mRingerController;
                    mSliderController.update(actions);
                    break;
                case NotificationRingerController.ID:
                    mSliderController = mNotificationRingerController;
                    mSliderController.update(actions);
                    break;
                case RefreshController.ID:
                    mSliderController = mRefreshController;
                    mSliderController.update(actions);
                    break;
                case ExtraDimController.ID:
                    mSliderController = mExtraDimController;
                    mSliderController.update(actions);
                    break;
                case NightLightController.ID:
                    mSliderController = mNightLightController;
                    mSliderController.update(actions);
                    break;
                case ColorSpaceController.ID:
                    mSliderController = mColorSpaceController;
                    mSliderController.update(actions);
                    break;
            }

            mSliderController.restoreState(context, false);
        }
    };

    public KeyHandler(Context context) {
        mContext = context;

        mNotificationController = new NotificationController(mContext);
        mFlashlightController = new FlashlightController(mContext);
        mBrightnessController = new BrightnessController(mContext);
        mRotationController = new RotationController(mContext);
        mRingerController = new RingerController(mContext);
        mNotificationRingerController = new NotificationRingerController(mContext);
        mRefreshController = new RefreshController(mContext);
        mExtraDimController = new ExtraDimController(mContext);
        mNightLightController = new NightLightController(mContext);
        mColorSpaceController = new ColorSpaceController(mContext);

        mContext.registerReceiver(mSliderUpdateReceiver,
                new IntentFilter(Constants.ACTION_UPDATE_SLIDER_SETTINGS));

        mInputManager = mContext.getSystemService(InputManager.class);
    }

    public KeyEvent handleKeyEvent(KeyEvent event) {
        if (event.getAction() != KeyEvent.ACTION_DOWN) {
            return event;
        }

        String deviceName = mInputManager.getInputDevice(event.getDeviceId()).getName();
        if (!deviceName.equals("oplus,hall_tri_state_key") && !deviceName.equals("oplus,tri-state-key")) {
            return event;
        }

        mSliderController.processEvent(mContext);

        return null;
    }
}
