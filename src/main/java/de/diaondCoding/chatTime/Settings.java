package de.diaondCoding.chatTime;

import java.util.List;

import net.labymod.settings.elements.*;
import net.labymod.utils.Consumer;
import net.labymod.utils.Material;
import net.minecraft.block.BlockOldLeaf;

public class Settings {

    private boolean enabled;
    private String chatData;
    private String chatData2;
    private boolean before;
    private boolean useUTF8;
    private String hoverTime;

    Settings(List<SettingsElement> subSettings)
    {
        loadConfig();

        subSettings.add(new HeaderElement("General"));

        subSettings.add(new BooleanElement("Enabled", new ControlElement.IconData(Material.LEVER), new Consumer()
        {

            public void accept(Object aBooleanObj)
            {
                Boolean aBoolean = (Boolean) aBooleanObj;
                Settings.this.enabled = aBoolean.booleanValue();
                Settings.this.saveConfig();
            }
        }, this.enabled));

        StringElement channelStringElement = new StringElement("Time Formatting", new ControlElement.IconData(Material.PAPER), this.chatData, new Consumer()
        {
            public void accept(Object chatDataStringObj)
            {
                String chatDataString = (String) chatDataStringObj;
                Settings.this.chatData = chatDataString;
                Settings.this.saveConfig();
            }
        });
        subSettings.add(channelStringElement);

        channelStringElement = new StringElement("Prefix Formatting", new ControlElement.IconData(Material.PAPER), this.chatData2, new Consumer()
        {
            public void accept(Object chatDataStringObj)
            {
                String chatDataString = (String) chatDataStringObj;
                Settings.this.chatData2 = chatDataString;
                Settings.this.saveConfig();
            }
        });
        subSettings.add(channelStringElement);

        channelStringElement = new StringElement("Hover Formatting", new ControlElement.IconData(Material.PAPER), this.hoverTime, new Consumer()
        {
            public void accept(Object chatDataStringObj)
            {
                String chatDataString = (String) chatDataStringObj;
                Settings.this.hoverTime = chatDataString;
                Settings.this.saveConfig();
            }
        });
        subSettings.add(channelStringElement);

        subSettings.add(new BooleanElement("Before Message", new ControlElement.IconData(Material.LEVER), new Consumer()
        {
            public void accept(Object aBooleanObj)
            {
                Boolean aBoolean = (Boolean) aBooleanObj;
                Settings.this.before = aBoolean.booleanValue();
                Settings.this.saveConfig();
            }
        }, this.before));
    }

    String c_enabled = "enabled";
    String c_chatData = "chatData";
    String c_chatData2 = "chatData2";
    String c_before = "before";
    String c_useUTF8 = "useUTF8";
    String c_hover = "hover";

    private void loadConfig()
    {
        this.enabled = (ChatTimeAddon.getInstance().getConfig().has(this.c_enabled) ? ChatTimeAddon.getInstance().getConfig().get(this.c_enabled).getAsBoolean() : true);
        this.chatData = (ChatTimeAddon.getInstance().getConfig().has(this.c_chatData) ? ChatTimeAddon.getInstance().getConfig().get(this.c_chatData).getAsString() : "HH:mm:ss");
        this.chatData2 = (ChatTimeAddon.getInstance().getConfig().has(this.c_chatData2) ? ChatTimeAddon.getInstance().getConfig().get(this.c_chatData2).getAsString() : "&4[&e%time%&4] ");
        this.before = (ChatTimeAddon.getInstance().getConfig().has(this.c_before) ? ChatTimeAddon.getInstance().getConfig().get(this.c_before).getAsBoolean() : true);
        this.useUTF8 = (ChatTimeAddon.getInstance().getConfig().has(this.c_useUTF8) ? ChatTimeAddon.getInstance().getConfig().get(this.c_useUTF8).getAsBoolean() : false);
        this.hoverTime = (ChatTimeAddon.getInstance().getConfig().has(this.c_hover) ? ChatTimeAddon.getInstance().getConfig().get(this.c_hover).getAsString() : "");
    }

    private void reloadConfig()
    {
        loadConfig();
    }

    private void saveConfig()
    {
        ChatTimeAddon.getInstance().getConfig().addProperty(this.c_enabled, Boolean.valueOf(this.enabled));
        ChatTimeAddon.getInstance().getConfig().addProperty(this.c_chatData, this.chatData);
        ChatTimeAddon.getInstance().getConfig().addProperty(this.c_chatData2, this.chatData2);
        ChatTimeAddon.getInstance().getConfig().addProperty(this.c_before, Boolean.valueOf(this.before));
        ChatTimeAddon.getInstance().getConfig().addProperty(this.c_useUTF8, Boolean.valueOf(this.useUTF8));
        ChatTimeAddon.getInstance().getConfig().addProperty(this.c_hover, this.hoverTime);
    }

    public boolean isEnabled()
    {
        return this.enabled;
    }

    public String getChatData()
    {
        return this.chatData;
    }

    public String getChatData2()
    {
        return this.chatData2;
    }

    public boolean isBefore()
    {
        return this.before;
    }

    public boolean isUseUTF8()
    {
        return this.useUTF8;
    }

    public String getHoverTime()
    {
        return this.hoverTime;
    }

    public boolean showHoverTime()
    {
        if (getHoverTime().length() == 0) {
            return false;
        }
        return true;
    }

}
