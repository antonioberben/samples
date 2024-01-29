# Jaeger

```
helm upgrade --install jaeger jaegertracing/jaeger -n jaeger --create-namespace \
-f jaeger-values.yaml --version 0.73.1
```