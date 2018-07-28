package io.github.wulkanowy.data.db.dao.entities;

import org.greenrobot.greendao.test.AbstractDaoTestLongPk;

public class AttendanceSubjectTest extends AbstractDaoTestLongPk<AttendanceSubjectDao, AttendanceSubject> {

    public AttendanceSubjectTest() {
        super(AttendanceSubjectDao.class);
    }

    @Override
    protected AttendanceSubject createEntity(Long key) {
        AttendanceSubject entity = new AttendanceSubject();
        entity.setId(key);
        return entity;
    }

}
