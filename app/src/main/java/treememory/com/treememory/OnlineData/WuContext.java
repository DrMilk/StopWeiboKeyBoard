package treememory.com.treememory.OnlineData;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/1/12.
 */

public class WuContext extends BmobObject {
    private int num;
    private String context;
    private String writename;
    private int fakelevel;
    public WuContext(){

    }
    public WuContext(String writename, String context,int num,int fakelevel) {
        this.writename=writename;
        this.context = context;
        this.num = num;
        this.fakelevel=fakelevel;
    }
    public int getFakelevel() {
        return fakelevel;
    }

    public void setFakelevel(int fakelevel) {
        this.fakelevel = fakelevel;
    }

    public String getWritename() {
        return writename;
    }

    public void setWritename(String writename) {
        this.writename = writename;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
