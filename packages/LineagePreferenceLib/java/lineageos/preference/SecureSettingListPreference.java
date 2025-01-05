/*
 * SPDX-FileCopyrightText: 2016 The CyanogenMod Project
 * SPDX-FileCopyrightText: 2018 The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 */
package lineageos.preference;

import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.AttributeSet;

public class SecureSettingListPreference extends SelfRemovingListPreference {

    private boolean mAutoSummary = false;

    public SecureSettingListPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SecureSettingListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setValue(String value) {
        super.setValue(value);
        if (mAutoSummary || TextUtils.isEmpty(getSummary())) {
            setSummary(getEntry(), true);
        }
    }

    @Override
    public void setSummary(CharSequence summary) {
        setSummary(summary, false);
    }

    private void setSummary(CharSequence summary, boolean autoSummary) {
        mAutoSummary = autoSummary;
        super.setSummary(summary);
    }

    public int getIntValue(int defValue) {
        return getValue() == null ? defValue : Integer.valueOf(getValue());
    }

    @Override
    protected boolean isPersisted() {
        return Settings.Secure.getString(getContext().getContentResolver(), getKey()) != null;
    }

    @Override
    protected void putString(String key, String value) {
        Settings.Secure.putString(getContext().getContentResolver(), key, value);
    }

    @Override
    protected String getString(String key, String defaultValue) {
        String result = Settings.Secure.getString(getContext().getContentResolver(), key);
        return result == null ? defaultValue : result;
    }
}
