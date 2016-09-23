package io.github.malvadeza.sandbox.cursorloadersqlite;

import android.net.Uri;
import android.provider.BaseColumns;

public class DbContract {
    public static final Uri URI = Uri.parse("sqlite://doideradaporra");
    public static class StringEntry implements BaseColumns {
        public static String TABLE_NAME = "string_entry";
        public static String CONTENT = "content";
    }
}
