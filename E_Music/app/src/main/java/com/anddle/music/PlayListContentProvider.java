package com.anddle.music;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

//内容提供器

public class PlayListContentProvider extends ContentProvider {

    private static final String SCHEME = "content://";

    private static final String PATH_SONGS = "/songs";

    public static final String AUTHORITY = "com.anddle.anddlemusicprovider";

    public static final Uri CONTENT_SONGS_URI = Uri.parse(SCHEME + AUTHORITY + PATH_SONGS);

    private DBHelper mDBHelper;

    public PlayListContentProvider() {

    }

    @Override
    public boolean onCreate() {
        //创建数据库和升级
        mDBHelper = new DBHelper(getContext());

        return true;
    }

    @Override
    public String getType(Uri uri) {

        return null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        //query方法第一个参数url为表名，projection确定查询的列，selection和selectionArgs参数约束查询那些行，sortOrder参数对结果进行排序
        Cursor cursor = db.query(DBHelper.PLAYLIST_TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);

        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        int count = db.update(DBHelper.PLAYLIST_TABLE_NAME, values, selection, selectionArgs);

        return count;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        int count = db.delete(DBHelper.PLAYLIST_TABLE_NAME, selection, selectionArgs);
        return count;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        Uri result = null;
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        long id = db.insert(DBHelper.PLAYLIST_TABLE_NAME, null, values);
        if(id > 0) {
            result = ContentUris.withAppendedId(CONTENT_SONGS_URI, id);
        }

        return result;

    }

}
