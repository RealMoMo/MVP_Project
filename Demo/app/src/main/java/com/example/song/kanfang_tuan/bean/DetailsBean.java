package com.example.song.kanfang_tuan.bean;

/**
 * Created by Administrator on 2016/12/19 0019.
 */

public class DetailsBean {
//    "status": 0,
//            "ctime": "2016-12-05T21:42:32",
//            "hate_count": 0,
//            "data_id": 22455797,
//            "content": "B来的。",
//            "like_count": 1,
//            "user": {
//        "username": "再挣一个亿",
//                "qq_uid": "zhuzishow",
//                "profile_image": "http://wimg.spriteapp.cn/profile/large/2015/06/11/55786a970fae3_mini.jpg",
//                "weibo_uid": "",
//                "personal_page": "http://t.qq.com/zhuzishow/",
//                "room_name": "",
//                "room_role": "",
//                "total_cmt_like_count": "172",
//                "is_vip": false,
//                "room_url": "",
//                "qzone_uid": "",
//                "sex": "m",
//                "id": 3228913,
//                "room_icon": ""
//    },
//            "precmts": [],
//            "type": "text",
//            "id": 70189405


    String content;
    String username;
    String profile_image;
    String total_cmt_like_count;
    boolean is_vip;
    String sex;

    public DetailsBean(String content, String username, String profile_image, String total_cmt_like_count, boolean is_vip, String sex) {
        this.content = content;
        this.username = username;
        this.profile_image = profile_image;
        this.total_cmt_like_count = total_cmt_like_count;
        this.is_vip = is_vip;
        this.sex = sex;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getTotal_cmt_like_count() {
        return total_cmt_like_count;
    }

    public void setTotal_cmt_like_count(String total_cmt_like_count) {
        this.total_cmt_like_count = total_cmt_like_count;
    }

    public boolean is_vip() {
        return is_vip;
    }

    public void setIs_vip(boolean is_vip) {
        this.is_vip = is_vip;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
