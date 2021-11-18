package me.mindlessly.notenoughcoins.config;

import gg.essential.universal.UDesktop;
import gg.essential.vigilance.Vigilant;
import gg.essential.vigilance.data.*;
import me.mindlessly.notenoughcoins.commands.subcommands.Toggle;

import java.io.File;
import java.net.URI;
import java.util.Arrays;

public class Config extends Vigilant {

    @Property(
            type = PropertyType.SWITCH,
            category = "Flipping",
            subcategory = "Basic",
            name = "Enabled",
            description = "Whether the mod should check for and send flips"
    )
    public static boolean enabled = false;

    @Property(
            type = PropertyType.NUMBER,
            category = "Flipping",
            subcategory = "Basic",
            name = "Minimum Profit",
            description = "The minimum amount of profit that is required for the mod to send you a flip",
            max = Integer.MAX_VALUE,
            increment = 10000
    )
    public static int minProfit = 50000;

    @Property(
            type = PropertyType.NUMBER,
            category = "Flipping",
            subcategory = "Basic",
            name = "Demand",
            description = "The minimum sales per day of a product for the mod to send you a flip",
            max = Integer.MAX_VALUE
    )
    public static int demand = 3;

    @Property(
            type = PropertyType.PERCENT_SLIDER,
            category = "Flipping",
            subcategory = "Basic",
            name = "Minimum Profit Percentage",
            description = "The minimum percentage of profit that is required for the mod to send you a flip"
    )
    public static float minProfitPercentage = 0F;

    public static int threads = 1;

    @Property(
            type = PropertyType.SWITCH,
            category = "Flipping",
            subcategory = "Basic",
            name = "Alert Sounds",
            description = "Whether a sound should be played upon flip sent"
    )
    public static boolean alertSounds = true;

    @Property(
            type = PropertyType.TEXT,
            category = "Confidential",
            name = "API Key", protectedText = true,
            description = "Run /api new to set it automatically, or paste one if you do not want to renew it"
    )
    public static String apiKey = "";

    @Property(
            type = PropertyType.BUTTON,
            category = "Links",
            name = "Discord",
            description = "Join our Discord server!"
    )
    public static void discord() {
        UDesktop.browse(URI.create("https://discord.gg/b3JBsh8fEd"));
    }

    @Property(
            type = PropertyType.BUTTON,
            category = "Links",
            name = "GitHub",
            description = "Help with the development!"
    )
    public static void github() {
        UDesktop.browse(URI.create("https://github.com/mindlesslydev/NotEnoughCoins"));
    }

    public static final File CONFIG_FILE = new File("config/nec.toml");
    private boolean last = false;

    public Config() {
        super(CONFIG_FILE, "NEC Configuration");
        PropertyData threadsPD;
        try {
            threadsPD = new PropertyData(new PropertyAttributesExt(PropertyType.NUMBER, "Speed", "Flipping", "Basic",
                    "The amount of threads to use for flipping, the maximum is your core count", 1, Runtime.getRuntime().availableProcessors()),
                    new FieldBackedPropertyValue(getClass().getField("threads")), this);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return;
        }
        registerProperty(threadsPD); // threads param needs to be processed separately due to its max being dynamic
        Arrays.asList(
                "threads",
                "enabled",
                "apiKey"
        ).forEach(property -> registerListener(property, e -> Toggle.updateConfig(true)));
        /* YOU CANT CONFIGURE WHILE NOT ENABLED BECAUSE OF THIS
        Arrays.asList(
                "minProfit",
                "minProfitPercentage",
                "threads",
                "alertSounds"
        ).forEach(property -> addDependency(property, "enabled"));*/
        initialize();
    }
}
