package com.itheima.simpledemo;

import java.util.ArrayList;
import java.util.List;

public class DataFactory {

    /**
     * 图片String集合
     */
    public static List<String> imgListString() {
        List<String> imageData = new ArrayList<>();
        imageData.add("http://d.hiphotos.baidu.com/image/h%3D200/sign=72b32dc4b719ebc4df787199b227cf79/58ee3d6d55fbb2fb48944ab34b4a20a44723dcd7.jpg");
        imageData.add("http://pic.4j4j.cn/upload/pic/20130815/31e652fe2d.jpg");
        imageData.add("http://pic.4j4j.cn/upload/pic/20130815/5e604404fe.jpg");
        imageData.add("http://pic.4j4j.cn/upload/pic/20130909/681ebf9d64.jpg");
        imageData.add("http://d.hiphotos.baidu.com/image/pic/item/54fbb2fb43166d22dc28839a442309f79052d265.jpg");
        return imageData;
    }

    /**
     * 图片String数组
     */
    public static String[] imgArrayString() {
        String[] imageData = new String[]{
                "http://d.hiphotos.baidu.com/image/h%3D200/sign=72b32dc4b719ebc4df787199b227cf79/58ee3d6d55fbb2fb48944ab34b4a20a44723dcd7.jpg",
                "http://pic.4j4j.cn/upload/pic/20130815/31e652fe2d.jpg",
                "http://pic.4j4j.cn/upload/pic/20130815/5e604404fe.jpg",
                "http://pic.4j4j.cn/upload/pic/20130909/681ebf9d64.jpg",
                "http://d.hiphotos.baidu.com/image/pic/item/54fbb2fb43166d22dc28839a442309f79052d265.jpg",
        };
        return imageData;
    }

    /**
     * 图片Integer集合
     */
    public static List<Integer> imgListInt() {
        List<Integer> imageData = new ArrayList<>();
        imageData.add(R.mipmap.a);
        imageData.add(R.mipmap.b);
        imageData.add(R.mipmap.c);
        imageData.add(R.mipmap.d);
        imageData.add(R.mipmap.e);
        return imageData;
    }

    /**
     * 图片Integer数组
     */
    public static Integer[] imgArrayInt() {
        Integer[] imageData = new Integer[]{
                R.mipmap.a,
                R.mipmap.b,
                R.mipmap.c,
                R.mipmap.d,
                R.mipmap.e
        };
        return imageData;
    }

    /**
     * 标题String集合
     */
    public static List<String> titleListString() {
        List<String> titleData = new ArrayList<>();
        titleData.add("1、在这里等着你");
        titleData.add("2、在你身边");
        titleData.add("3、打电话给你就是想说声“嗨”");
        titleData.add("4、不介意你对我大喊大叫");
        titleData.add("5、期待你总是尽全力");
        return titleData;
    }

    /**
     * 标题String数组
     */
    public static String[] titleArrayString() {
        String[] titleData = new String[]{
                "1、在这里等着你",
                "2、在你身边",
                "3、打电话给你就是想说声“嗨”",
                "4、不介意你对我大喊大叫",
                "5、期待你总是尽全力",
        };
        return titleData;
    }

}
