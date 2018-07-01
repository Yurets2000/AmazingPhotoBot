package amazingphotobot;

import java.util.ArrayList;
import java.util.List;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class AnswerFactory {
    
    public static SendMessage getOptions(long chat_id){
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        row1.add(new InlineKeyboardButton("фильтры").setCallbackData("filters"));
        row1.add(new InlineKeyboardButton("эффекты").setCallbackData("effects"));
        row2.add(new InlineKeyboardButton("яркость").setCallbackData("brightness"));
        row2.add(new InlineKeyboardButton("контраст").setCallbackData("contrast"));
        buttons.add(row1);
        buttons.add(row2);
        markup.setKeyboard(buttons);
        SendMessage send = new SendMessage(chat_id, "Выберите одну из команд");
        send.setReplyMarkup(markup);
        return send;    
    }
    
    public static SendMessage getEffects(long chat_id){
        SendMessage send = new SendMessage(chat_id, "Выберите нужный эффект");         
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        List<InlineKeyboardButton> row3 = new ArrayList<>();
        row1.add(new InlineKeyboardButton("mosaic").setCallbackData("mosaic"));
        row1.add(new InlineKeyboardButton("mirror").setCallbackData("mirror"));      
        row1.add(new InlineKeyboardButton("sharpen").setCallbackData("sharpen"));
        row2.add(new InlineKeyboardButton("kaleidoscope").setCallbackData("kaleidoscope"));
        row2.add(new InlineKeyboardButton("crystallize").setCallbackData("crystallize"));
        row2.add(new InlineKeyboardButton("emboss").setCallbackData("emboss"));      
        row3.add(new InlineKeyboardButton("pinch").setCallbackData("pinch"));
        row3.add(new InlineKeyboardButton("chrome").setCallbackData("chrome"));
        row3.add(new InlineKeyboardButton("swim").setCallbackData("swim"));
        buttons.add(row1);
        buttons.add(row2);
        buttons.add(row3);
        markup.setKeyboard(buttons);
        send.setReplyMarkup(markup);
        return send;     
    }
    
    public static SendMessage getFilters(long chat_id){
        SendMessage send = new SendMessage(chat_id, "Выберите нужный фильтр");         
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        List<InlineKeyboardButton> row3 = new ArrayList<>();
        row1.add(new InlineKeyboardButton("sepia").setCallbackData("sepia"));
        row1.add(new InlineKeyboardButton("grayscale").setCallbackData("grayscale"));
        row1.add(new InlineKeyboardButton("negative").setCallbackData("negative"));
        row2.add(new InlineKeyboardButton("glow").setCallbackData("glow"));
        row2.add(new InlineKeyboardButton("mix").setCallbackData("mix"));
        row2.add(new InlineKeyboardButton("posterize").setCallbackData("posterize"));
        row3.add(new InlineKeyboardButton("diffusion").setCallbackData("diffusion"));
        row3.add(new InlineKeyboardButton("edge").setCallbackData("edge"));
        row3.add(new InlineKeyboardButton("tritone").setCallbackData("tritone"));
        buttons.add(row1);
        buttons.add(row2);
        buttons.add(row3);
        markup.setKeyboard(buttons);
        send.setReplyMarkup(markup);
        return send;     
    }
    
    public static SendMessage getNewOrOld(long chat_id){
        SendMessage send = new SendMessage(chat_id, "Сохранить изменения?");
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        row1.add(new InlineKeyboardButton("Да").setCallbackData("save_yes"));
        row1.add(new InlineKeyboardButton("Нет").setCallbackData("save_no"));
        buttons.add(row1);
        markup.setKeyboard(buttons);
        send.setReplyMarkup(markup);
        return send;
    }
    
}
