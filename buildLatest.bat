@echo off

REM --------------------------------------------------------
REM VARIABLES - set these according to your environment
REM
set MAVEN_HOME=C:\Maven
set JAVA_HOME=C:\Java
set HG_HOME=C:\Mercurial
set NETLIB_HOME=NetLib
set DIGIAPP_HOME=DigiHdmiApp
REM --------------------------------------------------------

REM - Get latest NetLib and build it
REM -   assumes that you have ..\Netlib fully checked out
cd ..\%NETLIB_HOME%
hg pull update
mvn clean package install

REM - Get latest App code and build it
cd ..\%DIGIAPP_HOME%
hg pull update
mvn clean package install

REM - Make the distribution pkg
cd ..

del dist /s
rmdir dist /s

mkdir dist
mkdir dist\lib
mkdir dist\conf

copy .\%NETLIB_HOME%\target\NetLib*.jar dist\lib\NetLib.jar
copy .\%DIGIAPP_HOME%\target\DigiHdmiApp*.jar dist\lib\DigiHdmiApp.jar
copy .\thirdptylib\*.jar dist\lib\

REM - Copy properties
copy .\%DIGIAPP_HOME%\target\classes\Device.properties dist\conf\Device.properties

REM - Copy run file
copy .\runme.bat dist\runme.bat

