@echo off
echo.
REM Try to update MWSE first
if exist MWSE-Update.exe (
	echo Updating MWSE build...
	start /wait MWSE-Update.exe
)
REM Check if an appropriate version of Java is installed
echo Checking your Java version...
PATH %PATH%;%JAVA_HOME%\bin\
for /f tokens^=2-5^ delims^=.-_^" %%j in ('java -fullversion 2^>^&1') do set "jver=%%j%%k"
if %jver% lss 18 ( REM Java 1.8
    echo.
	echo ^|========================================^|
	echo ^|= Missing or incorrect version of Java =^|
	echo ^|========================================^|
	echo ------------------------------------------ & echo.
	echo I am sorry to inform you that you do not have the correct version of Java installed, please install Java SE Runtime Environment 8u202. You can get that version from the official Java website. The URL has been copied to your clipboard, just paste it into your browser search bar and press enter.
	echo https://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html| clip
	goto eof
)
REM Check if the updater jar file is present in the root dir
if exist MTE-Updater.jar (
	java -jar MTE-Updater.jar
) else (
	echo.
	echo ERROR: Unable to find "MTE-Updater.jar"
)
:eof
echo.
Pause
