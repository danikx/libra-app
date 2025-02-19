#!/bin/bash

# Start Kafka Connect in the background
/etc/confluent/docker/run &

# Wait for Kafka Connect API to be available
echo "Waiting for Kafka Connect to start..."
until curl -s -f http://localhost:8083/connectors > /dev/null; do
    echo "Waiting for Kafka Connect API..."
    sleep 2
done

# Register connectors
for connector in /etc/kafka/connect/connectors/*.json; do
    name=$(basename "$connector" .json)
    echo "Creating connector: $name"
    curl -s -X POST -H "Content-Type: application/json" \
         --data "@$connector" \
         http://localhost:8083/connectors
done

# Wait for the background process
wait