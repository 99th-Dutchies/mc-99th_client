package nl._99th_client.settings;

import net.minecraft.util.math.MathHelper;

import java.util.Arrays;
import java.util.Comparator;

public enum DiscordShowRPC
{
    OFF(0, "99thclient.options.DISCORDRPC_SHOW_SERVER.off"),
    PLAYING(1, "99thclient.options.DISCORDRPC_SHOW_SERVER.playing"),
    SERVER(2, "99thclient.options.DISCORDRPC_SHOW_SERVER.server"),
    GAME(3, "99thclient.options.DISCORDRPC_SHOW_SERVER.game"),
    MAP(4, "99thclient.options.DISCORDRPC_SHOW_SERVER.map");

    private static final DiscordShowRPC[] field_238159_d_ = Arrays.stream(values()).sorted(Comparator.comparingInt(DiscordShowRPC::func_238162_a_)).toArray((p_238165_0_) -> {
        return new DiscordShowRPC[p_238165_0_];
    });
    private final int field_238160_e_;
    private final String field_238161_f_;

    private DiscordShowRPC(int p_i232238_3_, String p_i232238_4_)
    {
        this.field_238160_e_ = p_i232238_3_;
        this.field_238161_f_ = p_i232238_4_;
    }

    public int func_238162_a_()
    {
        return this.field_238160_e_;
    }

    public String func_238164_b_()
    {
        return this.field_238161_f_;
    }

    public DiscordShowRPC func_238166_c_()
    {
        return func_238163_a_(this.func_238162_a_() + 1);
    }

    public String toString()
    {
        switch (this)
        {
            case OFF:
                return "off";

            case PLAYING:
                return "playing";

            case SERVER:
                return "server";

            case GAME:
                return "game";

            case MAP:
                return "map";

            default:
                throw new IllegalArgumentException();
        }
    }

    public static DiscordShowRPC func_238163_a_(int p_238163_0_)
    {
        return field_238159_d_[MathHelper.normalizeAngle(p_238163_0_, field_238159_d_.length)];
    }
}
