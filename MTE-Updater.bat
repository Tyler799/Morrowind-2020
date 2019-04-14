@echo off
echo.
REM Try to update MWSE first
if exist MWSE-Update.exe (
	echo Updating MWSE build...
	start /wait MWSE-Update.exe
)
	java -jar MTE-Updater.jar
Pause