package cn.com.fyl.learn.mysendmsgapp.localdata;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.lang.ref.WeakReference;
import java.util.List;

import cn.chinapost.jdpt.pda.pickup.dao.DaoMaster;
import cn.chinapost.jdpt.pda.pickup.dao.DaoSession;
import cn.chinapost.jdpt.pda.pickup.dao.TTempPlateContentDao;
import cn.chinapost.jdpt.pda.pickup.dao.TTemplateDao;
import cn.chinapost.jdpt.pda.pickup.dao.TUserDao;

/**
 * Created by Administrator on 2019/8/2 0002.
 * @description 本地数据库操作管理(GreenDao数据库)
 *
 */
public class LocalDataDBManager {
    private static final String TAG = "LocalDataDBManager";
    private static LocalDataDBManager instance;
    private WeakReference<Context> mContext;
    public final static String dbName = "pickup.db";
    private DaoMaster.DevOpenHelper openHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private TUserDao tUserDao;
    private TTemplateDao tTemplateDao;
    private TTempPlateContentDao tTempPlateContentDao;


    public LocalDataDBManager(Context context) {
        mContext = new WeakReference<Context>(context);
        openHelper = new DaoMaster.DevOpenHelper(mContext.get(), dbName, null);
        db = openHelper.getReadableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();

        tUserDao = mDaoSession.getTUserDao();
        tTemplateDao = mDaoSession.getTTemplateDao();
        tTempPlateContentDao = mDaoSession.getTTempPlateContentDao();
    }

    public static synchronized LocalDataDBManager getInstance(Context context) {
        if (instance == null)
            instance = new LocalDataDBManager(context);
        return instance;
    }

    /**
     * 保存一条成员的信息
     * @param tUser
     * @return
     */
    public boolean saveOneUser(final TUser tUser) {
        boolean flag = false;
        if (null!=tUser) {
            try {
                mDaoSession.runInTx(new Runnable() {
                    @Override
                    public void run() {
                        tUserDao.insertOrReplace(tUser);
                    }
                });
                flag = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    /**
     * 保存一条短信模板
     * @param tTempPlateContent
     * @return
     */
    public boolean saveOneTemplateContent(final TTempPlateContent tTempPlateContent) {
        boolean flag = false;
        if (null!=tTempPlateContent) {
            try {
                mDaoSession.runInTx(new Runnable() {
                    @Override
                    public void run() {
                        tTempPlateContentDao.insertOrReplace(tTempPlateContent);
                    }
                });
                flag = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    /**
     * 保存一条成员的信息
     * @param tTemplate
     * @return
     */
    public boolean saveOneTemplate(final TTemplate tTemplate) {
        boolean flag = false;
        if (null!=tTemplate) {
            try {
                mDaoSession.runInTx(new Runnable() {
                    @Override
                    public void run() {
                        tTemplateDao.insertOrReplace(tTemplate);
                    }
                });
                flag = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return flag;
    }


    /**
     *保存人员数据信息
     * @param tUserList
     * @return
     */
    public boolean saveUser(final List<TUser> tUserList) {
        boolean flag = false;
        if (tUserList != null && !tUserList.isEmpty()) {
            try {
                mDaoSession.runInTx(new Runnable() {
                    @Override
                    public void run() {
                        for (TUser tUser : tUserList) {
                            tUserDao.insertOrReplace(tUser);
                        }
                    }
                });
                flag = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return flag;
    }


    /**
     * 删除一条数据
     * @param tUser
     * @return
     */
    public boolean deleteUser(TUser tUser) {
        boolean flag = false;
        try {
            //按照指定的id进行删除 delete from tMonitor where _id = ?
            tUserDao.delete(tUser);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    /**
     * 删除TUser表中的全部数据
     */
    public boolean deleteAllUser() {
        boolean flag = false;
        try {
            mDaoSession.runInTx(new Runnable() {
                @Override
                public void run() {
                    tUserDao.deleteAll();
                }
            });
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
 /**
     * 删除Template表中的全部数据
     */
    public boolean deleteAllTemplate() {
        boolean flag = false;
        try {
            mDaoSession.runInTx(new Runnable() {
                @Override
                public void run() {
                    tTemplateDao.deleteAll();
                }
            });
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除Template表中的全部数据
     */
    public boolean deleteAllTemplateContent() {
        boolean flag = false;
        try {
            mDaoSession.runInTx(new Runnable() {
                @Override
                public void run() {
                    tTempPlateContentDao.deleteAll();
                }
            });
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     *查询全部数据
     */
    public List<TUser> queryAllTUser() {
        return tUserDao.loadAll();
    }

    /**
     *查询全部时间数据
     */
    public List<TTemplate> queryAllTTemplate() {
        return tTemplateDao.loadAll();
    }

 /**
     *查询全部短信模板数据
     */
    public List<TTempPlateContent> queryAllTemplateContemt() {
        return tTempPlateContentDao.loadAll();
    }



    /**
     *修改手机号码
     */
    public void updateUser(long id,String mobilePhone) {
        try {
            TUser tUser = tUserDao.queryBuilder()
                    .where(TUserDao.Properties.Id.eq(id)).unique();
            if (tUser != null) {
                tUser.setMobilePhone(mobilePhone);
                tUserDao.update(tUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * 根据身份证号码查询相应信息
     * @param
     * @param id
     * @return
     */
    public TUser queryUser(long id) {
        TUser tUser = tUserDao.queryBuilder().where(TUserDao.Properties.Id.eq(id)).unique();
        if (tUser != null) {
            return tUser;
        } else {
            return null;
        }
    }



    public DaoMaster.DevOpenHelper getOpenHelper() {
        return openHelper;
    }

    public void setOpenHelper(DaoMaster.DevOpenHelper openHelper) {
        this.openHelper = openHelper;
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public void setDb(SQLiteDatabase db) {
        this.db = db;
    }

    public DaoMaster getmDaoMaster() {
        return mDaoMaster;
    }

    public void setmDaoMaster(DaoMaster mDaoMaster) {
        this.mDaoMaster = mDaoMaster;
    }

    public DaoSession getmDaoSession() {
        return mDaoSession;
    }

    public void setmDaoSession(DaoSession mDaoSession) {
        this.mDaoSession = mDaoSession;
    }
}
