package pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class User implements Serializable {

    private String id;//用户帐号
    private String password;//密码
    private String name;//姓名
    private String phone;//电话
    private double mater;//贡献数（单位：米）
    private ArrayList<HashMap> map;//贡献的点的集合

    public ArrayList<HashMap> getMap() {
        return map;
    }

    public void setMap(ArrayList<HashMap> map) {
        this.map = map;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    private String verifyCode;//验证码

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getMater() {
        return mater;
    }

    public void setMater(double mater) {
        this.mater = mater;
    }
}
