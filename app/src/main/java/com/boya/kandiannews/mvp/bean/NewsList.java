package com.boya.kandiannews.mvp.bean;

import java.io.Serializable;
import java.util.List;

public class NewsList implements Serializable {


    /**
     * Desc : 成功
     * data : [{"img":"","id":1,"value":"新专利显示：苹果移动终端屏幕将更坚固不易破碎"},{"img":"http://n.sinaimg.cn/translate/209/w1000h1609/20180529/I3C7-hcffhsu7072998.jpg, http://n.sinaimg.cn/translate/709/w974h535/20180529/ppR1-hcffhsu7073151.jpg, http://n.sinaimg.cn/translate/528/w975h353/20180529/JfoR-hcffhsu7073256.jpg, http://n.sinaimg.cn/translate/630/w975h455/20180529/XMrz-fzrwiaz6039606.jpg, http://n.sinaimg.cn/translate/579/w977h402/20180529/WyEf-fzrwiaz6039610.jpg","id":2,"value":"如何让别人对你的潜在价值深信不疑？｜首席人才官"},{"img":"","id":3,"value":"滴滴公布整改进展：快车已上线人车不符评价机制"},{"img":"","id":4,"value":"Waymo扩大自动驾驶车队规模：新订6.2万辆克莱斯勒混合动力车"},{"img":"","id":5,"value":"忘记骁龙850吧！高通骁龙1000版Windows 10 ARM笔记本曝光"}]
     * Code : 1
     */

    private String Desc;
    private String Code;
    private List<DataBean> data;

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String Desc) {
        this.Desc = Desc;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public List<NewsList.DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * img :
         * id : 1
         * value : 新专利显示：苹果移动终端屏幕将更坚固不易破碎
         */

        private String img;
        private String id;
        private String value;
        private String count;
        private String from;

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
