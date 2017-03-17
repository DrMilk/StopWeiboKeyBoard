package treememory.com.treememory.OnlineData;

import java.util.ArrayList;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/2/4.
 */

public class TreeAllMore extends BmobObject {
    private ArrayList<String> search_more_list;
    private ArrayList<String> banner_pic_list;
    public TreeAllMore(){}
    public TreeAllMore(ArrayList<String> search_more_list, ArrayList<String> banner_pic_list) {
        this.search_more_list = search_more_list;
        this.banner_pic_list = banner_pic_list;
    }

    public ArrayList<String> getSearch_more_list() {
        return search_more_list;
    }

    public void setSearch_more_list(ArrayList<String> search_more_list) {
        this.search_more_list = search_more_list;
    }

    public ArrayList<String> getBanner_pic_list() {
        return banner_pic_list;
    }

    public void setBanner_pic_list(ArrayList<String> banner_pic_list) {
        this.banner_pic_list = banner_pic_list;
    }
}
