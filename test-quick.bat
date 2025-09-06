@echo off
echo ========================================
echo    TESTE RÁPIDO - ECOMMERCE
echo ========================================
echo.

echo [1/2] Compilando projeto...
call mvn compile -q
if %errorlevel% neq 0 (
    echo ❌ Erro na compilação!
    pause
    exit /b 1
)
echo ✅ Compilação concluída!

echo.
echo [2/2] Executando teste final...
call mvn test -Dtest=FinalTest -q
if %errorlevel% neq 0 (
    echo ❌ Teste rápido falhou!
    pause
    exit /b 1
)
echo ✅ Teste rápido passou!

echo.
echo ========================================
echo    TESTE RÁPIDO CONCLUÍDO COM SUCESSO!
echo ========================================
pause
