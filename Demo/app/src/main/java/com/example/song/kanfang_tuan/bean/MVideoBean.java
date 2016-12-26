package com.example.song.kanfang_tuan.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/19 0019.
 */

public class MVideoBean{

    private InfoBean info;
    private List<ListBean> list;

    public static MVideoBean objectFromData(String str) {

        return new Gson().fromJson(str, MVideoBean.class);
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class InfoBean {
        private int count;
        private int np;

        public static InfoBean objectFromData(String str) {

            return new Gson().fromJson(str, InfoBean.class);
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getNp() {
            return np;
        }

        public void setNp(int np) {
            this.np = np;
        }
    }

    public static class ListBean implements Parcelable {
        private int status;
        private String comment;
        private String bookmark;
        private String text;
        private String up;
        private String share_url;
        private int down;
        private int forward;
        private UBean u;
        private String passtime;
        private VideoBean video;
        private String type;
        private String id;
        private List<TopCommentsBean> top_comments;
        private List<TagsBean> tags;

        public static ListBean objectFromData(String str) {

            return new Gson().fromJson(str, ListBean.class);
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getBookmark() {
            return bookmark;
        }

        public void setBookmark(String bookmark) {
            this.bookmark = bookmark;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getUp() {
            return up;
        }

        public void setUp(String up) {
            this.up = up;
        }

        public String getShare_url() {
            return share_url;
        }

        public void setShare_url(String share_url) {
            this.share_url = share_url;
        }

        public int getDown() {
            return down;
        }

        public void setDown(int down) {
            this.down = down;
        }

        public int getForward() {
            return forward;
        }

        public void setForward(int forward) {
            this.forward = forward;
        }

        public UBean getU() {
            return u;
        }

        public void setU(UBean u) {
            this.u = u;
        }

        public String getPasstime() {
            return passtime;
        }

        public void setPasstime(String passtime) {
            this.passtime = passtime;
        }

        public VideoBean getVideo() {
            return video;
        }

        public void setVideo(VideoBean video) {
            this.video = video;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<TopCommentsBean> getTop_comments() {
            return top_comments;
        }

        public void setTop_comments(List<TopCommentsBean> top_comments) {
            this.top_comments = top_comments;
        }

        public List<TagsBean> getTags() {
            return tags;
        }

        public void setTags(List<TagsBean> tags) {
            this.tags = tags;
        }

        public static class UBean implements Parcelable {
            private String uid;
            private boolean is_vip;
            private boolean is_v;
            private String room_url;
            private String room_name;
            private String room_role;
            private String room_icon;
            private String name;
            private List<String> header;

            public static UBean objectFromData(String str) {

                return new Gson().fromJson(str, UBean.class);
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public boolean isIs_vip() {
                return is_vip;
            }

            public void setIs_vip(boolean is_vip) {
                this.is_vip = is_vip;
            }

            public boolean isIs_v() {
                return is_v;
            }

            public void setIs_v(boolean is_v) {
                this.is_v = is_v;
            }

            public String getRoom_url() {
                return room_url;
            }

            public void setRoom_url(String room_url) {
                this.room_url = room_url;
            }

            public String getRoom_name() {
                return room_name;
            }

            public void setRoom_name(String room_name) {
                this.room_name = room_name;
            }

            public String getRoom_role() {
                return room_role;
            }

            public void setRoom_role(String room_role) {
                this.room_role = room_role;
            }

            public String getRoom_icon() {
                return room_icon;
            }

            public void setRoom_icon(String room_icon) {
                this.room_icon = room_icon;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<String> getHeader() {
                return header;
            }

            public void setHeader(List<String> header) {
                this.header = header;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.uid);
                dest.writeByte(this.is_vip ? (byte) 1 : (byte) 0);
                dest.writeByte(this.is_v ? (byte) 1 : (byte) 0);
                dest.writeString(this.room_url);
                dest.writeString(this.room_name);
                dest.writeString(this.room_role);
                dest.writeString(this.room_icon);
                dest.writeString(this.name);
                dest.writeStringList(this.header);
            }

            public UBean() {
            }

            protected UBean(Parcel in) {
                this.uid = in.readString();
                this.is_vip = in.readByte() != 0;
                this.is_v = in.readByte() != 0;
                this.room_url = in.readString();
                this.room_name = in.readString();
                this.room_role = in.readString();
                this.room_icon = in.readString();
                this.name = in.readString();
                this.header = in.createStringArrayList();
            }

            public static final Creator<UBean> CREATOR = new Creator<UBean>() {
                @Override
                public UBean createFromParcel(Parcel source) {
                    return new UBean(source);
                }

                @Override
                public UBean[] newArray(int size) {
                    return new UBean[size];
                }
            };
        }

        public static class VideoBean implements Parcelable {
            private int playfcount;
            private int height;
            private int width;
            private int duration;
            private int playcount;
            private List<String> video;
            private List<String> download;
            private List<String> thumbnail;
            private List<String> thumbnail_small;

            public static VideoBean objectFromData(String str) {

                return new Gson().fromJson(str, VideoBean.class);
            }

            public int getPlayfcount() {
                return playfcount;
            }

            public void setPlayfcount(int playfcount) {
                this.playfcount = playfcount;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getDuration() {
                return duration;
            }

            public void setDuration(int duration) {
                this.duration = duration;
            }

            public int getPlaycount() {
                return playcount;
            }

            public void setPlaycount(int playcount) {
                this.playcount = playcount;
            }

            public List<String> getVideo() {
                return video;
            }

            public void setVideo(List<String> video) {
                this.video = video;
            }

            public List<String> getDownload() {
                return download;
            }

            public void setDownload(List<String> download) {
                this.download = download;
            }

            public List<String> getThumbnail() {
                return thumbnail;
            }

            public void setThumbnail(List<String> thumbnail) {
                this.thumbnail = thumbnail;
            }

            public List<String> getThumbnail_small() {
                return thumbnail_small;
            }

            public void setThumbnail_small(List<String> thumbnail_small) {
                this.thumbnail_small = thumbnail_small;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.playfcount);
                dest.writeInt(this.height);
                dest.writeInt(this.width);
                dest.writeInt(this.duration);
                dest.writeInt(this.playcount);
                dest.writeStringList(this.video);
                dest.writeStringList(this.download);
                dest.writeStringList(this.thumbnail);
                dest.writeStringList(this.thumbnail_small);
            }

            public VideoBean() {
            }

            protected VideoBean(Parcel in) {
                this.playfcount = in.readInt();
                this.height = in.readInt();
                this.width = in.readInt();
                this.duration = in.readInt();
                this.playcount = in.readInt();
                this.video = in.createStringArrayList();
                this.download = in.createStringArrayList();
                this.thumbnail = in.createStringArrayList();
                this.thumbnail_small = in.createStringArrayList();
            }

            public static final Creator<VideoBean> CREATOR = new Creator<VideoBean>() {
                @Override
                public VideoBean createFromParcel(Parcel source) {
                    return new VideoBean(source);
                }

                @Override
                public VideoBean[] newArray(int size) {
                    return new VideoBean[size];
                }
            };
        }

        public static class TopCommentsBean implements Parcelable {
            private int voicetime;
            private int status;
            private int hate_count;
            private String cmt_type;
            private int precid;
            private String content;
            private int like_count;
            private UBeanX u;
            private int preuid;
            private String passtime;
            private String voiceuri;
            private int id;

            public static TopCommentsBean objectFromData(String str) {

                return new Gson().fromJson(str, TopCommentsBean.class);
            }

            public int getVoicetime() {
                return voicetime;
            }

            public void setVoicetime(int voicetime) {
                this.voicetime = voicetime;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getHate_count() {
                return hate_count;
            }

            public void setHate_count(int hate_count) {
                this.hate_count = hate_count;
            }

            public String getCmt_type() {
                return cmt_type;
            }

            public void setCmt_type(String cmt_type) {
                this.cmt_type = cmt_type;
            }

            public int getPrecid() {
                return precid;
            }

            public void setPrecid(int precid) {
                this.precid = precid;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getLike_count() {
                return like_count;
            }

            public void setLike_count(int like_count) {
                this.like_count = like_count;
            }

            public UBeanX getU() {
                return u;
            }

            public void setU(UBeanX u) {
                this.u = u;
            }

            public int getPreuid() {
                return preuid;
            }

            public void setPreuid(int preuid) {
                this.preuid = preuid;
            }

            public String getPasstime() {
                return passtime;
            }

            public void setPasstime(String passtime) {
                this.passtime = passtime;
            }

            public String getVoiceuri() {
                return voiceuri;
            }

            public void setVoiceuri(String voiceuri) {
                this.voiceuri = voiceuri;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public static class UBeanX implements Parcelable {
                private String uid;
                private boolean is_vip;
                private String room_url;
                private String sex;
                private String room_name;
                private String room_role;
                private String room_icon;
                private String name;
                private List<String> header;

                public static UBeanX objectFromData(String str) {

                    return new Gson().fromJson(str, UBeanX.class);
                }

                public String getUid() {
                    return uid;
                }

                public void setUid(String uid) {
                    this.uid = uid;
                }

                public boolean isIs_vip() {
                    return is_vip;
                }

                public void setIs_vip(boolean is_vip) {
                    this.is_vip = is_vip;
                }

                public String getRoom_url() {
                    return room_url;
                }

                public void setRoom_url(String room_url) {
                    this.room_url = room_url;
                }

                public String getSex() {
                    return sex;
                }

                public void setSex(String sex) {
                    this.sex = sex;
                }

                public String getRoom_name() {
                    return room_name;
                }

                public void setRoom_name(String room_name) {
                    this.room_name = room_name;
                }

                public String getRoom_role() {
                    return room_role;
                }

                public void setRoom_role(String room_role) {
                    this.room_role = room_role;
                }

                public String getRoom_icon() {
                    return room_icon;
                }

                public void setRoom_icon(String room_icon) {
                    this.room_icon = room_icon;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public List<String> getHeader() {
                    return header;
                }

                public void setHeader(List<String> header) {
                    this.header = header;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.uid);
                    dest.writeByte(this.is_vip ? (byte) 1 : (byte) 0);
                    dest.writeString(this.room_url);
                    dest.writeString(this.sex);
                    dest.writeString(this.room_name);
                    dest.writeString(this.room_role);
                    dest.writeString(this.room_icon);
                    dest.writeString(this.name);
                    dest.writeStringList(this.header);
                }

                public UBeanX() {
                }

                protected UBeanX(Parcel in) {
                    this.uid = in.readString();
                    this.is_vip = in.readByte() != 0;
                    this.room_url = in.readString();
                    this.sex = in.readString();
                    this.room_name = in.readString();
                    this.room_role = in.readString();
                    this.room_icon = in.readString();
                    this.name = in.readString();
                    this.header = in.createStringArrayList();
                }

                public static final Creator<UBeanX> CREATOR = new Creator<UBeanX>() {
                    @Override
                    public UBeanX createFromParcel(Parcel source) {
                        return new UBeanX(source);
                    }

                    @Override
                    public UBeanX[] newArray(int size) {
                        return new UBeanX[size];
                    }
                };
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.voicetime);
                dest.writeInt(this.status);
                dest.writeInt(this.hate_count);
                dest.writeString(this.cmt_type);
                dest.writeInt(this.precid);
                dest.writeString(this.content);
                dest.writeInt(this.like_count);
                dest.writeParcelable(this.u, flags);
                dest.writeInt(this.preuid);
                dest.writeString(this.passtime);
                dest.writeString(this.voiceuri);
                dest.writeInt(this.id);
            }

            public TopCommentsBean() {
            }

            protected TopCommentsBean(Parcel in) {
                this.voicetime = in.readInt();
                this.status = in.readInt();
                this.hate_count = in.readInt();
                this.cmt_type = in.readString();
                this.precid = in.readInt();
                this.content = in.readString();
                this.like_count = in.readInt();
                this.u = in.readParcelable(UBeanX.class.getClassLoader());
                this.preuid = in.readInt();
                this.passtime = in.readString();
                this.voiceuri = in.readString();
                this.id = in.readInt();
            }

            public static final Creator<TopCommentsBean> CREATOR = new Creator<TopCommentsBean>() {
                @Override
                public TopCommentsBean createFromParcel(Parcel source) {
                    return new TopCommentsBean(source);
                }

                @Override
                public TopCommentsBean[] newArray(int size) {
                    return new TopCommentsBean[size];
                }
            };
        }

        public static class TagsBean implements Parcelable {
            private int id;
            private String name;

            public static TagsBean objectFromData(String str) {

                return new Gson().fromJson(str, TagsBean.class);
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.id);
                dest.writeString(this.name);
            }

            public TagsBean() {
            }

            protected TagsBean(Parcel in) {
                this.id = in.readInt();
                this.name = in.readString();
            }

            public static final Creator<TagsBean> CREATOR = new Creator<TagsBean>() {
                @Override
                public TagsBean createFromParcel(Parcel source) {
                    return new TagsBean(source);
                }

                @Override
                public TagsBean[] newArray(int size) {
                    return new TagsBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.status);
            dest.writeString(this.comment);
            dest.writeString(this.bookmark);
            dest.writeString(this.text);
            dest.writeString(this.up);
            dest.writeString(this.share_url);
            dest.writeInt(this.down);
            dest.writeInt(this.forward);
            dest.writeParcelable(this.u, flags);
            dest.writeString(this.passtime);
            dest.writeParcelable(this.video, flags);
            dest.writeString(this.type);
            dest.writeString(this.id);
            dest.writeList(this.top_comments);
            dest.writeList(this.tags);
        }

        public ListBean() {
        }

        protected ListBean(Parcel in) {
            this.status = in.readInt();
            this.comment = in.readString();
            this.bookmark = in.readString();
            this.text = in.readString();
            this.up = in.readString();
            this.share_url = in.readString();
            this.down = in.readInt();
            this.forward = in.readInt();
            this.u = in.readParcelable(UBean.class.getClassLoader());
            this.passtime = in.readString();
            this.video = in.readParcelable(VideoBean.class.getClassLoader());
            this.type = in.readString();
            this.id = in.readString();
            this.top_comments = new ArrayList<TopCommentsBean>();
            in.readList(this.top_comments, TopCommentsBean.class.getClassLoader());
            this.tags = new ArrayList<TagsBean>();
            in.readList(this.tags, TagsBean.class.getClassLoader());
        }

        public static final Parcelable.Creator<ListBean> CREATOR = new Parcelable.Creator<ListBean>() {
            @Override
            public ListBean createFromParcel(Parcel source) {
                return new ListBean(source);
            }

            @Override
            public ListBean[] newArray(int size) {
                return new ListBean[size];
            }
        };
    }
}
