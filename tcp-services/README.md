istioctl install -y --set profile=ambient --set "components.ingressGateways[0].enabled=true" --set "components.ingressGateways[0].name=istio-ingressgateway" --set meshConfig.accessLogFile=/dev/stdout


kubectl sniff jrmp-client-7c57dc4ffd-kw6zb -n demo -p

Generate certs:

```bash
export keystore_alias=myalias
export keystore_password=mypassword
openssl genrsa -out server.key 2048
openssl req -new -key server.key -out server.csr -subj '/CN=*'
openssl x509 -req -in server.csr -signkey server.key -out server.crt
openssl pkcs12 -export -in server.crt -inkey server.key -out server.p12 -name $keystore_alias -password pass:$keystore_password
kubectl delete secret server.p12 -n demo-tls || true
kubectl create secret generic server.p12 --from-file=server.p12 -n demo-tls
kubectl create secret generic keystore-password --from-literal=password=$keystore_password -n demo-tls
```



keytool -importkeystore -deststorepass $keystore_password -destkeystore server.keystore.jks -srckeystore server.p12 -srcstoretype PKCS12 -srcstorepass mypassword -alias $keystore_alias

keytool -genkeypair -alias myalias -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore server.jks -validity 3650
keytool -genkeypair -alias myalias -keyalg RSA -keysize 2048 -keystore server.jks -validity 3650
keytool -genkeypair -alias myalias -keystore server.jks -keyalg RSA -validity 365 -keysize 2048 -storepass mypassword -dname "CN=*, OU=*, O=*, L=*, ST=*, C=*" -storepass mypassword -keypass mykeypassword

openssl genrsa -out keystore.key 2048
openssl req -new -key keystore.key -out keystore.csr -subj '/CN=*'

kubectl create secret generic truststore-secret --from-literal=password=my-password
kubectl create secret generic certificate-secret --from-file=certificate.crt=/path/to/certificate.crt


kaf fake-service.yaml -n demo

docker build -t antonioberben/xmlrpc-server:0.9.0 .
docker build -t antonioberben/xmlrpc-client:0.9.0 .

docker run -it --rm --name xmlrpc-server -p 8080:8080 antonioberben/xmlrpc-server:0.9.0
docker run -it --rm -e localhost --name xmlrpc-client antonioberben/xmlrpc-client:0.9.0


# Run in local 

1. Without TLS

Server:

```bash
kill $(lsof -t -i:1099)
kill $(lsof -t -i:1100)
javac *.java
./start-server.sh
```

Client:

```bash
javac *.java
./start-client.sh
```

1. With TLS

Server:

```bash
kill $(lsof -t -i:1099)
kill $(lsof -t -i:1100)
javac *.java
TLS=enabled ./start-server.sh
```

Client:

```bash
javac *.java
TLS=enabled RMI_HOST=127.0.0.1 ./start-client.sh
```


test