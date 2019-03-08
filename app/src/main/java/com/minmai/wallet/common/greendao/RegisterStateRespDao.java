package com.minmai.wallet.common.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.minmai.wallet.moudles.bean.response.RegisterStateResp;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "REGISTER_STATE_RESP".
*/
public class RegisterStateRespDao extends AbstractDao<RegisterStateResp, Long> {

    public static final String TABLENAME = "REGISTER_STATE_RESP";

    /**
     * Properties of entity RegisterStateResp.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "ID");
        public final static Property RegisterState = new Property(1, String.class, "registerState", false, "REGISTER_STATE");
        public final static Property IsOpenDateRepayment = new Property(2, String.class, "isOpenDateRepayment", false, "IS_OPEN_DATE_REPAYMENT");
    }


    public RegisterStateRespDao(DaoConfig config) {
        super(config);
    }
    
    public RegisterStateRespDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"REGISTER_STATE_RESP\" (" + //
                "\"ID\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"REGISTER_STATE\" TEXT," + // 1: registerState
                "\"IS_OPEN_DATE_REPAYMENT\" TEXT);"); // 2: isOpenDateRepayment
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"REGISTER_STATE_RESP\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, RegisterStateResp entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String registerState = entity.getRegisterState();
        if (registerState != null) {
            stmt.bindString(2, registerState);
        }
 
        String isOpenDateRepayment = entity.getIsOpenDateRepayment();
        if (isOpenDateRepayment != null) {
            stmt.bindString(3, isOpenDateRepayment);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, RegisterStateResp entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String registerState = entity.getRegisterState();
        if (registerState != null) {
            stmt.bindString(2, registerState);
        }
 
        String isOpenDateRepayment = entity.getIsOpenDateRepayment();
        if (isOpenDateRepayment != null) {
            stmt.bindString(3, isOpenDateRepayment);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public RegisterStateResp readEntity(Cursor cursor, int offset) {
        RegisterStateResp entity = new RegisterStateResp( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // registerState
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2) // isOpenDateRepayment
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, RegisterStateResp entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setRegisterState(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setIsOpenDateRepayment(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(RegisterStateResp entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(RegisterStateResp entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(RegisterStateResp entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
