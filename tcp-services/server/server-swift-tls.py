import socket
import ssl

SERVER_HOST = os.getenv("SERVER_HOST", "localhost")
SERVER_PORT = int(os.getenv("SERVER_PORT", 443))

server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
server_socket.bind((SERVER_HOST, SERVER_PORT))
server_socket.listen()

print("TLS server listening on port 443...")

while True:
    client_socket, address = server_socket.accept()

    server_socket = ssl.wrap_socket(server_socket, ssl_version=ssl.PROTOCOL_TLSv1_2)

    message_bytes = client_socket.recv(1024).decode()
    print("Received message from client:", message_bytes)

    # Simulate processing the message
    response = "Message received: " + message_bytes

    # Send response to client
    server_socket.sendall(response.encode())
    client_socket.close()
    server_socket.close()
