import xmlrpc.client
import os


SERVER_HOST = os.environ.get("SERVER_HOST", "localhost")
SERVER_PORT = int(os.environ.get("SERVER_PORT", 8080))

print("URL: http://{}:{}/".format(SERVER_HOST, SERVER_PORT))
server = xmlrpc.client.ServerProxy("http://{}:{}/".format(SERVER_HOST, SERVER_PORT))

message = server.helloWorld("Hello, world!")
print("Received message from server: " + message)