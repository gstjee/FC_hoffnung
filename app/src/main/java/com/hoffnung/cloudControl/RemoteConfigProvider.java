/*
 * Copyright (C) 2026 gstjee
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package com.hoffnung.cloudControl;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class RemoteConfigProvider extends ContentProvider {

    private static final String TAG = "gurun:hoffnung";
    private static final String AUTHORITY = "com.hoffnung.cloudControl.RemoteConfigProvider";

    private static final int ALL = 1;
    private static final int SINGLE = 2;
    private static final int PREFIX = 3;

    private static final UriMatcher MATCHER;

    static {

        MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

        MATCHER.addURI(AUTHORITY,
                "config",
                ALL);

        MATCHER.addURI(AUTHORITY,
                "config/*",
                SINGLE);

        MATCHER.addURI(AUTHORITY,
                "config/pre/*",
                PREFIX);
    }

    private final Map<String,String> configs = new HashMap<>();

    @Override
    public boolean onCreate() {

        configs.put("os_feature_a", "true");
        configs.put("os_feature_b", "false");
        configs.put("os_tpms_rc_pkg_whitelist",
                "com.android.shell,root");

        configs.put("test_key", "dummy");

        Log.d(TAG,"com.hoffnung by gstjee");
        Log.d(TAG,
                "RemoteConfigProvider initialized. authority="
                        + AUTHORITY
                        + " entries="
                        + configs.size());

        return true;
    }

    @Override
    public Cursor query(
            Uri uri,
            String[] projection,
            String selection,
            String[] selectionArgs,
            String sortOrder) {

        String caller = getCallingPackage();
        Log.d(TAG, "query uri=" + uri + " caller=" + caller);

        MatrixCursor cursor =
                new MatrixCursor(
                        new String[]{
                                "key",
                                "value"
                        });

        int match = MATCHER.match(uri);
        Log.d(TAG, "match=" + match);
        switch (match) {

            case ALL:

                for (Map.Entry<String,String> e :
                        configs.entrySet()) {

                    cursor.addRow(
                            new Object[]{
                                    e.getKey(),
                                    e.getValue()
                            });
                }

                break;

            case SINGLE:

                String key =
                        uri.getLastPathSegment();

                if (configs.containsKey(key)) {

                    cursor.addRow(
                            new Object[]{
                                    key,
                                    configs.get(key)
                            });
                }

                break;

            case PREFIX:

                String prefix = uri.getLastPathSegment();

                for (Map.Entry<String,String> e :
                        configs.entrySet()) {

                    if (e.getKey().startsWith(prefix)) {

                        cursor.addRow(
                                new Object[]{
                                        e.getKey(),
                                        e.getValue()
                                });
                    }
                }

                break;
        }

        Bundle extras = new Bundle();
        extras.putString("version", "1");
        cursor.setExtras(extras);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(
            Uri uri,
            String selection,
            String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(
            Uri uri,
            ContentValues values,
            String selection,
            String[] selectionArgs) {
        return 0;
    }
}