from xmlrpc.server import SimpleXMLRPCServer
from xmlrpc.server import SimpleXMLRPCRequestHandler
import os

SERVER_PORT = int(os.environ.get("SERVER_PORT", 8080))
SERVER_HOST = os.environ.get("SERVER_HOST", "0.0.0.0")

class HelloWorldHandler(SimpleXMLRPCRequestHandler):
    rpc_paths = ('/',)

def helloWorld(message):
    print("Received message: " + message)
    return "Hello, world!"

server = SimpleXMLRPCServer((SERVER_HOST, SERVER_PORT), requestHandler=HelloWorldHandler)
server.register_function(helloWorld, 'helloWorld')
print("XML-RPC server running on port 8080")
server.serve_forever()
