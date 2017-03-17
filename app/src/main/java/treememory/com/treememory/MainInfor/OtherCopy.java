package treememory.com.treememory.MainInfor;

import java.io.Serializable;
import java.util.ArrayList;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/1/18.
 */
public class OtherCopy extends BmobObject implements Serializable {
    private String id;
    private ArrayList<String> contextid_list;
    private ArrayList<String> remarkid_list;
    private ArrayList<String> rewardid_list;
    public OtherCopy(){
    }
    public OtherCopy(String id, ArrayList<String> context_id,ArrayList<String> remark_id) {
        this.id = id;
        this.contextid_list = context_id;
        this.remarkid_list=remark_id;
    }
    public OtherCopy(String id, ArrayList<String> context_id) {
        this.id = id;
        this.contextid_list = context_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> getContext_id() {
        return contextid_list;
    }

    public void setContext_id(ArrayList<String> context_id) {
        this.contextid_list = context_id;
    }
    public ArrayList<String> getRemarkid_list() {
        return remarkid_list;
    }

    public void setRemarkid_list(ArrayList<String> remarkid_list) {
        this.remarkid_list = remarkid_list;
    }

    public ArrayList<String> getContextid_list() {
        return contextid_list;
    }

    public void setContextid_list(ArrayList<String> contextid_list) {
        this.contextid_list = contextid_list;
    }

    public ArrayList<String> getRewardid_list() {
        return rewardid_list;
    }

    public void setRewardid_list(ArrayList<String> rewardid_list) {
        this.rewardid_list = rewardid_list;
    }
}
