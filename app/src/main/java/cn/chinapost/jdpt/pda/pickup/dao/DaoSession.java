package cn.chinapost.jdpt.pda.pickup.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import cn.com.fyl.learn.mysendmsgapp.localdata.TUser;
import cn.com.fyl.learn.mysendmsgapp.localdata.TTemplate;
import cn.com.fyl.learn.mysendmsgapp.localdata.TTempPlateContent;

import cn.chinapost.jdpt.pda.pickup.dao.TUserDao;
import cn.chinapost.jdpt.pda.pickup.dao.TTemplateDao;
import cn.chinapost.jdpt.pda.pickup.dao.TTempPlateContentDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig tUserDaoConfig;
    private final DaoConfig tTemplateDaoConfig;
    private final DaoConfig tTempPlateContentDaoConfig;

    private final TUserDao tUserDao;
    private final TTemplateDao tTemplateDao;
    private final TTempPlateContentDao tTempPlateContentDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        tUserDaoConfig = daoConfigMap.get(TUserDao.class).clone();
        tUserDaoConfig.initIdentityScope(type);

        tTemplateDaoConfig = daoConfigMap.get(TTemplateDao.class).clone();
        tTemplateDaoConfig.initIdentityScope(type);

        tTempPlateContentDaoConfig = daoConfigMap.get(TTempPlateContentDao.class).clone();
        tTempPlateContentDaoConfig.initIdentityScope(type);

        tUserDao = new TUserDao(tUserDaoConfig, this);
        tTemplateDao = new TTemplateDao(tTemplateDaoConfig, this);
        tTempPlateContentDao = new TTempPlateContentDao(tTempPlateContentDaoConfig, this);

        registerDao(TUser.class, tUserDao);
        registerDao(TTemplate.class, tTemplateDao);
        registerDao(TTempPlateContent.class, tTempPlateContentDao);
    }
    
    public void clear() {
        tUserDaoConfig.clearIdentityScope();
        tTemplateDaoConfig.clearIdentityScope();
        tTempPlateContentDaoConfig.clearIdentityScope();
    }

    public TUserDao getTUserDao() {
        return tUserDao;
    }

    public TTemplateDao getTTemplateDao() {
        return tTemplateDao;
    }

    public TTempPlateContentDao getTTempPlateContentDao() {
        return tTempPlateContentDao;
    }

}
