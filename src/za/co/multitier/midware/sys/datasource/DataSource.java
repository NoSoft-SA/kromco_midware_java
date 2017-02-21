/*
 * DataSource.java
 *
 * Created on January 25, 2007, 11:02 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package za.co.multitier.midware.sys.datasource;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import com.ibatis.common.resources.Resources;
import za.co.multitier.midware.sys.appservices.MyClassPath;

import java.io.Reader;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 *
 * @author Administrator
 */
public class DataSource {

    private static final SqlMapClient sqlMap;

    static {
        try {

            //TODO uncomment
           MyClassPath.addFile("/opt/jmt/midware_config");
            //MyClassPath.addFile("/home/hans/projects/midware_env/midware_config");
            String resource = "map_files/config.xml";
            Reader reader = null;

            reader = Resources.getResourceAsReader(resource);

            sqlMap = SqlMapClientBuilder.buildSqlMapClient(reader);

        } catch (Exception e) {

            System.out.println(e);
            throw new RuntimeException("Error initializing MyAppSqlConfig class,. Cause: " + e);


        }

    }

    public synchronized static SqlMapClient getSqlMapInstance() {
        return sqlMap;
    }

    public static java.util.Date toDate(java.sql.Timestamp timestamp) {
        long milliseconds = timestamp.getTime() + (timestamp.getNanos() / 1000000);
        return new java.util.Date(milliseconds);
    }

    public static String toFriendlyDate(java.sql.Timestamp timestamp)
    {
         java.util.Date date = toDate(timestamp);
         SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");

         String friendly_date = formatter.format(date);
         return friendly_date;
    }
}

