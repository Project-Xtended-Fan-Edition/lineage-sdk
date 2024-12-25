/*
 * Copyright (C) 2017 AICP
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package lineageos.preference;

import android.content.Context;
import android.provider.Settings;
import android.util.AttributeSet;

import androidx.preference.PreferenceDataStore;

public class SystemSettingSeekBarPreference extends CustomSeekBarPreference {

    public SystemSettingSeekBarPreference(Context context, AttributeSet attrs, int defStyle, int defStyleRes) {
        super(context, attrs, defStyle, defStyleRes);
        setPreferenceDataStore(new DataStore());
    }

    public SystemSettingSeekBarPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setPreferenceDataStore(new DataStore());
    }

    public SystemSettingSeekBarPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPreferenceDataStore(new DataStore());
    }

    public SystemSettingSeekBarPreference(Context context) {
        super(context, null);
        setPreferenceDataStore(new DataStore());
    }

    private class DataStore extends PreferenceDataStore {
        @Override
        public float getFloat(String key, float defValue) {
            return Settings.System.getFloat(getContext().getContentResolver(), key, defValue);
        }

        @Override
        public int getInt(String key, int defValue) {
            return Settings.System.getInt(getContext().getContentResolver(), key, defValue);
        }

        @Override
        public long getLong(String key, long defValue) {
            return Settings.System.getLong(getContext().getContentResolver(), key, defValue);
        }

        @Override
        public String getString(String key, String defValue) {
            String result = Settings.System.getString(getContext().getContentResolver(), key);
            return result == null ? defValue : result;
        }

        @Override
        public void putFloat(String key, float value) {
            Settings.System.putFloat(getContext().getContentResolver(), key, value);
        }

        @Override
        public void putInt(String key, int value) {
            Settings.System.putInt(getContext().getContentResolver(), key, value);
        }

        @Override
        public void putLong(String key, long value) {
            Settings.System.putLong(getContext().getContentResolver(), key, value);
        }

        @Override
        public void putString(String key, String value) {
            Settings.System.putString(getContext().getContentResolver(), key, value);
        }
    }

}
