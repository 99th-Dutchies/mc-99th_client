package net.minecraft.client.gui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatLine<T>
{
    private final int updateCounterCreated;
    private final T lineString;
    private final int chatLineID;
    private final LocalDateTime timestamp;

    public ChatLine(int updatedCounterCreated, T lineString, int chatLineID)
    {
        this.lineString = lineString;
        this.updateCounterCreated = updatedCounterCreated;
        this.chatLineID = chatLineID;
        this.timestamp = LocalDateTime.now();
    }

    public T getLineString()
    {
        return this.lineString;
    }

    public int getUpdatedCounter()
    {
        return this.updateCounterCreated;
    }

    public int getChatLineID()
    {
        return this.chatLineID;
    }

    public String getFormattedTimestamp()
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        return dtf.format(this.timestamp);
    }

    public LocalDateTime getTimestamp() { return this.timestamp; }
}
