# RabbitMQ

This project comes from this spring repository https://github.com/spring-guides/gs-messaging-rabbitmq


```bash
helm upgrade --install rmq oci://registry-1.docker.io/bitnamicharts/rabbitmq -n rmq --create-namespace \
--set replicaCount=3
```