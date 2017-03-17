package treememory.com.treememory.OnlineData;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/1/12.
 */

public class WuRemark extends BmobObject {
    private String context;
    private String writename;
    private int greatpeoplo;
    private int badpeople;
    public WuRemark(String context, String writename, int greatpeoplo, int badpeople) {
        this.context = context;
        this.writename = writename;
        this.greatpeoplo = greatpeoplo;
        this.badpeople = badpeople;
    }
    public WuRemark(){}
    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getWritename() {
        return writename;
    }

    public void setWritename(String writename) {
        this.writename = writename;
    }

    public int getGreatpeoplo() {
        return greatpeoplo;
    }

    public void setGreatpeoplo(int greatpeoplo) {
        this.greatpeoplo = greatpeoplo;
    }

    public int getBadpeople() {
        return badpeople;
    }

    public void setBadpeople(int badpeople) {
        this.badpeople = badpeople;
    }



}
