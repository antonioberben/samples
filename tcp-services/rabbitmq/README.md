# RabbitMQ

This project comes from this spring repository https://github.com/spring-guides/gs-messaging-rabbitmq


```bash
helm upgrade --install rmq oci://registry-1.docker.io/bitnamicharts/rabbitmq -n rmq --create-namespace \
--set replicaCount=3
```


```bash
CONF_ENV_FILE="/usr/local/etc/rabbitmq/rabbitmq-env.conf" /usr/local/opt/rabbitmq/sbin/rabbitmq-server

/opt/homebrew/sbin/rabbitmqctl enable_feature_flag all
```