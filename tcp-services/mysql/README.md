

```bash
docker run -it --name mysql --rm -e MYSQL_ROOT_PASSWORD=mypassword -e MYSQL_USER=myuser -e MYSQL_PASSWORD=myuserpassword -e MYSQL_DATABASE=mydatabase -p 3306:3306 -v $PWD/my.cnf:/etc/mysql/my.cnf -v $PWD/init.sql:/docker-entrypoint-initdb.d/init.sql mysql:5.6
```

OR

```bash
docker run -it --name mysql --rm -e MYSQL_ROOT_PASSWORD=mypassword -e MYSQL_USER=myuser -e MYSQL_PASSWORD=myuserpassword -e MYSQL_DATABASE=mydatabase -p 3306:3306 -v $PWD/init.sql:/docker-entrypoint-initdb.d/init.sql mysql:5.6
```

docker exec -it mysql mysql -umyuser -pmyuserpassword -e "USE mydatabase; SELECT * FROM demo;"

```bash
docker build -t antonioberben/java-mysql-demo:0.9.0 .

docker run --name demo -it --rm -e DB_HOST=172.17.0.1 -e DB_PORT=3306 -e DB_NAME=mydatabase -e DB_USER=myuser -e DB_PASSWORD=myuserpassword antonioberben/java-mysql-demo:0.9.0

# OR
docker run --name demo -it --rm -e DB_HOST=172.17.0.1 -e DB_PORT=3306 -e DB_NAME=mydatabase -e DB_USER=myuser -e DB_PASSWORD=myuserpassword -v $PWD/server-cert.pem:/app/server-cert.pem antonioberben/java-mysql-demo:0.9.0

kubectl create configmap mysql-config --from-file=init.sql -n demo
kubectl create configmap mysql-config --from-file=init.sql --from-file=my.cnf -n demo-tls
kubectl create secret generic mysql-certs --from-file=ca.pem --from-file=server-cert.pem --from-file=server-key.pem -n demo-tls
```

Run in local:
```bash
mvn clean install
DB_HOST=127.0.0.1 DB_PORT=3306 DB_NAME=mydatabase DB_USER=myuser DB_PASSWORD=myuserpassword JAR_PATH=target/binary-protocol-1.0-SNAPSHOT.jar ./start-app.sh
```

```bash
openssl genrsa 2048 > ca-key.pem
openssl req -new -x509 -nodes -days 3600 -key ca-key.pem -out ca.pem -subj "/C=US/ST=YourState/L=YourCity/O=YourOrganization/CN=*"
openssl req -newkey rsa:2048 -days 3600 -nodes -keyout server-key.pem -out server-req.pem -subj "/C=US/ST=YourState/L=YourCity/O=YourOrganization/CN=*"
openssl rsa -in server-key.pem -out server-key.pem
openssl x509 -req -in server-req.pem -days 3600 -CA ca.pem -CAkey ca-key.pem -set_serial 01 -out server-cert.pem
```


```bash
istioctl install -y --set profile=ambient --set "components.ingressGateways[0].enabled=true" --set "components.ingressGateways[0].name=istio-ingressgateway" --set meshConfig.accessLogFile=/dev/stdout


kubectl sniff $(kubectl get pods -l app=helloworld -o=jsonpath={.items..metadata.name} -n demo) -n demo -p

kubectl sniff $(kubectl get pods -l app=helloworld -o=jsonpath={.items..metadata.name} -n demo-tls) -n demo-tls -p
```