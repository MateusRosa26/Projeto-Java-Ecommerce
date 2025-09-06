#!/bin/bash

echo "========================================"
echo "   EXECUTANDO TESTES JUNIT - ECOMMERCE"
echo "========================================"
echo

echo "[1/3] Compilando projeto..."
mvn compile -q
if [ $? -ne 0 ]; then
    echo "❌ Erro na compilação!"
    exit 1
fi
echo "✅ Compilação concluída!"

echo
echo "[2/3] Compilando testes..."
mvn test-compile -q
if [ $? -ne 0 ]; then
    echo "❌ Erro na compilação dos testes!"
    exit 1
fi
echo "✅ Compilação dos testes concluída!"

echo
echo "[3/3] Executando testes..."
mvn test
if [ $? -ne 0 ]; then
    echo "❌ Alguns testes falharam!"
    exit 1
fi
echo "✅ Todos os testes passaram!"

echo
echo "========================================"
echo "   TESTES CONCLUÍDOS COM SUCESSO!"
echo "========================================"
