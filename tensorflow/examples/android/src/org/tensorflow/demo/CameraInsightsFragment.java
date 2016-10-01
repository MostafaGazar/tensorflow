package org.tensorflow.demo;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import org.tensorflow.demo.env.ImageUtils;
import org.tensorflow.demo.env.Logger;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class CameraInsightsFragment extends BaseFragment {

    private static final Logger LOGGER = new Logger();

    private static final String ARG_SELECTED_IMAGE_URI = "selected_image_uri";

    private final TensorFlowImageClassifier tensorflow = new TensorFlowImageClassifier();

    private ImageView image;
    private TextView resultsTextView;

    private final CompositeSubscription compositeSubscription = new CompositeSubscription();

    public static @NonNull CameraInsightsFragment newInstance(@NonNull Uri selectedImageUri) {
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
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(final Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        image.setImageBitmap(resource);

                        compositeSubscription.add(
                                Observable
                                        .fromCallable(new Callable<Bitmap>() {
                                            @Override
                                            public Bitmap call() throws Exception {
                                                try {
                                                    tensorflow.initializeTensorFlow(
                                                            getActivity().getAssets(),
                                                            TensorFlowImageListener.MODEL_FILE,
                                                            TensorFlowImageListener.LABEL_FILE,
                                                            TensorFlowImageListener.NUM_CLASSES,
                                                            TensorFlowImageListener.INPUT_SIZE,
                                                            TensorFlowImageListener.IMAGE_MEAN,
                                                            TensorFlowImageListener.IMAGE_STD,
                                                            TensorFlowImageListener.INPUT_NAME,
                                                            TensorFlowImageListener.OUTPUT_NAME);
                                                } catch (IOException e) {
                                                    LOGGER.e(e, "Exception!");
                                                }

                                                Bitmap croppedBitmap = Bitmap.createBitmap(ImageUtils.INPUT_SIZE, ImageUtils.INPUT_SIZE, Bitmap.Config.ARGB_8888);
                                                ImageUtils.drawResizedBitmap(resource, croppedBitmap, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                                                return croppedBitmap;
                                            }
                                        })
                                        .subscribeOn(Schedulers.newThread())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Observer<Bitmap>() {
                                            @Override
                                            public void onCompleted() {

                                            }

                                            @Override
                                            public void onError(Throwable e) {
                                                LOGGER.e("Could not loaded bitmap for classification");
                                            }

                                            @Override
                                            public void onNext(Bitmap bitmap) {
                                                final List<Classifier.Recognition> results = tensorflow.recognizeImage(bitmap);

                                                LOGGER.v("%d results", results.size());

                                                if (results.isEmpty()) {
                                                    resultsTextView.setText("Could find a match!");
                                                } else {
                                                    resultsTextView.setText(TextUtils.join(", ", results));
                                                }
                                            }
                                        }));
                    }
                });
    }

    @Override
    public void onDestroy() {
        compositeSubscription.clear();

        super.onDestroy();
    }

}
