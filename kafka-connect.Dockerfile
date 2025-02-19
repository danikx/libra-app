FROM confluentinc/cp-kafka-connect:7.4.0

USER root

# Install confluent-hub client with proper directory structure
RUN mkdir -p /usr/share/confluent-hub && \
    curl -O https://client.hub.confluent.io/confluent-hub-client-latest.tar.gz && \
    tar -xf confluent-hub-client-latest.tar.gz -C /usr/share/confluent-hub && \
    rm confluent-hub-client-latest.tar.gz && \
    ln -s /usr/share/confluent-hub/bin/confluent-hub /usr/local/bin/confluent-hub && \
    chmod +x /usr/local/bin/confluent-hub

# Now install the connector with specific version
RUN confluent-hub install --no-prompt debezium/debezium-connector-postgresql:1.9.7

# Create necessary directories
RUN mkdir -p /etc/kafka/connect/connectors
# Copy connector configs
COPY connect/connectors/ /etc/kafka/connect/connectors/
# Add script to automatically register connectors
COPY connect/register-connectors.sh /etc/kafka/connect/
RUN chmod +x /etc/kafka/connect/register-connectors.sh

# Switch back to non-root user
USER appuser
CMD ["/etc/kafka/connect/register-connectors.sh"]