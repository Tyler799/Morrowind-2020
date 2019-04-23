@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  MTE-Updater startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..
set JAVA_VERSION=18

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto init

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto checkJavaVersion

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:checkJavaVersion
@REM Check if an appropriate version of Java is installed
if not defined JAVA_VERSION goto init

echo Checking your Java version...
PATH %PATH%;%JAVA_HOME%\bin\
for /f tokens^=2-5^ delims^=.-_^" %%j in ('java -fullversion 2^>^&1') do set "jver=%%j%%k"
if %jver% lss %JAVA_VERSION% (
    echo.
	echo ERROR: Incorrect version of Java ^(%jver:~0,1%.%jver:~-1%^)
	echo.
	echo You do not have the correct version of Java installed
	echo Please install Java SE Runtime Environment 8u202 ^(Java 8^)
	echo which you can download from the official Java website
	echo.
	echo The URL has been copied to your clipboard
	echo just paste it into your browser search bar and press enter
	echo https://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html| clip

	goto fail
)
goto init

:init
@rem Get command-line arguments, handling Windows variants

if not "%OS%" == "Windows_NT" goto win9xME_args

:win9xME_args
@rem Slurp the command line arguments.
set CMD_LINE_ARGS=
set _SKIP=2

:win9xME_args_slurp
if "x%~1" == "x" goto execute

set CMD_LINE_ARGS=%*

:execute
@rem Add default JVM options here. You can also use JAVA_OPTS and MTE_UPDATER_OPTS to pass JVM options to this script.
set APPPATH=MTE-Updater.jar
set "MTE_UPDATER_OPTS=-Dprogram.name=%APPPATH%"

@rem Execute MTEUpdater
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %MTE_UPDATER_OPTS% -jar "%APPPATH%" --launch %CMD_LINE_ARGS%

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable MTE_UPDATER_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%MTE_UPDATER_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega