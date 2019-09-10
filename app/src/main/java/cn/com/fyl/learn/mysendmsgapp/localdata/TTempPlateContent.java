package cn.com.fyl.learn.mysendmsgapp.localdata;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2019/8/8 0008.
 */

@Entity
public class TTempPlateContent {
    private String content;

    @Generated(hash = 1625639854)
    public TTempPlateContent() {
    }

    @Generated(hash = 1723387748)
    public TTempPlateContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
