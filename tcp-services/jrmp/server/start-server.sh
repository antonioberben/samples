#!/bin/sh

set -x

# Get the TLS environment variable
export TLS=${TLS:-}

export KEYSTORE_PASSWORD=${KEYSTORE_PASSWORD:-mypassword}
# export KEYSTORE_PATH=${KEYSTORE_PATH:-keystore.jks}
export KEYSTORE_PATH=${KEYSTORE_PATH:-server.jks}
# export P12_CERT=${P12_CERT:-server.p12}
export KEYSTORE_ALIAS=${KEYSTORE_ALIAS:-myalias}

export RMI_HOST=${RMI_HOST:-localhost}
export RMI_SERVER_PORT=${RMI_SERVER_PORT:-1100}
export RMI_REGISTRY_PORT=${RMI_REGISTRY_PORT:-1099}

if [ "$TLS" = "enabled" ]; then
  echo "Keystore path: $KEYSTORE_PATH"
  # Create the truststore
  # keytool -importkeystore -deststorepass $KEYSTORE_PASSWORD -destkeystore $KEYSTORE_PATH -srckeystore $P12_CERT -srcstoretype PKCS12 -srcstorepass $KEYSTORE_PASSWORD -alias $KEYSTORE_ALIAS -noprompt
  # Start the registry and the server with SSL/TLS
  export JAVA_TOOL_OPTIONS="-Djavax.net.ssl.keyStore=$KEYSTORE_PATH -Djavax.net.ssl.keyStorePassword=$KEYSTORE_PASSWORD -Djava.rmi.server.useLocalHostname=true -Dhttps.protocols=TLSv1.2 -Djavax.net.debug=all"
  # rmiregistry $RMI_REGISTRY_PORT &
  while ! nc -z localhost $RMI_REGISTRY_PORT; do   
    sleep 1
  done
  # JAVA_OPTS="-Djavax.net.ssl.keyStore=$KEYSTORE_PATH -Djavax.net.ssl.keyStorePassword=$KEYSTORE_PASSWORD -Djava.rmi.server.hostname=$RMI_HOST -Djava.rmi.server.useLocalHostname=true -Dhttps.protocols=TLSv1.1,TLSv1.2 -Djavax.net.debug=all"
  java $JAVA_OPTS HelloServer
else
  # Start the registry and the server without SSL/TLS
  rmiregistry $RMI_REGISTRY_PORT &
  while ! nc -z localhost $RMI_REGISTRY_PORT; do   
    sleep 1
  done
  JAVA_OPTS="-Djava.rmi.server.hostname=$RMI_HOST -Djava.rmi.server.useLocalHostname=true"
  java $JAVA_OPTS HelloServer
fi