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

package appeng.debug;

import java.util.EnumSet;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import appeng.block.AEBaseBlock;
import appeng.core.features.AEFeature;

public class BlockPhantomNode extends AEBaseBlock
{

	public BlockPhantomNode() {
		super( BlockPhantomNode.class, Material.iron );
		setFeature( EnumSet.of( AEFeature.UnsupportedDeveloperTools, AEFeature.Creative ) );
		setTileEntity( TilePhantomNode.class );
	}

	@Override
	public boolean onActivated(World w, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		TilePhantomNode tpn = getTileEntity( w, x, y, z );
		tpn.BOOM();
		return true;
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegistry)
	{
		registerNoIcons();
	}

}