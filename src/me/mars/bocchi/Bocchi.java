package me.mars.bocchi;

import arc.Core;
import arc.Events;
import arc.graphics.Pixmap;
import arc.graphics.g2d.TextureRegion;
import arc.graphics.gl.FrameBuffer;
import arc.util.Log;
import mindustry.content.Blocks;
import mindustry.game.EventType;
import mindustry.mod.Mod;
import mindustry.world.blocks.defense.ForceProjector;
import static me.mars.bocchi.ModifiedForceBuild.frameBuffer;

public class Bocchi extends Mod {
	public static TextureRegion bocchiRegion;
	public Bocchi() {
		Events.on(EventType.ContentInitEvent.class, contentInitEvent -> {
			bocchiRegion = Core.atlas.find("bocchi-melt");
			Blocks.forceProjector.buildType = () -> new ModifiedForceBuild((ForceProjector) Blocks.forceProjector);
		});
	}

	@Override
	public void init() {
		frameBuffer = new FrameBuffer(Pixmap.Format.rgba8888, 2, 2, false, true);
		Log.info("aawawa");
		Events.run(EventType.Trigger.preDraw, () -> {
			frameBuffer.resize(Core.graphics.getWidth(), Core.graphics.getHeight());
		});
	}


}
