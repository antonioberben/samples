#!/bin/sh

# Get the TLS environment variable
export TLS=${TLS:-}

# export TRUSTSTORE_PATH=${TRUSTSTORE_PATH:-truststore.jks}
export TRUSTSTORE_PATH=${TRUSTSTORE_PATH:-server.jks}
export CERTIFICATE_PATH=${CERTIFICATE_PATH:-server.crt}
export TRUSTSTORE_PASSWORD=${TRUSTSTORE_PASSWORD:-mypassword}
export TRUSTSTORE_ALIAS=${TRUSTSTORE_ALIAS:-myalias}

export RMI_HOST=${RMI_HOST:-localhost}
export RMI_PORT=${RMI_PORT:-1099}

echo "rmi server host: $RMI_HOST"

if [ "$TLS" = "enabled" ]; then
  echo "Truststore path: $TRUSTSTORE_PATH"
  # Create the truststore
  # keytool -import -alias $TRUSTSTORE_ALIAS -file $CERTIFICATE_PATH -keystore $TRUSTSTORE_PATH -storepass $TRUSTSTORE_PASSWORD -noprompt

  # Start the client with SSL/TLS
  JAVA_OPTS="-Djavax.net.ssl.trustStore=$TRUSTSTORE_PATH -Djavax.net.ssl.trustStorePassword=$TRUSTSTORE_PASSWORD -Djava.rmi.server.hostname=$RMI_HOST -Djava.rmi.server.useLocalHostname=true -Dhttps.protocols=TLSv1.2 -Djavax.net.debug=all"
  java $JAVA_OPTS HelloClient
else
  # Start the client without SSL/TLS
  JAVA_OPTS="-Djava.rmi.server.hostname=$RMI_HOST -Djava.rmi.server.useLocalHostname=true"
  # java $JAVA_OPTS HelloClient
fi