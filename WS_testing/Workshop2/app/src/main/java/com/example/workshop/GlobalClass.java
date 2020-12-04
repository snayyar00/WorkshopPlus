package com.example.workshop;

import android.app.Application;

public class GlobalClass extends Application {
    private String uri;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void backSpaceUri() {
        //Insert code for removing all text up to the most recent "/" symbol.
    }

    public void concatenateToUri(String directoryToConcat) {
        //Insert code for concatenating a directory to the path.
        uri.concat(directoryToConcat);
        return;
    }
}
