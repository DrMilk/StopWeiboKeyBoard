package treememory.com.treememory.OnlineData;

import android.os.Parcelable;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/2/8.
 */

public class WuReward extends BmobObject{
    private boolean response_status;
    private String keyword;
    private String rcontext;
    private String response_id;
    private String release_id;
    private Integer rmoney;
    private String expire_date;
    private Integer photograph;

    public WuReward(boolean response_status, String keyword, String rcontext, String response_id, String release_id, Integer rmoney, String expire_date, Integer photograph) {
        this.response_status = response_status;
        this.keyword = keyword;
        this.rcontext = rcontext;
        this.response_id = response_id;
        this.release_id = release_id;
        this.rmoney = rmoney;
        this.expire_date = expire_date;
        this.photograph = photograph;
    }

    public boolean isResponse_status() {
        return response_status;
    }

    public void setResponse_status(boolean response_status) {
        this.response_status = response_status;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getRcontext() {
        return rcontext;
    }

    public void setRcontext(String rcontext) {
        this.rcontext = rcontext;
    }

    public String getResponse_id() {
        return response_id;
    }

    public void setResponse_id(String response_id) {
        this.response_id = response_id;
    }

    public String getRelease_id() {
        return release_id;
    }

    public void setRelease_id(String release_id) {
        this.release_id = release_id;
    }

    public Integer getRmoney() {
        return rmoney;
    }

    public void setRmoney(Integer rmoney) {
        this.rmoney = rmoney;
    }

    public String getExpire_date() {
        return expire_date;
    }

    public void setExpire_date(String expire_date) {
        this.expire_date = expire_date;
    }

    public Integer getPhotograph() {
        return photograph;
    }

    public void setPhotograph(Integer photograph) {
        this.photograph = photograph;
    }

}
