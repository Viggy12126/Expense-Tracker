from utils.messagesUtil import MessagesUtil
from service.llmService import LLMService

class MessageService:
    def __init__(self):
        self.llmService=LLMService()
        self.messageUtil = MessagesUtil()
    
    def process_message(self, message):
        if self.messageUtil.isBankSms(message):
            return self.llmService.runLLM(message)
        else:
            return None
        