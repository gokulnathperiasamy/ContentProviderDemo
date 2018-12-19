package com.kpgn.common;

public class ApplicationConstant {

    public static final String PROVIDER_NAME = "com.kpgn.appone.CustomContentProvider";
    public static final String PROVIDER_PATH = "email";
    public static final String PROVIDER_URL = "content://" + PROVIDER_NAME + "/" + PROVIDER_PATH;
    public static final String URI_TYPE = "vnd.android.cursor.dir" + "/" + PROVIDER_PATH;

    public static final String DATABASE_NAME = "SharedEmailDb";
    public static final String TABLE_NAME = "EmailIDs";
    public static final int DATABASE_VERSION = 1;

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_EMAIL = "email";
    public static final int URI_CODE = 1;

}
