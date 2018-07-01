package amazingphotobot;

import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import static amazingphotobot.MessageHandler.NextAction.*;
import java.util.HashMap;
import java.util.Map;

public class AmazingPhotoBot extends TelegramLongPollingBot{
    
    private final Map<Long,MessageHandler> map = new HashMap<>();
    
    @Override
    public String getBotToken() {
        return "578900303:AAHFftKVeJfheck9seQtcIvpuuFSd7K5eP4";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()){
            Message msg = update.getMessage();
            MessageHandler handler = null;
            if(map.containsKey(msg.getChatId())){
                handler = map.get(msg.getChatId());
            }else{
                handler = new MessageHandler();
                map.put(msg.getChatId(), handler);
            }
            if(msg.hasText() && msg.isCommand()){
                handler.handleCommand(this, msg);
            }  
            if(msg.hasPhoto()){
                handler.processPhoto(this, msg);
            }
            if(msg.hasText() && (handler.getNext() == BRIGHTNESS || handler.getNext() == CONTRAST) && tryParse(msg.getText())!= null){
                String text = msg.getText();
                int value = tryParse(text);
                if(value>=0 && value<=100){
                    handler.handleImageProperties(this, msg, value);
                }
            }              
        }
        if(update.hasCallbackQuery()){
            CallbackQuery cq = update.getCallbackQuery(); 
            Message msg = cq.getMessage();
            MessageHandler handler = null;
            if(map.containsKey(msg.getChatId())){
                handler = map.get(msg.getChatId());
            }else{
                handler = new MessageHandler();
                map.put(msg.getChatId(), handler);
            }
            if(!handler.isOperationChoosed()){
                handler.handleOperationCallbackQuery(this, cq);
            }else if(handler.isOperationChoosed() && !handler.isActionChoosed() && handler.getNext() == FILTERS){
                handler.handleImageFilters(this, cq);
            }else if(handler.isOperationChoosed() && handler.isActionChoosed() && !handler.isSaveChoosed()){
                handler.handleSave(this, cq);
            }           
        }
    }

    @Override
    public String getBotUsername() {
        return "AmazingPhotoBot";
    }
    
    private Integer tryParse(String s) {
        Integer val;
        try {
          val = Integer.parseInt(s.trim());
        } catch (NumberFormatException ex) {
          val = null; 
        }
        return val;
    }
    
}
