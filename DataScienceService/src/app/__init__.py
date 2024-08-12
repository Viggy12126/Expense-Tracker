from flask import Flask
from flask import request, jsonify
from service.messageService import MessageService
import json
import os
messageService=MessageService()

app = Flask(__name__)
app.config.from_pyfile('config.py')


@app.route('/v1/ds/message', methods=['POST'])
def handle_message():
    message = request.json.get('message')
    result = messageService.process_message(message)
    return result