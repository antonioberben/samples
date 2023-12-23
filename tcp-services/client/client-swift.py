import os
import socket
import time

SERVER_HOST = os.getenv("SERVER_HOST", "localhost")
SERVER_PORT = int(os.getenv("SERVER_PORT", 8080))
MESSAGES = ["hello", "world"]

while True:
    for message in MESSAGES:
        client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        client_socket.connect((SERVER_HOST, SERVER_PORT))

        message_bytes = message.encode("utf-8")
        client_socket.sendall(message_bytes)

        # Receive response from server
        response = client_socket.recv(1024).decode()
        print("Received response from server:", response)

        client_socket.close()

        # Delay between messages
        time.sleep(2)