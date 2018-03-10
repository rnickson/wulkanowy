package io.github.wulkanowy.data.db.dao.entities;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.OrderBy;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;

@Entity(
        nameInDb = "Days",
        active = true,
        indexes = {@Index(value = "userId,weekId,date", unique = true)}
)
public class Day {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "USER_ID")
    private Long userId;

    @Property(nameInDb = "WEEK_ID")
    private Long weekId;

    @Property(nameInDb = "DATE")
    private String date = "";

    @Property(nameInDb = "DAY_NAME")
    private String dayName = "";

    @Property(nameInDb = "IS_FREE_DAY")
    private boolean isFreeDay = false;

    @Property(nameInDb = "FREE_DAY_NAME")
    private String freeDayName = "";

    @ToMany(referencedJoinProperty = "dayId")
    private List<TimetableLesson> timetableLessons;

    @ToMany(referencedJoinProperty = "dayId")
    @OrderBy("number ASC")
    private List<AttendanceLesson> attendanceLessons;

    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /**
     * Used for active entity operations.
     */
    @Generated(hash = 312167767)
    private transient DayDao myDao;

    @Generated(hash = 723729681)
    public Day(Long id, Long userId, Long weekId, String date, String dayName,
            boolean isFreeDay, String freeDayName) {
        this.id = id;
        this.userId = userId;
        this.weekId = weekId;
        this.date = date;
        this.dayName = dayName;
        this.isFreeDay = isFreeDay;
        this.freeDayName = freeDayName;
    }

    @Generated(hash = 866989762)
    public Day() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getWeekId() {
        return weekId;
    }

    public Day setWeekId(Long weekId) {
        this.weekId = weekId;
        return this;
    }

    public Day setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public String getDate() {
        return date;
    }

    public Day setDate(String date) {
        this.date = date;
        return this;
    }

    public String getDayName() {
        return dayName;
    }

    public Day setDayName(String dayName) {
        this.dayName = dayName;
        return this;
    }

    public boolean getIsFreeDay() {
        return this.isFreeDay;
    }

    public Day setIsFreeDay(boolean isFreeDay) {
        this.isFreeDay = isFreeDay;
        return this;
    }

    public String getFreeDayName() {
        return freeDayName;
    }

    public Day setFreeDayName(String freeDayName) {
        this.freeDayName = freeDayName;
        return this;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 218588195)
    public List<TimetableLesson> getTimetableLessons() {
        if (timetableLessons == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TimetableLessonDao targetDao = daoSession.getTimetableLessonDao();
            List<TimetableLesson> timetableLessonsNew = targetDao
                    ._queryDay_TimetableLessons(id);
            synchronized (this) {
                if (timetableLessons == null) {
                    timetableLessons = timetableLessonsNew;
                }
            }
        }
        return timetableLessons;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1687683740)
    public synchronized void resetTimetableLessons() {
        timetableLessons = null;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1166820581)
    public List<AttendanceLesson> getAttendanceLessons() {
        if (attendanceLessons == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            AttendanceLessonDao targetDao = daoSession.getAttendanceLessonDao();
            List<AttendanceLesson> attendanceLessonsNew = targetDao
                    ._queryDay_AttendanceLessons(id);
            synchronized (this) {
                if (attendanceLessons == null) {
                    attendanceLessons = attendanceLessonsNew;
                }
            }
        }
        return attendanceLessons;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1343075564)
    public synchronized void resetAttendanceLessons() {
        attendanceLessons = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1409317752)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getDayDao() : null;
    }
}