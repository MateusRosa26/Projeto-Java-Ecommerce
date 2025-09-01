@echo off
echo ========================================
echo    SISTEMA E-COMMERCE - CLI
echo ========================================
echo.

echo Compilando o projeto...
call mvn clean compile

if %ERRORLEVEL% NEQ 0 (
    echo Erro na compilacao!
    pause
    exit /b 1
)

echo.
echo Iniciando a CLI...
echo Certifique-se de que a API REST esta rodando em http://localhost:8080
echo.

java -cp "target/classes" com.ecommerce.cli.Main

pause
