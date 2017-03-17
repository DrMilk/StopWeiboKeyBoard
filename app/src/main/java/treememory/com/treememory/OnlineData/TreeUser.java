package treememory.com.treememory.OnlineData;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2016/11/29.
 */

public class TreeUser extends BmobUser{
    private String phonenum;
    private String TreePassword;
    private String firstdata;
    private String sex;
    private Integer age;
    private String treename;
    public String getTreename() {
        return treename;
    }

    public void setTreename(String treename) {
        this.treename = treename;
    }

    public String getTreePassword() {
        return TreePassword;
    }

    public void setTreePassword(String treePassword) {
        TreePassword = treePassword;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }



    public String getFirstdata() {
        return firstdata;
    }

    public void setFirstdata(String firstdata) {
        this.firstdata = firstdata;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
