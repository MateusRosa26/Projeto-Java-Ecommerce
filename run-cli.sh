#!/bin/bash

echo "========================================"
echo "    SISTEMA E-COMMERCE - CLI"
echo "========================================"
echo

echo "Compilando o projeto..."
mvn clean compile

if [ $? -ne 0 ]; then
    echo "Erro na compilação!"
    exit 1
fi

echo
echo "Iniciando a CLI..."
echo "Certifique-se de que a API REST está rodando em http://localhost:8080"
echo

java -cp "target/classes" com.ecommerce.cli.Main
