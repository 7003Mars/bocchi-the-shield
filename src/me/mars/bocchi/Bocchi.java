package me.mars.bocchi;

import arc.Core;
import arc.Events;
import arc.graphics.g2d.TextureRegion;
import mindustry.content.Blocks;
import mindustry.game.EventType;
import mindustry.mod.Mod;
import mindustry.world.blocks.defense.ForceProjector;

public class Bocchi extends Mod {
	public static TextureRegion bocchiRegion;
	public Bocchi() {
		Events.on(EventType.ContentInitEvent.class, contentInitEvent -> {
			bocchiRegion = Core.atlas.find("bocchi-melt");
			bocchiRegion.flip(false, true);
			Blocks.forceProjector.buildType = () -> new ModifiedForceBuild((ForceProjector) Blocks.forceProjector);
		});

	}
}
