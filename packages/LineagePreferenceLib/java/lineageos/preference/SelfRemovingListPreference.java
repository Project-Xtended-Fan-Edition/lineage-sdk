/*
 * SPDX-FileCopyrightText: 2016 The CyanogenMod Project
 * SPDX-FileCopyrightText: 2018 The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 */
package lineageos.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.preference.ListPreference;
import androidx.preference.PreferenceDataStore;
import androidx.preference.PreferenceViewHolder;

import lineageos.preference.R;

/**
 * A Preference which can automatically remove itself from the hierarchy
 * based on constraints set in XML.
 */
public abstract class SelfRemovingListPreference extends ListPreference {

    private final ConstraintsHelper mConstraints;

    public SelfRemovingListPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mConstraints = new ConstraintsHelper(context, attrs, this);
        setPreferenceDataStore(new DataStore());
        init(context, attrs);
    }

    public SelfRemovingListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        mConstraints = new ConstraintsHelper(context, attrs, this);
        setPreferenceDataStore(new DataStore());
        init(context, attrs);
    }

    public SelfRemovingListPreference(Context context) {
        super(context);
        mConstraints = new ConstraintsHelper(context, null, this);
        setPreferenceDataStore(new DataStore());
    }

    @Override
    public void onAttached() {
        super.onAttached();
        mConstraints.onAttached();
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);
        mConstraints.onBindViewHolder(holder);
    }

    public void setAvailable(boolean available) {
        mConstraints.setAvailable(available);
    }

    public boolean isAvailable() {
        return mConstraints.isAvailable();
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray b = context.obtainStyledAttributes(attrs, R.styleable.PreferenceLayout);
        int position = b.getInt(R.styleable.PreferenceLayout_position, 4);
        b.recycle();

        switch (position) {
            case 0: // Top
                setLayoutResource(R.layout.tenx_preference_top);
                break;
            case 1: // Middle
                setLayoutResource(R.layout.tenx_preference_middle);
                break;
            case 2: // Bottom
                setLayoutResource(R.layout.tenx_preference_bottom);
                break;
            case 3: // Full
                setLayoutResource(R.layout.tenx_preference);
                break;
            case 4: // None
                return;
        }
    }

    protected abstract boolean isPersisted();
    protected abstract void putString(String key, String value);
    protected abstract String getString(String key, String defaultValue);

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
        final String value;
        if (!restorePersistedValue || !isPersisted()) {
            if (defaultValue == null) {
                return;
            }
            value = (String) defaultValue;
            if (shouldPersist()) {
                persistString(value);
            }
        } else {
            // Note: the default is not used because to have got here
            // isPersisted() must be true.
            value = getString(getKey(), null /* not used */);
        }
        setValue(value);
    }

    private class DataStore extends PreferenceDataStore {
        @Override
        public void putString(String key, String value) {
            SelfRemovingListPreference.this.putString(key, value);
        }

        @Override
        public String getString(String key, String defaultValue) {
            return SelfRemovingListPreference.this.getString(key, defaultValue);
        }
    }
}
