/**
 * Copyright © 2020 SOLTEKNO.COM
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.thingsboard.server.dao.util;

import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.DriverManager;

public class DaoTestUtil {
    private static final String POSTGRES_DRIVER_CLASS = "org.postgresql.Driver";
    private static final String H2_DRIVER_CLASS = "org.hsqldb.jdbc.JDBCDriver";


    public static SqlDbType getSqlDbType(JdbcTemplate template){
        try {
            String driverName = DriverManager.getDriver(template.getDataSource().getConnection().getMetaData().getURL()).getClass().getName();
            if (POSTGRES_DRIVER_CLASS.equals(driverName)) {
                return SqlDbType.POSTGRES;
            } else if (H2_DRIVER_CLASS.equals(driverName)) {
                return SqlDbType.H2;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
