import socket
import os

SERVER_HOST = os.getenv("SERVER_HOST", "0.0.0.0")
SERVER_PORT = int(os.getenv("SERVER_PORT", 443))

server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
server_socket.bind((SERVER_HOST, SERVER_PORT))
server_socket.listen()

print(f"Server listening on {SERVER_HOST}:{SERVER_PORT}...")

while True:
    client_socket, address = server_socket.accept()

    message_bytes = client_socket.recv(1024).decode()
    print("Received message from client:", message_bytes)

    # Simulate processing the message
    response = "Message received: " + message_bytes

    # Send response to client
    server_socket.sendall(response.encode())
    client_socket.close()
    server_socket.close()
