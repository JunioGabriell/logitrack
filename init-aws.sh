#!/bin/bash

echo "Criando recursos AWS no LocalStack..."

awslocal sqs create-queue \
  --queue-name tracking-queue \
  --region us-east-1

awslocal dynamodb create-table \
  --table-name tracking \
  --attribute-definitions AttributeName=id,AttributeType=S \
  --key-schema AttributeName=id,KeyType=HASH \
  --billing-mode PAY_PER_REQUEST \
  --region us-east-1

echo "Recursos criados com sucesso!"