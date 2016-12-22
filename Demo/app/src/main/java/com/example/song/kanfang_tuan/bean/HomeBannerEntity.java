package com.example.song.kanfang_tuan.bean;

import java.util.List;

/**
 * 首页广告轮播的实体类
 */
public class HomeBannerEntity {


    /**
     * retcode : 0
     * retmsg : 成功
     * data : [{"type":"3","picurl":"http://p.qpic.cn/estate/0/5258accc088d262d9c58bb77bafd2d50.jpg/0","title":"3字头起购天河3-4房","houseid":"170022"},{"type":"3","picurl":"http://p.qpic.cn/estate/0/039a493b123a269cbbb06599f530f16c.jpg/0","title":"购8字头公寓可减4万","houseid":"172725"},{"type":"3","picurl":"http://p.qpic.cn/estate/0/cdcb54da0dea383b16a82713f7016362.jpg/0","title":"黄埔双地铁房总价20万","houseid":"149465"}]
     * ehouse_timestamp : 0
     */

    private int retcode;
    private String retmsg;
    private int ehouse_timestamp;
    private List<DataEntity> data;

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    public void setRetmsg(String retmsg) {
        this.retmsg = retmsg;
    }

    public void setEhouse_timestamp(int ehouse_timestamp) {
        this.ehouse_timestamp = ehouse_timestamp;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public int getRetcode() {
        return retcode;
    }

    public String getRetmsg() {
        return retmsg;
    }

    public int getEhouse_timestamp() {
        return ehouse_timestamp;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * type : 3
         * picurl : http://p.qpic.cn/estate/0/5258accc088d262d9c58bb77bafd2d50.jpg/0
         * title : 3字头起购天河3-4房
         * houseid : 170022
         */

        private String type;
        private String picurl;
        private String title;
        private String houseid;

        public void setType(String type) {
            this.type = type;
        }

        public void setPicurl(String picurl) {
            this.picurl = picurl;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setHouseid(String houseid) {
            this.houseid = houseid;
        }

        public String getType() {
            return type;
        }

        public String getPicurl() {
            return picurl;
        }

        public String getTitle() {
            return title;
        }

        public String getHouseid() {
            return houseid;
        }
    }
}
