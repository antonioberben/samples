istioctl install -y --set profile=ambient --set "components.ingressGateways[0].enabled=true" --set "components.ingressGateways[0].name=istio-ingressgateway" --set meshConfig.accessLogFile=/dev/stdout


helm upgrade --install kubeshark kubeshark/kubeshark -n kubeshark --create-namespace \
--set tap.release.namespace=kubeshark --version 52.0.0

kubectl patch svc kubeshark-front -n kubeshark -p '{"spec": {"type": "LoadBalancer"}}'

kubectl port-forward service/kubeshark-front 8899:80 -n kubeshark  

kaf fake-service.yaml -n demo

docker build -t antonioberben/xmlrpc-server:0.9.0 .
docker build -t antonioberben/xmlrpc-client:0.9.0 .

docker run -it --rm --name xmlrpc-server -p 8080:8080 antonioberben/xmlrpc-server:0.9.0
docker run -it --rm -e localhost --name xmlrpc-client antonioberben/xmlrpc-client:0.9.0