@echo off

:: ports
set web_port=8420
set db_port=9420

:: location of the H2 database files
set dbhome=h:\h2-dbs

:: create dbhome if it doesn't already exist
if not exist %dbhome% mkdir %dbhome%

:: start H2
java.exe -cp h2-1.4.192.jar -Duser.home=%dbhome% -Dh2.baseDir=%dbhome% -Dh2.bindAddress=localhost org.h2.tools.Console -tcp -tcpPort %db_port% -web -webPort %web_port% -tool

if errorlevel 1 pause