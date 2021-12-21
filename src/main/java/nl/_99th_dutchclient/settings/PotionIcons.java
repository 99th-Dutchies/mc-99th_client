package nl._99th_dutchclient.settings;

import net.minecraft.util.math.MathHelper;

import java.util.Arrays;
import java.util.Comparator;

public enum PotionIcons
{
    HIDE(0, "99thdc.options.POTION_ICONS.hide"),
    NORMAL(1, "99thdc.options.POTION_ICONS.normal"),
    ALL(2, "99thdc.options.POTION_ICONS.all");

    private static final PotionIcons[] field_238159_d_ = Arrays.stream(values()).sorted(Comparator.comparingInt(PotionIcons::func_238162_a_)).toArray((p_238165_0_) -> {
        return new PotionIcons[p_238165_0_];
    });
    private final int field_238160_e_;
    private final String field_238161_f_;

    private PotionIcons(int p_i232238_3_, String p_i232238_4_)
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

    public PotionIcons func_238166_c_()
    {
        return func_238163_a_(this.func_238162_a_() + 1);
    }

    public String toString()
    {
        switch (this)
        {
            case HIDE:
                return "off";

            case NORMAL:
                return "normal";

            case ALL:
                return "all";

            default:
                throw new IllegalArgumentException();
        }
    }

    public static PotionIcons func_238163_a_(int p_238163_0_)
    {
        return field_238159_d_[MathHelper.normalizeAngle(p_238163_0_, field_238159_d_.length)];
    }
}
