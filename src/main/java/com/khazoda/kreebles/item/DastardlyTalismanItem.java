package com.khazoda.kreebles.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class DastardlyTalismanItem extends Item {
  public DastardlyTalismanItem(Properties pProperties) {
    super(pProperties);
  }

  @Override
  public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
    super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    pTooltipComponents.add(Component.translatable("tooltip.kreebles.dastardly_talisman_1").withColor(10197915));
  }
}
