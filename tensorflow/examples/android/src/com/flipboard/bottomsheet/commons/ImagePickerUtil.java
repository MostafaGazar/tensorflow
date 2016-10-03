package com.flipboard.bottomsheet.commons;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.annotation.Nullable;

public class ImagePickerUtil {

    public static @Nullable
    Cursor getImagesCursor(Context context) {
        // Add local images, in descending order of date taken
        String[] projection = new String[]{
                MediaStore.Images.ImageColumns._ID,
                MediaStore.Images.ImageColumns.DATA,
                MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
                MediaStore.Images.ImageColumns.DATE_TAKEN,
                MediaStore.Images.ImageColumns.MIME_TYPE
        };
        ContentResolver resolver = context.getContentResolver();

        final Cursor cursor = resolver
                .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null,
                        null, MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC");

        return cursor;
    }

}
