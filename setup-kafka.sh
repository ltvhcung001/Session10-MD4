#!/bin/bash
# Create topics
docker exec -it kafka-pharmacy /opt/kafka/bin/kafka-topics.sh --create --topic medicine-stock-events --partitions 3 --replication-factor 1 --bootstrap-server localhost:9092
docker exec -it kafka-pharmacy /opt/kafka/bin/kafka-topics.sh --create --topic medicine-price-updates --partitions 1 --replication-factor 1 --bootstrap-server localhost:9092
docker exec -it kafka-pharmacy /opt/kafka/bin/kafka-topics.sh --create --topic pharmacy-notifications --partitions 2 --replication-factor 1 --bootstrap-server localhost:9092

# List topics
docker exec -it kafka-pharmacy /opt/kafka/bin/kafka-topics.sh --list --bootstrap-server localhost:9092

# Describe topics
docker exec -it kafka-pharmacy /opt/kafka/bin/kafka-topics.sh --describe --topic medicine-stock-events --bootstrap-server localhost:9092
docker exec -it kafka-pharmacy /opt/kafka/bin/kafka-topics.sh --describe --topic medicine-price-updates --bootstrap-server localhost:9092
docker exec -it kafka-pharmacy /opt/kafka/bin/kafka-topics.sh --describe --topic pharmacy-notifications --bootstrap-server localhost:9092
