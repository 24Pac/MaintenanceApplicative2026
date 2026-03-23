@echo off
@REM Shortcut to call Maven from the cached wrapper distribution
set MVN_CMD=%USERPROFILE%\.m2\wrapper\dists\apache-maven-3.6.3-bin\1iopthnavndlasol9gbrbg6bf2\apache-maven-3.6.3\bin\mvn.cmd
call %MVN_CMD% %*
