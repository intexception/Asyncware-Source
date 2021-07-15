package com.nquantum.module.render;

import cf.nquan.util.Strings;
import com.nquantum.Asyncware;
import com.nquantum.event.EventTarget;
import com.nquantum.event.impl.EventUpdate;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import nig.hero.settings.Setting;

import java.util.ArrayList;

public class SwordAnimation extends Module {
    public SwordAnimation(){
        super("SwordAnimation", 0, Category.RENDER);
    }


    public static int animint;
    @Override
    public void setup(){
        ArrayList<String> anims = new ArrayList<>();
        anims.add("Swang");
        anims.add("Swong");
        anims.add("Exhibition");
        anims.add("Asyncware");
        anims.add("Fan");
        anims.add("Idk");


        Asyncware.instance.settingsManager.rSetting(new Setting("Animation", this, "Swang", anims));



        /*
         SwordAnimation sword = new SwordAnimation();
                            if(SwordAnimation.animint == 1){
                                this.transformFirstPersonItem(f, 0.0F);
                                this.fan();
                                break;
                            }
                            if(SwordAnimation.animint == 2) {
                               // float f8 = (float) Math.sin(Math.sqrt(f1) * Math.PI);
                               // func_178096_b(f, 0.0F);
                               // GlStateManager.translate(0.1F, 0.145F, -0.15F);
                               // GL11.glRotated((-f8 * 50.0F), (f8 / 2.0F), 0.0D, 7.0D);
                               // GL11.glRotated((-f8 * -25.0F), 0.800000011920929D, (f8 / 2.0F), 0.0D);
                               // func_178103_d();
                               // break;


                                this.transformFirstPersonItem(f / 2.0F, 1.0F);
                                GL11.glTranslatef(0, -0.2f, 0);
                                GL11.glScalef(1.25f, 1.25f, 1.23f);
                              //  GL11.glRotatef(-var9 * 60.0F / 2.0F, var9 / 2.0F, -0.0F, 9.0F);

                                GlStateManager.rotate(-80, 30, 1, -30);
                                GlStateManager.scale(2, 2, 2);
                                GlStateManager.translate(0.8f, 0, 0);
                                GL11.glRotatef(-var9 * 30.0F, 121.0F, var9 / 10.0F, -0.0F);
                                this.func_178103_d();





                                break;
                            }
                            if(SwordAnimation.animint == 3){
                                float f8 = (float) Math.sin(Math.sqrt(f1) * Math.PI);
                                func_178096_b(f, 0.0F);
                                GlStateManager.translate(0.1F, 0.435F, -0.15F);
                                GL11.glTranslatef((-f8), -(f8 / 2.5f), 0);
                                GL11.glRotated(12, (f8 / 2.78F), 0.0D, 50.0D);
                                GL11.glRotated((-f8 * 40.0F), (f8 / 2.78F), 0.0D, 50.0D);
                                GL11.glRotated((-f8 * -34.0F), 0.800000011920929D, (f8 / 2.0F), 0.0D);
                                func_178103_d();
                                break;
                            }
                            if(SwordAnimation.animint == 4){

                                float f8 = (float) Math.sin(Math.sqrt(f1) * Math.PI);
                                func_178096_b(f, 0.0F);
                                GlStateManager.translate(1.332F, 0.335F, -0.15F);
                                GL11.glRotated((-f8 * 32.0F), (f8 / 2.0F), 0.0D, 7.0D);
                                GL11.glRotated((-f8 * -40.0F), 0.800000011920929D, (f8 / 2.0F), 0.0D);
                                GlStateManager.translate(-0.5F, -0.37F, 0.0F);
                                GlStateManager.rotate(30.0F, 0.0F, 1.0F, 0.0F);
                                GlStateManager.rotate(-110.0F, 1.0F, 0.0F, 0.0F);
                                GlStateManager.rotate(60.0F, 0.0F, 1.0F, 0.0F);
                                GlStateManager.scale(1.25F, 1.25F, 1.25F);
                                break;
                            }
                            else
                            {
                            this.transformFirstPersonItem(f, 0.0F);
                            this.func_178103_d();
                            }


         */

    }



    @EventTarget
    public void onUpdate(EventUpdate event){
        String mode = Asyncware.instance.settingsManager.getSettingByName("Animation").getValString();
        this.setDisplayName("SwordAnimation \u00A77" + Strings.capitalizeOnlyFirstLetter(mode));
        if(mode.equalsIgnoreCase("Fan")){
            animint = 1;
        }
        if(mode.equalsIgnoreCase("Swang")){
            animint = 2;
        }
        if(mode.equalsIgnoreCase("Asyncware")){
            animint = 3;
        }
        if(mode.equalsIgnoreCase("Swong")){
            animint = 4;
        }
        if(mode.equalsIgnoreCase("Exhibition")){
            animint = 5;
        }
    }



}
