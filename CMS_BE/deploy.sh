#!/bin/bash

SERVICES=("auth-service" "config-server" "customer-service" "discovery-server" "gateway-service" "order-service" "payment-service" "product-service" "user-service")

for SERVICE in "${SERVICES[@]}"; do
  echo "Building and pushing $SERVICE..."
  docker build -t ptho1504/$SERVICE ./$SERVICE
  docker tag ptho1504/$SERVICE ptho1504/$SERVICE:latest
  docker push ptho1504/$SERVICE
done
