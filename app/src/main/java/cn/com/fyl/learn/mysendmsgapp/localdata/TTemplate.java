package cn.com.fyl.learn.mysendmsgapp.localdata;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by Administrator on 2019/8/2 0002.
 */

@Entity
public class TTemplate {

    private String time;

    @Generated(hash = 412665580)
    public TTemplate() {
    }

    @Generated(hash = 1341274102)
    public TTemplate(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
