/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.ops4j.pax.jdbc.test.mariadb;

import static org.ops4j.pax.exam.CoreOptions.options;

import java.sql.SQLException;
import java.util.Properties;

import javax.inject.Inject;

import org.junit.Rule;
import org.junit.Test;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.util.Filter;
import org.ops4j.pax.jdbc.test.AbstractJdbcTest;
import org.ops4j.pax.jdbc.test.ServerConfiguration;
import org.osgi.service.jdbc.DataSourceFactory;

public class MariaDbDataSourceTest extends AbstractJdbcTest {

    @Rule
    public ServerConfiguration config = new ServerConfiguration("mariadb");

    @Inject
    @Filter("(osgi.jdbc.driver.class=org.mariadb.jdbc.Driver)")
    private DataSourceFactory dsf;

    @Configuration
    public Option[] config() {
        return options(regressionDefaults(), //
            mvnBundle("org.ops4j.pax.jdbc", "pax-jdbc"), //
            mvnBundle("org.mariadb.jdbc", "mariadb-java-client") //
        );
    }

    @Test
    public void createDataSourceAndConnection() throws SQLException {
        Properties props = new Properties();
        props.setProperty(DataSourceFactory.JDBC_URL, config.getUrl());
        props.setProperty(DataSourceFactory.JDBC_USER, config.getUser());
        props.setProperty(DataSourceFactory.JDBC_PASSWORD, config.getPassword());
        dsf.createDataSource(props).getConnection().close();
    }
}
