package com.example.getalldata;

public class Member {

    private String videoName;
    private String videoUri;

    //Class Constructor (Empty)
    private Member () {}

    //Class Constructor (With Parameters)
    public Member(String name, String vidUri) {
        if (name.trim().equals("")) {
            name = "Nameless";
        }
        videoName = name;
        videoUri = vidUri;
    }


    //GET&SET METHODS

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getVideoUri() {
        return videoUri;
    }

    public void setVideoUri(String videoUri) {
        this.videoUri = videoUri;
    }
}
