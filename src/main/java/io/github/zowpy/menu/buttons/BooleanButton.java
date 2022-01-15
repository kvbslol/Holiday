package io.github.zowpy.menu.buttons;

import io.github.zowpy.menu.Button;
import me.andyreckt.holiday.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public final class BooleanButton<T>
        extends Button {
    private final T target;
    private final String trait;
    private final BiConsumer<T, Boolean> writeFunction;
    private final Function<T, Boolean> readFunction;
    private final Consumer<T> saveFunction;

    public BooleanButton(T target, String trait, BiConsumer<T, Boolean> writeFunction, Function<T, Boolean> readFunction) {
        this(target, trait, writeFunction, readFunction, i -> {
        });
    }

    public BooleanButton(T target, String trait, BiConsumer<T, Boolean> toDo, Function<T, Boolean> getFrom, Consumer<T> saveFunction) {
        this.target = target;
        this.trait = trait;
        this.writeFunction = toDo;
        this.readFunction = getFrom;
        this.saveFunction = saveFunction;
    }


    @Override
    public ItemStack getButtonItem(Player p0) {
        return new ItemBuilder(Material.WOOL)
                .displayname((this.readFunction.apply(this.target) ? "&a" : "&c") + this.trait)
                .damage(this.readFunction.apply(this.target) ? 5 : 14)
                .build();
    }

    @Override
    public void clicked(final Player player, final int i, final ClickType clickType, final int hb) {
        boolean current = this.readFunction.apply(this.target);
        this.writeFunction.accept(this.target, !current);
        this.saveFunction.accept(this.target);
        player.sendMessage(ChatColor.GREEN + "Set " + this.trait + " trait to " + (current ? "off" : "on"));
    }
}