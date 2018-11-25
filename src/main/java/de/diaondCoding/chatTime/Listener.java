package de.diaondCoding.chatTime;

import net.labymod.api.events.MessageModifyChatEvent;
import net.labymod.core.ChatComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentBase;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.HoverEvent;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Listener {

    private String lastMessage = UUID.randomUUID().toString();

    private String UTF8C(String old) {
        if (!ChatTimeAddon.getSettings().isUseUTF8()) {
            return old;
        }
        byte[] ptext = old.getBytes(StandardCharsets.ISO_8859_1);
        return new String(ptext, StandardCharsets.UTF_8);
    }

    public Listener() {
        ChatTimeAddon.getInstance().getApi().getEventManager().register((MessageModifyChatEvent) o -> modifyChatMessage(o));
    }

    private Object modifyChatMessage(Object o){
        if(!ChatTimeAddon.getSettings().isEnabled()) {
            return o;
        }
        try {
            TextComponentBase cct = (TextComponentBase) o;
            String prefix = timeFormat();

            if(cct.getUnformattedText().length() == 0) return o;
            if(prefix.length() == 0) return o;
            if(cct.getUnformattedText().equals(lastMessage)) return o;
            if(cct.getUnformattedText().contains(prefix)) return o;
            String withOut = cct.getUnformattedText().replace("\t", "").replace("\n", "").replace("\r", "").replace(" ", "");
            if(withOut.length() == 0) return o;

            prefix = UTF8C(prefix + "§f");

            if(ChatTimeAddon.getSettings().isBefore()) {
                TextComponentBase newC = newWithText("");

                TextComponentBase cc = addHover(newWithText(prefix), timeFormatHover(), ChatTimeAddon.getSettings().showHoverTime());
                newC.appendSibling(cc);

                String base = cct.getFormattedText();
                for(ITextComponent c  : cct.getSiblings()) {
                    base = base.replace(c.getFormattedText(), "");
                }
                TextComponentBase cc2 = newWithText(base);
                newC.appendSibling(cc2);

                for(ITextComponent c  : cct.getSiblings()) {
                    newC.appendSibling(c);
                }

                cct = newC;
            }else {
                TextComponentBase cc = addHover(newWithText(prefix), timeFormatHover(), ChatTimeAddon.getSettings().showHoverTime());
                cct.appendSibling(cc);
            }

            lastMessage = cct.getUnformattedText();
            return cct;
        }catch (Exception e){
            System.out.println("Exception: ");
            e.printStackTrace();
            return o;
        }
    }

    private String timeFormat(){
        String format = ChatTimeAddon.getSettings().getChatData();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        String time = LocalDateTime.now().format(formatter);

        String t = ChatTimeAddon.getSettings().getChatData2();
        return t.replace("%time%", time).replace("&", "§");
    }

    private String timeFormatHover(){
        String format = ChatTimeAddon.getSettings().getChatData();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        String time = LocalDateTime.now().format(formatter);

        String t = ChatTimeAddon.getSettings().getHoverTime();
        return t.replace("%time%", time).replace("&", "§");
    }

    public TextComponentBase addHover(TextComponentBase comp, String hover, boolean add){
        if(!add) {
            return comp;
        }
        Style style = comp.getStyle();
        style.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString(hover)));
        comp.setStyle(style);
        return comp;
    }
    public TextComponentBase newWithText(String text){
        return new TextComponentString(text);
    }
}
