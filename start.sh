#!/usr/bin/env bash

/usr/local/glassfish4/bin/asadmin start-domain domain1 && \
/usr/local/glassfish4/bin/asadmin create-jdbc-connection-pool --datasourceclassname org.postgresql.ds.PGSimpleDataSource --restype javax.sql.DataSource --property portNumber=5432:password=$1:user=$2:serverName=$3:databaseName=$4 postgres_pool && \
/usr/local/glassfish4/bin/asadmin create-jdbc-resource --connectionpoolid postgres_pool jdbc/postgresql && \
/usr/local/glassfish4/bin/asadmin -u admin deploy /HackerNews4.war && \
/usr/local/glassfish4/bin/asadmin set configs.config.server-config.network-config.network-listeners.network-listener.http-listener-1.port=80 && \
/usr/local/glassfish4/bin/asadmin stop-domain domain1 && \
/usr/local/glassfish4/bin/asadmin start-domain --verbose