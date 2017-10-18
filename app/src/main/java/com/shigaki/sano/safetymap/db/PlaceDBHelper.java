package com.shigaki.sano.safetymap.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class PlaceDBHelper extends SQLiteOpenHelper {

    // コンストラクタ
    public PlaceDBHelper(Context context) {
        // 任意のデータベースファイル名と、バージョンを指定する
        super(context, "place.db", null, 1);
    }


    /**
     * このデータベースを初めて使用する時に実行される処理
     * テーブルの作成や初期データの投入を行う
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // テーブルを作成。SQLの文法は通常のSQLiteと同様
        db.execSQL(
                "create table place_table ("+"id integer,"+"name text,"+"explanation text,"+"latitude real,"+"longitude real"+")"
        );
        // 必要なら、ここで他のテーブルを作成したり、初期データを挿入したりする
    }


    /**
     * アプリケーションの更新などによって、データベースのバージョンが上がった場合に実行される処理
     * 今回は割愛
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 取りあえず、空実装でよい
    }

}
