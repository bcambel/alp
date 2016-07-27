import socket
import sys

sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

# Connect the socket to the port where the server is listening
server_address = ('localhost', int( sys.argv[1]))
print >>sys.stderr, 'connecting to %s port %s' % server_address
sock.connect(server_address)

amount_received = 0

while True:
	data = sock.recv(16)
	amount_received += len(data)
	print >>sys.stderr, 'received "%s"' % data

print >>sys.stderr, "Closing socket"
sock.close()
