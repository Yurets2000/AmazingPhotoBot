package amazingphotobot;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.telegram.telegrambots.api.methods.GetFile;
import org.telegram.telegrambots.api.objects.File;
import org.telegram.telegrambots.api.objects.PhotoSize;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class Photo {
    private BufferedImage photo;

    public Photo(BufferedImage photo) {
        this.photo = photo;
    }
    
    public Photo(TelegramLongPollingBot bot, PhotoSize photo){      
        try {
            File file = bot.getFile(new GetFile().setFileId(photo.getFileId()));
            java.io.File download = bot.downloadFile(file);
            this.photo = ImageIO.read(download);
        } catch (TelegramApiException | IOException ex) {
            ex.printStackTrace(System.out);
        }
    }

    public BufferedImage getPhoto() {
        return photo;
    }
    
}
