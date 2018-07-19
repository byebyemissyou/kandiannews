package com.boya.kandiannews.mvp.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/23.
 */

public class User implements Serializable {

    /**
     * Desc : 成功
     * data : {"spid_jd":"","dtinsert":"2017-08-23 13:00:29","dtupdate":"2017-08-23 13:00:29","ifatherid":"0","smobile":"18482291630","iuserid":"28","integral":"0","spassword":"","spid_tbk":"mm_125681632_34606525_127746230","pid":"127746230"}
     * Code : 1
     */

    private String Desc;
    private DataBean data;
    private String Code;



    public String getDesc() {
        return Desc;
    }

    public void setDesc(String Desc) {
        this.Desc = Desc;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public class DataBean implements Serializable {
        /**
         * spid_jd :
         * dtinsert : 2017-08-23 13:00:29
         * dtupdate : 2017-08-23 13:00:29
         * ifatherid : 0
         * smobile : 18482291630
         * iuserid : 28
         * integral : 0
         * spassword :
         * spid_tbk : mm_125681632_34606525_127746230
         * pid : 127746230
         */

        private String spid_jd;
        private String dtinsert;
        private String dtupdate;
        private String ifatherid;
        private String smobile;
        private String iuserid;
        private String integral;
        private String spassword;
        private String spid_tbk;
        private String pid;

        private String Login;
        private String Pass;

        private String LoginState;
        private String UserInfo;

        public String getUserInfo() {
            return UserInfo;
        }

        public void setUserInfo(String userInfo) {
            UserInfo = userInfo;
        }

        public String getLoginState() {
            return LoginState;
        }

        public void setLoginState(String loginState) {
            LoginState = loginState;
        }

        public String getLogin() {
            return Login;
        }

        public void setLogin(String login) {
            Login = login;
        }

        public String getPass() {
            return Pass;
        }

        public void setPass(String pass) {
            Pass = pass;
        }

        public String getSpid_jd() {
            return spid_jd;
        }

        public void setSpid_jd(String spid_jd) {
            this.spid_jd = spid_jd;
        }

        public String getDtinsert() {
            return dtinsert;
        }

        public void setDtinsert(String dtinsert) {
            this.dtinsert = dtinsert;
        }

        public String getDtupdate() {
            return dtupdate;
        }

        public void setDtupdate(String dtupdate) {
            this.dtupdate = dtupdate;
        }

        public String getIfatherid() {
            return ifatherid;
        }

        public void setIfatherid(String ifatherid) {
            this.ifatherid = ifatherid;
        }

        public String getSmobile() {
            return smobile;
        }

        public void setSmobile(String smobile) {
            this.smobile = smobile;
        }

        public String getIuserid() {
            return iuserid;
        }

        public void setIuserid(String iuserid) {
            this.iuserid = iuserid;
        }

        public String getIntegral() {
            return integral;
        }

        public void setIntegral(String integral) {
            this.integral = integral;
        }

        public String getSpassword() {
            return spassword;
        }

        public void setSpassword(String spassword) {
            this.spassword = spassword;
        }

        public String getSpid_tbk() {
            return spid_tbk;
        }

        public void setSpid_tbk(String spid_tbk) {
            this.spid_tbk = spid_tbk;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }
    }
}
