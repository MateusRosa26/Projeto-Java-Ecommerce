@echo off
echo ========================================
echo    EXECUTANDO TESTES JUNIT - ECOMMERCE
echo ========================================
echo.

echo [1/3] Compilando projeto...
call mvn compile -q
if %errorlevel% neq 0 (
    echo ❌ Erro na compilação!
    pause
    exit /b 1
)
echo ✅ Compilação concluída!

echo.
echo [2/3] Compilando testes...
call mvn test-compile -q
if %errorlevel% neq 0 (
    echo ❌ Erro na compilação dos testes!
    pause
    exit /b 1
)
echo ✅ Compilação dos testes concluída!

echo.
echo [3/3] Executando testes...
call mvn test
if %errorlevel% neq 0 (
    echo ❌ Alguns testes falharam!
    pause
    exit /b 1
)
echo ✅ Todos os testes passaram!

echo.
echo ========================================
echo    TESTES CONCLUÍDOS COM SUCESSO!
echo ========================================
pause
