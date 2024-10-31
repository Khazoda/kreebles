package com.khazoda.kreebles.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class DastardlyTalismanItem extends Item {
  public static final  Item.Properties PROPERTIES = new Item.Properties().stacksTo(1);

  public DastardlyTalismanItem(Properties p) {
    super(PROPERTIES);
  }

  @Override
  public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
    super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    pTooltipComponents.add(Component.translatable("tooltip.kreebles.dastardly_talisman_1").withColor(10197915));
    pTooltipComponents.add(Component.translatable("tooltip.kreebles.dastardly_talisman_2").withColor(12688733));
    pTooltipComponents.add(Component.translatable("tooltip.kreebles.dastardly_talisman_3").withColor(6316381));

  }
}
