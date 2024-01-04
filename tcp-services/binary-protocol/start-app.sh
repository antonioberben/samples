#!/bin/sh

# Get the TLS environment variable
export TLS=${TLS:-}

export JAR_PATH=${JAR_PATH:-binary-protocol.jar}
export TRUSTSTORE_PATH=${TRUSTSTORE_PATH:-truststore.jks}
export CERTIFICATE_PATH=${CERTIFICATE_PATH:-server-cert.pem}
export TRUSTSTORE_PASSWORD=${TRUSTSTORE_PASSWORD:-mypassword}
export TRUSTSTORE_ALIAS=${TRUSTSTORE_ALIAS:-myalias}

if [ "$TLS" = "enabled" ]; then
  echo "Truststore path: $TRUSTSTORE_PATH"
  # Create the truststore
  keytool -import -alias $TRUSTSTORE_ALIAS -file $CERTIFICATE_PATH -keystore $TRUSTSTORE_PATH -storepass $TRUSTSTORE_PASSWORD -noprompt

  # Start the client with SSL/TLS
  JAVA_OPTS="-Djavax.net.ssl.trustStore=$TRUSTSTORE_PATH -Djavax.net.ssl.trustStorePassword=$TRUSTSTORE_PASSWORD"
  java $JAVA_OPTS -jar $JAR_PATH
else
  # Start the client without SSL/TLS
  java -jar $JAR_PATH
fi