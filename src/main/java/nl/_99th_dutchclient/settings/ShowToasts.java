package nl._99th_dutchclient.settings;

import net.minecraft.util.math.MathHelper;

import java.util.Arrays;
import java.util.Comparator;

public enum ShowToasts
{
    OFF(0, "99thdc.options.SHOW_TOASTS.off"),
    SYSTEM(1, "99thdc.options.SHOW_TOASTS.system"),
    ALL(2, "99thdc.options.SHOW_TOASTS.all");

    private static final ShowToasts[] field_238159_d_ = Arrays.stream(values()).sorted(Comparator.comparingInt(ShowToasts::func_238162_a_)).toArray((p_238165_0_) -> {
        return new ShowToasts[p_238165_0_];
    });
    private final int field_238160_e_;
    private final String field_238161_f_;

    private ShowToasts(int p_i232238_3_, String p_i232238_4_)
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

    public ShowToasts func_238166_c_()
    {
        return func_238163_a_(this.func_238162_a_() + 1);
    }

    public String toString()
    {
        switch (this)
        {
            case OFF:
                return "off";

            case SYSTEM:
                return "system";

            case ALL:
                return "all";

            default:
                throw new IllegalArgumentException();
        }
    }

    public static ShowToasts func_238163_a_(int p_238163_0_)
    {
        return field_238159_d_[MathHelper.normalizeAngle(p_238163_0_, field_238159_d_.length)];
    }
}
