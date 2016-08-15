package com.android4dev.navigationview;

import java.io.IOException;

/**
 * Created by priya on 7/16/2015.
 */
public interface GetUserCallback {
    public abstract void done(Contact returnedContact) throws IOException;
}
