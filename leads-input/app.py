import json
import logging
import os
import socket
import pika
from flask import Flask, render_template, request, make_response

logging.basicConfig(level=logging.INFO)

rabbit_host = os.getenv('RABBIT_HOST', 'localhost')
exchange = os.getenv('RABBIT_EXCHANGE', 'leads_input')
exchange_type = os.getenv('RABBIT_EXCHANGE_TYPE', 'fanout')

hostname = socket.gethostname()

logging.info("creating connection with rabbitmq to host: {}".format(rabbit_host))


app = Flask(__name__)


def create_connection():
    return pika.BlockingConnection(pika.ConnectionParameters(host=rabbit_host))


def init_configs():
    channel = create_connection().channel()
    channel.exchange_declare(exchange=exchange, exchange_type=exchange_type, durable=True)
    channel.close()


def send_message(message):
    channel = create_connection().channel()
    print("[x] Send message %r" % (message))
    channel.basic_publish(exchange=exchange, body=message, routing_key='', properties=pika.BasicProperties(
                          content_type='application/json',
                          content_encoding='utf-8',
                          delivery_mode=2 
                      ))
    channel.close()


@app.route("/register", methods=['POST'])
def form():

    if request.method == 'POST':
        email = request.form['email']
        name = request.form['name']
        data = json.dumps({'name': name, 'email': email})
        send_message(data)
        
    resp = make_response(render_template(
        'form.html',
        email=email,
        hostname=hostname,
        show_alert=True
    ))
    return resp


@app.route("/", methods=['GET'])
def hello():
    return make_response(render_template('form.html', hostname=hostname))


if __name__ == "__main__":
    app.run(host='0.0.0.0', port=80, debug=True, threaded=True)
