package nl._99th_dutchclient.util;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageClipboardHelper implements ClipboardOwner {
    public void copyToClipboardFromFilename(String fileName) {
        Thread thread = new Thread(() -> {
            try {
                BufferedImage in = ImageIO.read(new File(fileName));
                Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
                TransferableImage t = new TransferableImage(in);
                c.setContents(t, this);

                Minecraft.getInstance().ingameGUI.getChatGUI().printChatMessage(new StringTextComponent("Copied image to clipboard!"));
            } catch (Exception ex) {
                System.out.println("Failed copying file to clipboard: " + ex.toString() + " ; " + ex.getMessage());
            }
        });

        thread.start();
    }

    @Override
    public void lostOwnership(Clipboard clipboard, Transferable contents) {
        System.out.println("Lost Clipboard Ownership");
    }
}