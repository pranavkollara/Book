@echo off
REM Docker Management Script for Book Management Microservices (Windows)
REM Usage: docker-manage.bat [command]

setlocal enabledelayedexpansion

REM Colors don't work well in batch, so we'll use text indicators
set "SUCCESS=[OK]"
set "ERROR=[ERROR]"
set "INFO=[INFO]"

REM Functions
:usage
echo.
echo Docker Management - Book Management Microservices
echo.
echo Usage: docker-manage.bat [command]
echo.
echo Commands:
echo   up                  - Build and start all services
echo   down                - Stop all services
echo   restart             - Restart all services
echo   logs [service]      - View logs from services
echo   ps                  - Show running containers
echo   rebuild             - Rebuild all images (no cache)
echo   clean               - Remove containers and volumes
echo   health              - Check services health status
echo   shell [service]     - Access a service container shell
echo   mysql               - Access MySQL database
echo.
echo Examples:
echo   docker-manage.bat up
echo   docker-manage.bat logs auth
echo   docker-manage.bat shell book
echo.
goto end

:check_docker
where docker-compose >nul 2>nul
if %errorlevel% neq 0 (
    echo %ERROR% docker-compose is not installed
    exit /b 1
)
echo %SUCCESS% docker-compose is available
goto end

:cmd_up
echo %INFO% Building images and starting services...
call docker-compose up -d
if %errorlevel% neq 0 (
    echo %ERROR% Failed to start services
    exit /b 1
)
echo.
echo %SUCCESS% Services started successfully!
echo.
echo %INFO% Waiting for services to be ready (this may take 30-60 seconds)...
echo.
timeout /t 10 /nobreak
echo.
call docker-compose ps
goto end

:cmd_down
echo %INFO% Stopping all services...
call docker-compose down
echo %SUCCESS% Services stopped
goto end

:cmd_restart
echo %INFO% Restarting services...
call docker-compose restart
echo %SUCCESS% Services restarted
goto end

:cmd_logs
if "%~2"=="" (
    echo %INFO% Showing logs from all services (Ctrl+C to exit)...
    call docker-compose logs -f
) else (
    echo %INFO% Showing logs from %~2 service...
    call docker-compose logs -f %~2
)
goto end

:cmd_ps
call docker-compose ps
goto end

:cmd_rebuild
echo %INFO% Rebuilding all images (this may take several minutes)...
call docker-compose build --no-cache
if %errorlevel% neq 0 (
    echo %ERROR% Failed to rebuild images
    exit /b 1
)
echo %SUCCESS% Images rebuilt successfully
echo.
echo %INFO% Starting services...
call docker-compose up -d
echo %SUCCESS% Services started
goto end

:cmd_clean
echo %ERROR% This will remove all containers and volumes (database data will be lost)
set /p confirm="Are you sure? (y/N): "
if /i "%confirm%"=="y" (
    echo %INFO% Removing containers and volumes...
    call docker-compose down -v
    echo %SUCCESS% Cleanup completed
) else (
    echo %INFO% Cleanup cancelled
)
goto end

:cmd_health
echo %INFO% Checking service health...
echo.
echo MySQL:
docker exec book-mysql mysqladmin ping -h localhost -u root -proot123 >nul 2>&1
if %errorlevel% equ 0 (
    echo %SUCCESS% Ready
) else (
    echo %ERROR% Not ready
)

echo Eureka:
curl -s http://localhost:8761 >nul 2>&1
if %errorlevel% equ 0 (
    echo %SUCCESS% Ready
) else (
    echo %ERROR% Not ready
)

echo Gateway:
curl -s http://localhost:8050/actuator/health >nul 2>&1
if %errorlevel% equ 0 (
    echo %SUCCESS% Ready
) else (
    echo %ERROR% Not ready
)

echo.
echo %INFO% Full docker-compose status:
call docker-compose ps
goto end

:cmd_shell
if "%~2"=="" (
    echo %ERROR% Please specify a service name
    echo Available services: eureka, gateway, auth, book, wishlist, user, post, mysql
    exit /b 1
)

set "service=%~2"
set "container="

if /i "%service%"=="eureka" set "container=eureka-server"
if /i "%service%"=="gateway" set "container=api-gateway"
if /i "%service%"=="auth" set "container=auth-service"
if /i "%service%"=="book" set "container=book-service"
if /i "%service%"=="wishlist" set "container=wishlist-service"
if /i "%service%"=="user" set "container=user-service"
if /i "%service%"=="post" set "container=post-service"
if /i "%service%"=="mysql" set "container=book-mysql"

if "!container!"=="" (
    echo %ERROR% Unknown service: %service%
    exit /b 1
)

echo %INFO% Accessing %service% container shell...
docker exec -it !container! /bin/bash
goto end

:cmd_mysql
echo %INFO% Connecting to MySQL database...
docker exec -it book-mysql mysql -uroot -proot123 project
goto end

REM Main script logic
set "command=%~1"

if "%command%"=="" (
    call :usage
    goto end
)

call :check_docker
if %errorlevel% neq 0 exit /b 1
echo.

if /i "%command%"=="up" call :cmd_up
if /i "%command%"=="down" call :cmd_down
if /i "%command%"=="restart" call :cmd_restart
if /i "%command%"=="logs" call :cmd_logs %*
if /i "%command%"=="ps" call :cmd_ps
if /i "%command%"=="rebuild" call :cmd_rebuild
if /i "%command%"=="clean" call :cmd_clean
if /i "%command%"=="health" call :cmd_health
if /i "%command%"=="shell" call :cmd_shell %*
if /i "%command%"=="mysql" call :cmd_mysql
if /i "%command%"=="help" call :usage
if /i "%command%"=="-h" call :usage
if /i "%command%"=="--help" call :usage

if not /i "%command%"=="up" if not /i "%command%"=="down" if not /i "%command%"=="restart" if not /i "%command%"=="logs" if not /i "%command%"=="ps" if not /i "%command%"=="rebuild" if not /i "%command%"=="clean" if not /i "%command%"=="health" if not /i "%command%"=="shell" if not /i "%command%"=="mysql" if not /i "%command%"=="help" if not /i "%command%"=="-h" if not /i "%command%"=="--help" (
    echo %ERROR% Unknown command: %command%
    echo.
    call :usage
    exit /b 1
)

:end
endlocal
