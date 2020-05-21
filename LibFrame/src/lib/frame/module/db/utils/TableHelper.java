package lib.frame.module.db.utils;

import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Field;
import java.sql.Blob;

import lib.frame.module.db.annotation.Column;
import lib.frame.module.db.annotation.Id;
import lib.frame.module.db.annotation.Table;
import lib.frame.module.db.annotation.Unique;

public class TableHelper {
    public static <T> void createTablesByClasses(SQLiteDatabase db,
                                                 Class<?>[] clazzs) {
        for (Class clazz : clazzs)
            createTable(db, clazz);
    }

    public static <T> void dropTablesByClasses(SQLiteDatabase db,
                                               Class<?>[] clazzs) {
        for (Class clazz : clazzs)
            dropTable(db, clazz);
    }

    public static <T> void createTable(SQLiteDatabase db, Class<T> clazz) {
        String tableName = "";
        if (clazz.isAnnotationPresent(Table.class)) {
            Table table = (Table) clazz.getAnnotation(Table.class);
            tableName = table.name();
        }

        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS ").append(tableName).append(" (");

        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {

            if (!field.isAnnotationPresent(Column.class)) {
                continue;
            }
            Column column = (Column) field.getAnnotation(Column.class);

            String columnType = "";
            if (column.type().equals(""))
                columnType = getColumnType(field.getType());
            else {
                columnType = column.type();
            }

            sb.append(column.name() + " " + columnType);

            if ((!column.type().equals("")) && (column.length() != 0)) {
                sb.append("(" + column.length() + ")");
            }

            if (((field.isAnnotationPresent(Id.class)) && ((field.getType() == Integer.TYPE) || (field
                    .getType() == Integer.class)))) {
                sb.append(" primary key autoincrement");

            } else if (field.isAnnotationPresent(Id.class)) {
                sb.append(" primary key");
            }

            if (field.isAnnotationPresent(Unique.class)) {
                sb.append(" UNIQUE");
            }
            sb.append(", ");
        }

        sb.delete(sb.length() - 2, sb.length() - 1);
        sb.append(")");

        String sql = sb.toString().trim();
        db.execSQL(sql);
    }

//

    public static <T> void dropTable(SQLiteDatabase db, Class<T> clazz) {
        String tableName = "";
        if (clazz.isAnnotationPresent(Table.class)) {
            Table table = (Table) clazz.getAnnotation(Table.class);
            tableName = table.name();
        }
        String sql = "DROP TABLE IF EXISTS " + tableName;
        db.execSQL(sql);
    }

    private static String getColumnType(Class fieldType) {
        if (String.class == fieldType) {
            return "TEXT";
        }
        if ((Integer.TYPE == fieldType) || (Integer.class == fieldType)) {
            return "INTEGER";
        }
        if ((Long.TYPE == fieldType) || (Long.class == fieldType)) {
            return "BIGINT";
        }
        if ((Float.TYPE == fieldType) || (Float.class == fieldType)) {
            return "FLOAT";
        }
        if ((Short.TYPE == fieldType) || (Short.class == fieldType)) {
            return "INT";
        }
        if ((Double.TYPE == fieldType) || (Double.class == fieldType)) {
            return "DOUBLE";
        }
        if (Blob.class == fieldType) {
            return "BLOB";
        }

        return "TEXT";
    }
}
