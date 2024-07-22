/*
 * SPDX-FileCopyrightText: 2016 The CyanogenMod Project
 * SPDX-License-Identifier: Apache-2.0
 */
package lineageos.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.AttributeSet;

import lineageos.preference.RemotePreference;

import lineageos.preference.R;


/**
 * A link to a remote preference screen which can be used with a minimum amount
 * of information. Supports summary updates asynchronously.
 */
public class LineagePartsPreference extends RemotePreference {

    private static final String TAG = "LineagePartsPreference";

    private final PartInfo mPart;

    private final Context mContext;

    public LineagePartsPreference(Context context, AttributeSet attrs,
                            int defStyle, int defStyleRes) {
        super(context, attrs, defStyle, defStyleRes);
        mContext = context;
        mPart = PartsList.get(context).getPartInfo(getKey());
        if (mPart == null) {
            throw new RuntimeException("Part not found: " + getKey());
        }

        updatePreference();
        setIntent(mPart.getIntentForActivity());
        init(context, attrs);
    }

    public LineagePartsPreference(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs, defStyle, 0);
        init(context, attrs);
    }

    public LineagePartsPreference(Context context, AttributeSet attrs) {
        this(context, attrs, androidx.preference.R.attr.preferenceStyle);
        init(context, attrs);
    }

    @Override
    public void onRemoteUpdated(Bundle bundle) {
        if (bundle.containsKey(PartsList.EXTRA_PART)) {
            PartInfo update = bundle.getParcelable(PartsList.EXTRA_PART);
            if (update != null) {
                mPart.updateFrom(update);
                updatePreference();
            }
        }
    }

    @Override
    protected String getRemoteKey(Bundle metaData) {
        // remote key is the same as ours
        return getKey();
    }

    private void updatePreference() {
        if (isAvailable() != mPart.isAvailable()) {
            setAvailable(mPart.isAvailable());
        }
        if (isAvailable()) {
            setTitle(mPart.getTitle());
            setSummary(mPart.getSummary());
        }
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray b = context.obtainStyledAttributes(attrs, R.styleable.PreferenceLayout);
        int position = b.getInt(R.styleable.PreferenceLayout_position, 3);
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
        }
    }
}
