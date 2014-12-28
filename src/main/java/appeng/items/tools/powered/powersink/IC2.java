/*
 * This file is part of Applied Energistics 2.
 * Copyright (c) 2013 - 2014, AlgorithmX2, All rights reserved.
 *
 * Applied Energistics 2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Applied Energistics 2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Applied Energistics 2.  If not, see <http://www.gnu.org/licenses/lgpl>.
 */

package appeng.items.tools.powered.powersink;


import com.google.common.base.Optional;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import ic2.api.item.IElectricItemManager;
import ic2.api.item.ISpecialElectricItem;

import appeng.api.config.PowerUnits;
import appeng.transformer.annotations.integration.Interface;
import appeng.transformer.annotations.integration.InterfaceList;
import appeng.transformer.annotations.integration.Method;


@InterfaceList( value = { @Interface( iface = "ic2.api.item.ISpecialElectricItem", iname = "IC2" ),
		@Interface( iface = "ic2.api.item.IElectricItemManager", iname = "IC2" ) } )
public class IC2 extends AERootPoweredItem implements IElectricItemManager, ISpecialElectricItem
{

	public IC2( Class c, Optional<String> subName )
	{
		super( c, subName );
	}

	@Override
	public double charge( ItemStack is, double amount, int tier, boolean ignoreTransferLimit, boolean simulate )
	{
		double addedAmt = amount;
		double limit = getTransferLimit( is );

		if ( !ignoreTransferLimit && amount > limit )
			addedAmt = limit;

		return addedAmt - ( ( int ) injectExternalPower( PowerUnits.EU, is, addedAmt, simulate ) );
	}

	@Override
	public double discharge( ItemStack itemStack, double amount, int tier, boolean ignoreTransferLimit, boolean externally, boolean simulate )
	{
		return 0;
	}

	@Override
	public double getCharge( ItemStack is )
	{
		return ( int ) PowerUnits.AE.convertTo( PowerUnits.EU, getAECurrentPower( is ) );
	}

	@Override
	public boolean canUse( ItemStack is, double amount )
	{
		return getCharge( is ) > amount;
	}

	@Override
	public boolean use( ItemStack is, double amount, EntityLivingBase entity )
	{
		if ( canUse( is, amount ) )
		{
			// use the power..
			extractAEPower( is, PowerUnits.EU.convertTo( PowerUnits.AE, amount ) );
			return true;
		}
		return false;
	}

	@Override
	public void chargeFromArmor( ItemStack itemStack, EntityLivingBase entity )
	{
		// wtf?
	}

	@Override
	public String getToolTip( ItemStack itemStack )
	{
		return null;
	}

	@Override
	public boolean canProvideEnergy( ItemStack itemStack )
	{
		return false;
	}

	@Override
	public Item getChargedItem( ItemStack itemStack )
	{
		return itemStack.getItem();
	}

	@Override
	public Item getEmptyItem( ItemStack itemStack )
	{
		return itemStack.getItem();
	}

	@Override
	public double getMaxCharge( ItemStack itemStack )
	{
		return PowerUnits.AE.convertTo( PowerUnits.EU, getAEMaxPower( itemStack ) );
	}

	@Override
	public int getTier( ItemStack itemStack )
	{
		return 1;
	}

	@Override
	public double getTransferLimit( ItemStack itemStack )
	{
		return Math.max( 32, getMaxCharge( itemStack ) / 200 );
	}

	@Override
	@Method( iname = "IC2" )
	public IElectricItemManager getManager( ItemStack itemStack )
	{
		return this;
	}

}