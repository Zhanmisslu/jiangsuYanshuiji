package com.example.zhan.heathmanage.Main.EvaluteFragment.Beans;

import java.util.ArrayList;
import java.util.List;

public class DataUtil {
    public static List<Video> getVideoListData() {
        List<Video> videoList = new ArrayList<>();
        videoList.add(new Video("微运动课堂（一）",
                174000,
                "http://www.mcartoria.com:8080/Health/video/img/video1.jpg",
                "http://www.mcartoria.com:8080/Health/video/src/video1.mp4"));
        videoList.add(new Video("微运动课堂（二）",
                289000,
                "http://www.mcartoria.com:8080/Health/video/img/video2.jpg",
                "http://www.mcartoria.com:8080/Health/video/src/video2.mp4"));
        videoList.add(new Video("微运动课堂（三）",
                175000,
                "http://www.mcartoria.com:8080/Health/video/img/video3.jpg",
                "http://www.mcartoria.com:8080/Health/video/src/video3.mp4"));
        videoList.add(new Video("微运动课堂（四）",
                155000,
                "http://www.mcartoria.com:8080/Health/video/img/video4.jpg",
                "http://www.mcartoria.com:8080/Health/video/src/video4.mp4"));
        videoList.add(new Video("微运动课堂（五）",
                168000,
                "http://www.mcartoria.com:8080/Health/video/img/video5.jpg",
                "http://www.mcartoria.com:8080/Health/video/src/video5.mp4"));
        videoList.add(new Video("微运动课堂（六）",
                155000,
                "http://www.mcartoria.com:8080/Health/video/img/video6.jpg",
                "http://www.mcartoria.com:8080/Health/video/src/video6.mp4"));
        videoList.add(new Video("微运动课堂（七）",
                146000,
                "http://www.mcartoria.com:8080/Health/video/img/video7.jpg",
                "http://www.mcartoria.com:8080/Health/video/src/video7.mp4"));
        return videoList;
    }
}
