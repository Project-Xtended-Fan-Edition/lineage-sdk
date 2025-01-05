/*
 * SPDX-FileCopyrightText: 2022 The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package lineageos.preference;

import android.content.Context;
import android.provider.Settings;
import android.util.AttributeSet;

public class GlobalSettingListPreference extends SelfRemovingListPreference {

    public GlobalSettingListPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public GlobalSettingListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public int getIntValue(int defValue) {
        return getValue() == null ? defValue : Integer.valueOf(getValue());
    }

    @Override
    protected boolean isPersisted() {
        return Settings.Global.getString(getContext().getContentResolver(),
                getKey()) != null;
    }

    @Override
    protected void putString(String key, String value) {
        Settings.Global.putString(getContext().getContentResolver(), key, value);
    }

    @Override
    protected String getString(String key, String defaultValue) {
        String result = Settings.Global.getString(getContext().getContentResolver(), key);
        return result == null ? defaultValue : result;
    }
}
