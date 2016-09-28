/*
 * Copyright 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.tensorflow.demo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;

public class CameraInsightsActivity extends BaseActivity {

    private static final String ARG_SELECTED_IMAGE_URI = "selected_image_uri";

    public static void start(@NonNull Activity activity, @NonNull Uri selectedImageUri) {
        Intent intent = new Intent(activity, CameraInsightsActivity.class);
        intent.putExtra(ARG_SELECTED_IMAGE_URI, selectedImageUri);

        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_camera_insights);

        if (savedInstanceState == null) {
            setFragment();
        }
    }

    private void setFragment() {
        Uri selectedImageUri = getIntent().getParcelableExtra(ARG_SELECTED_IMAGE_URI);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, CameraInsightsFragment.newInstance(selectedImageUri))
                .commit();
    }

}
