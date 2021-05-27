package nl._99th_dutchclient;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.settings.PointOfView;

public class Freelook
{
    private Minecraft mc;

    public boolean active = false;
    public boolean activeBefore = false;
    public float pitch;
    public float yaw;
    public float originalPitch;
    public float originalYaw;
    public PointOfView originalpov;

    public Freelook(Minecraft mc) {
        this.mc = mc;
    }

    public void setActive(boolean active) {
        if(this.active == active) return;

        this.active = active;
        ActiveRenderInfo ari = this.mc.gameRenderer.getActiveRenderInfo();

        if(active) {
            this.originalPitch = ari.getPitch();
            this.originalYaw = ari.getYaw();
            this.originalpov = this.mc.gameSettings.getPointOfView();

            if(!this.activeBefore) {
                this.yaw = this.originalYaw;
                this.pitch = this.originalPitch;
            }
            this.mc.gameSettings.setPointOfView(PointOfView.THIRD_PERSON_BACK);

            this.activeBefore = true;
        } else {
            ari.setDirection(this.originalPitch, this.originalYaw);
            this.mc.gameSettings.setPointOfView(originalpov);
        }
    }
}
