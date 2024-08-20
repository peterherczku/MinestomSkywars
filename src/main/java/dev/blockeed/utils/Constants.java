package dev.blockeed.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class Constants {

    public static TextComponent PREFIX = Component
            .text("SKYWARS")
            .decorate(TextDecoration.BOLD)
            .color(TextColor.fromHexString("#7ed6df"))
            .append(Component.text(
                    " | "
            ).decoration(TextDecoration.BOLD, TextDecoration.State.FALSE).color(TextColor.fromHexString("#dff9fb")));

}
