package hectord.tacoma.uw.edu.webserviceslab.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import hectord.tacoma.uw.edu.webserviceslab.model.Course;

/**
 * Created by Hector on 4/27/2016.
 */
public class CourseDB {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "Course.db";
    private static final String COURSE_TABLE = "Course";

    private CourseDBHelper mCourseDBHelper;
    private SQLiteDatabase mSQLiteDatabase;


    public CourseDB(Context context) {
        mCourseDBHelper = new CourseDBHelper(
                context, DB_NAME, null, DB_VERSION);
        mSQLiteDatabase = mCourseDBHelper.getWritableDatabase();
    }



    /**
     * Inserts the course into the local sqlite table. Returns true if successful, false otherwise.
     * @param id ID of class.
     * @param shortDesc Short description of class.
     * @param longDesc Long description of class.
     * @param prereqs Prerequisites for the class.
     * @return true or false
     */
    public boolean insertCourse(String id, String shortDesc, String longDesc, String prereqs) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("shortDesc", shortDesc);
        contentValues.put("longDesc", longDesc);
        contentValues.put("prereqs", prereqs);

        long rowId = mSQLiteDatabase.insert(COURSE_TABLE, null, contentValues);
        return rowId != -1;
    }

    // method to close the database so that the file resource is closed.
    public void closeDB() {
        mSQLiteDatabase.close();
    }


    /**
     * Returns the list of courses from the local Course table.
     * @return list
     */
    public List<Course> getCourses() {

        String[] columns = {
                "id", "shortDesc", "longDesc", "prereqs"
        };

        Cursor c = mSQLiteDatabase.query(
                COURSE_TABLE,  // The table to query
                columns,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        c.moveToFirst();
        List<Course> list = new ArrayList<Course>();
        for (int i=0; i<c.getCount(); i++) {
            String id = c.getString(0);
            String shortDesc = c.getString(1);
            String longDesc = c.getString(2);
            String prereqs = c.getString(3);
            Course course = new Course(id, shortDesc, longDesc, prereqs);
            list.add(course);
            c.moveToNext();
        }

        return list;
    }

    /**
     * Delete all the data from the COURSE_TABLE
     */
    public void deleteCourses() {
        mSQLiteDatabase.delete(COURSE_TABLE, null, null);
    }




    // inner class
    class CourseDBHelper extends SQLiteOpenHelper {

        private static final String CREATE_COURSE_SQL =
                "CREATE TABLE IF NOT EXISTS Course "
                        + "(id TEXT PRIMARY KEY, shortDesc TEXT, longDesc TEXT, prereqs TEXT)";

        private static final String DROP_COURSE_SQL =
                "DROP TABLE IF EXISTS Course";

        public CourseDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_COURSE_SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL(DROP_COURSE_SQL);
            onCreate(sqLiteDatabase);
        }
    }
}
