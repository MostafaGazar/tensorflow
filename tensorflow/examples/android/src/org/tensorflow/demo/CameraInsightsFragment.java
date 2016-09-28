package org.tensorflow.demo;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CameraInsightsFragment extends BaseFragment {

    private static final String ARG_SELECTED_IMAGE_URI = "selected_image_uri";

    public static @NonNull
    CameraInsightsFragment newInstance(@NonNull Uri selectedImageUri) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_SELECTED_IMAGE_URI, selectedImageUri);

        CameraInsightsFragment fragment = new CameraInsightsFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_camera_insights, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        Glide.with(this)
//                .load(selectedImageUri)
//                .crossFade()
//                .fitCenter()
//                .into(selectedImage);
    }
}
