#!/bin/bash

hsqldb_home=./hsqldb
rc_file=auth.rc
urlid=Hagi
sql_file=animaux.sql
sql_file2=couleurs.sql
sql_file3=metiers.sql
sql_file4=pays.sql
sql_file5=prenoms.sql
sql_file6=sports.sql
sql_file7=villes.sql
sql_file8=vegetaux.sql

java -cp "$hsqldb_home/lib/sqltool.jar" org.hsqldb.cmdline.SqlTool --rcFile $rc_file $urlid $sql_file

java -cp "$hsqldb_home/lib/sqltool.jar" org.hsqldb.cmdline.SqlTool --rcFile $rc_file $urlid $sql_file2

java -cp "$hsqldb_home/lib/sqltool.jar" org.hsqldb.cmdline.SqlTool --rcFile $rc_file $urlid $sql_file3

java -cp "$hsqldb_home/lib/sqltool.jar" org.hsqldb.cmdline.SqlTool --rcFile $rc_file $urlid $sql_file4

java -cp "$hsqldb_home/lib/sqltool.jar" org.hsqldb.cmdline.SqlTool --rcFile $rc_file $urlid $sql_file5

java -cp "$hsqldb_home/lib/sqltool.jar" org.hsqldb.cmdline.SqlTool --rcFile $rc_file $urlid $sql_file6

java -cp "$hsqldb_home/lib/sqltool.jar" org.hsqldb.cmdline.SqlTool --rcFile $rc_file $urlid $sql_file7

java -cp "$hsqldb_home/lib/sqltool.jar" org.hsqldb.cmdline.SqlTool --rcFile $rc_file $urlid $sql_file8