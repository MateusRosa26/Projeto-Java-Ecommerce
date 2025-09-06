#!/bin/bash

echo "========================================"
echo "   TESTE RÁPIDO - ECOMMERCE"
echo "========================================"
echo

echo "[1/2] Compilando projeto..."
mvn compile -q
if [ $? -ne 0 ]; then
    echo "❌ Erro na compilação!"
    exit 1
fi
echo "✅ Compilação concluída!"

echo
echo "[2/2] Executando teste final..."
mvn test -Dtest=FinalTest -q
if [ $? -ne 0 ]; then
    echo "❌ Teste rápido falhou!"
    exit 1
fi
echo "✅ Teste rápido passou!"

echo
echo "========================================"
echo "   TESTE RÁPIDO CONCLUÍDO COM SUCESSO!"
echo "========================================"
