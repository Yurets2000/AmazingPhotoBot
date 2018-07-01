package amazingphotobot;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.List;
import javax.imageio.ImageIO;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.PhotoSize;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class MessageHandler {
    
    private boolean operationChoosed;
    private boolean actionChoosed;
    private boolean saveChoosedAction;
    private boolean start;
    private final ArrayDeque<Photo> listOfPhotos = new ArrayDeque<>();
    private NextAction next;
    
    public enum NextAction{
       FILTERS,CONTRAST,BRIGHTNESS,NONE
    }
    
    private void reset(){
        listOfPhotos.clear();
        next = NextAction.NONE;
        operationChoosed = false;
        actionChoosed = false;
        saveChoosedAction = false;
        start = true;
    }
    
    public void handleCommand(TelegramLongPollingBot bot, Message message){
        String command = message.getText().substring(1);
        switch(command){
            case "help":
                String helpText = "<b>Amazing Photo Bot help</b>\nВас приветствует Amazing Photo Bot."
                        + "Я создан для быстрой и легкой обработки изображений.\n"
                        + "Я могу:\n"
                        + " - Применять различные фильтры и эффекты к изображению;\n"
                        + " - Изменять яркость изображения;\n"
                        + " - Изменять контрастность изображения.\n"
                        + "Для начала работы с изображением отправьте команду start.\n";               
                sendText(bot,message.getChatId(),helpText);
            break;
            case "start":
                reset();
                String startText = "Отправьте изображение, которое вы желаете обработать."; 
                sendText(bot,message.getChatId(),startText);
            break;
        }
    }
    
    public void processPhoto(TelegramLongPollingBot bot, Message message){
        if(!start || !listOfPhotos.isEmpty()){
            return;
        }             
        long chat_id = message.getChatId();
        List<PhotoSize> photos = message.getPhoto();
        SendMessage send = AnswerFactory.getOptions(chat_id);
        try {
            bot.execute(send);
        } catch (TelegramApiException ex) {
            ex.printStackTrace(System.out);
        }            
        PhotoSize ps = photos.stream().sorted(Comparator.comparing(PhotoSize::getFileSize).reversed()).findFirst().orElse(null);
        listOfPhotos.push(new Photo(bot, ps));
    }
    
    public void handleImageProperties(TelegramLongPollingBot bot, Message message, int value){
        BufferedImage bi = PhotoManager.copyImage(listOfPhotos.peekLast().getPhoto()); 
        long chat_id = message.getChatId();
        
        switch(next){
            case CONTRAST:
                bi = PhotoManager.contrastChanger(bi, value);
                break;
            case BRIGHTNESS:
                bi = PhotoManager.brightnessChanger(bi, value);
                break;
        }       
        listOfPhotos.add(new Photo(bi));      
        sendTransformedImage(bot, chat_id, bi);
        SendMessage send = AnswerFactory.getNewOrOld(chat_id);
        try {
            bot.execute(send);
        } catch (TelegramApiException ex) {
            ex.printStackTrace(System.out);
        }    
    }
    
    public void handleImageFilters(TelegramLongPollingBot bot, CallbackQuery query){
        String callData = query.getData();
        Message message = query.getMessage();
        long chat_id = message.getChatId();
        int message_id = message.getMessageId();
        BufferedImage bi = PhotoManager.copyImage(listOfPhotos.peekLast().getPhoto());        
        
        switch(callData){
            //filters
            case "sepia":            
                bi = PhotoManager.sepiaFilter(bi);
            break;
            case "grayscale":
                bi = PhotoManager.grayscaleFilter(bi);
            break;
            case "negative":
                bi = PhotoManager.negativeFilter(bi);
            break;
            case "glow":
                bi = PhotoManager.glowFilter(bi);
            break;
            case "mix":
                bi = PhotoManager.mixFilter(bi);
            break;
            case "posterize":
                bi = PhotoManager.posterizeFilter(bi);
            break;
            case "diffusion":
                bi = PhotoManager.diffusionFilter(bi);
            break;
            case "edge":
                bi = PhotoManager.edgeFilter(bi);
            break;
            case "tritone":
                bi = PhotoManager.tritoneFilter(bi);
            break;
            //effects
            case "mosaic":
                bi = PhotoManager.mosaicEffect(bi);
            break;
            case "mirror":
                bi = PhotoManager.mirrorEffect(bi);
            break;
            case "sharpen":
                bi = PhotoManager.sharpenEffect(bi);
            break;
            case "kaleidoscope":
                bi = PhotoManager.kaleidoscopeEffect(bi);
            break;
            case "crystallize":
                bi = PhotoManager.crystallizeEffect(bi);
            break;
            case "emboss":
                bi = PhotoManager.embossEffect(bi);
            break;
            case "pinch":
                bi = PhotoManager.pinchEffect(bi);
            break;
            case "chrome":
                bi = PhotoManager.chromeEffect(bi);
            break;
            case "swim":
                bi = PhotoManager.swimEffect(bi);
            break;          
        }         
        listOfPhotos.add(new Photo(bi));      
        sendTransformedImage(bot, chat_id, bi);
        DeleteMessage delete = new DeleteMessage(chat_id, message_id);
        SendMessage send = AnswerFactory.getNewOrOld(chat_id);
        try {
            bot.execute(delete);
            bot.execute(send);
        } catch (TelegramApiException ex) {
            ex.printStackTrace(System.out);
        }              
    }
    
    public void handleOperationCallbackQuery(TelegramLongPollingBot bot, CallbackQuery query){
        String callData = query.getData();
        Message message = query.getMessage();
        long chat_id = message.getChatId();
        int message_id = message.getMessageId();
        if(listOfPhotos.isEmpty()){
            SendMessage send = new SendMessage(chat_id, "Изображение не выбрано!");
            try {
                bot.execute(send);
            } catch (TelegramApiException ex) {
                ex.printStackTrace(System.out);
            }
            return;
        }
        switch(callData){
            case "filters":
                try {
                    SendMessage send = AnswerFactory.getFilters(chat_id);
                    bot.execute(send);
                } catch (TelegramApiException ex) {
                    ex.printStackTrace(System.out);
                }      
                next = NextAction.FILTERS;
                operationChoosed = true;
            break;
            case "effects":
                try {
                    SendMessage send = AnswerFactory.getEffects(chat_id);
                    bot.execute(send);
                } catch (TelegramApiException ex) {
                    ex.printStackTrace(System.out);
                }      
                next = NextAction.FILTERS;
                operationChoosed = true;
            break;
            case "brightness":
                sendText(bot, chat_id, "Введите желаемый уровень яркости (от 0 до 100).\n "
                        + "Уровень яркости 50 соответствует яркости исходного изображения.");
                next = NextAction.BRIGHTNESS;
                operationChoosed = true;
                break;
            case "contrast":
                sendText(bot, chat_id, "Введите желаемый уровень контраста (от 0 до 100).\n "
                        + "Уровень контраста 50 соответствует контрасту исходного изображения.");
                next = NextAction.CONTRAST;
                operationChoosed = true;
                break;
        }
        DeleteMessage delete = new DeleteMessage(chat_id, message_id);
        try {
            bot.execute(delete);
        } catch (TelegramApiException ex) {
            ex.printStackTrace(System.out);
        }
    }
    
    private void sendText(TelegramLongPollingBot bot, long chat_id, String text){
        SendMessage send = new SendMessage(chat_id, text);
        send.enableHtml(true);
        try {
            bot.execute(send);
        } catch (TelegramApiException ex) {               
            ex.printStackTrace(System.out);
        }   
    }
    
    private void sendTransformedImage(TelegramLongPollingBot bot, long chat_id, BufferedImage img){
        SendPhoto sendPhoto = new SendPhoto();    
        sendPhoto.setChatId(chat_id);
        sendPhoto.setCaption("Ваше изображение");
        java.io.File output = new java.io.File("image.png");
        try {
            ImageIO.write(img, "png", output);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
        sendPhoto.setNewPhoto(output);
        try {
            bot.sendPhoto(sendPhoto);
        } catch (TelegramApiException ex) {
            ex.printStackTrace(System.out);
        }
        next = NextAction.NONE;
        actionChoosed = true;
        saveChoosedAction = false;
    }
    
    public void handleSave(TelegramLongPollingBot bot, CallbackQuery query){
        String callData = query.getData();
        Message message = query.getMessage();
        long chat_id = message.getChatId();
        int message_id = message.getMessageId();
        
        if(callData.equals("save_no")){
            listOfPhotos.pollLast();
        }      
        SendMessage send = AnswerFactory.getOptions(chat_id);
        DeleteMessage delete = new DeleteMessage(chat_id, message_id);
        try {
            bot.execute(delete);
            bot.execute(send);
        } catch (TelegramApiException ex) {
           ex.printStackTrace(System.out);
        }
        saveChoosedAction = true;
        operationChoosed = false;
        actionChoosed = false;
    }
    
    public boolean isOperationChoosed() {
        return operationChoosed;
    }

    public boolean isStart() {
        return start;
    }

    public boolean isSaveChoosed() {
        return saveChoosedAction;
    }

    public boolean isActionChoosed() {
        return actionChoosed;
    }
    
    public NextAction getNext() {
        return next;
    }
    
}
