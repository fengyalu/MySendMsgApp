package cn.com.fyl.learn.mysendmsgapp.localdata;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by Administrator on 2019/8/2 0002.
 */

@Entity
public class TUser {

    /**
     * 身份证号
     */
    @Id
    private long id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 手机号
     */
    private String mobilePhone;

    @Generated(hash = 373802051)
    public TUser() {
    }

    @Generated(hash = 1946169871)
    public TUser(long id, String name, String mobilePhone) {
        this.id = id;
        this.name = name;
        this.mobilePhone = mobilePhone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
}
