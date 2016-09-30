package org.tensorflow.demo;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class CameraInsightsFragment extends BaseFragment {

    private static final String ARG_SELECTED_IMAGE_URI = "selected_image_uri";

    private ImageView image;
    private TextView resultsTextView;

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

        image = (ImageView) view.findViewById(R.id.image);
        resultsTextView = (TextView) view.findViewById(R.id.results_textview);

        Uri selectedImageUri = getArguments().getParcelable(ARG_SELECTED_IMAGE_URI);

        Glide.with(this)
                .load(selectedImageUri)
                .crossFade()
                .centerCrop()
                .into(image);
    }
}
