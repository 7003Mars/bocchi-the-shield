package me.mars.bocchi

import arc.Core
import arc.Events
import arc.func.Prov
import arc.graphics.Color
import arc.graphics.Gl
import arc.graphics.Pixmap
import arc.graphics.g2d.Draw
import arc.graphics.g2d.Fill
import arc.graphics.g2d.Lines
import arc.graphics.g2d.TextureRegion
import arc.graphics.gl.FrameBuffer
import arc.math.Mathf
import arc.util.Log
import arc.util.Tmp
import mindustry.Vars
import mindustry.content.Blocks
import mindustry.content.Items
import mindustry.game.EventType.ContentInitEvent
import mindustry.gen.Building
import mindustry.graphics.Layer
import mindustry.mod.Mod
import mindustry.type.Category
import mindustry.world.Block
import mindustry.world.blocks.defense.ForceProjector
import mindustry.world.blocks.defense.ForceProjector.ForceBuild;

lateinit var bocchiRegion: TextureRegion;

class Bocchi: Mod() {
    init {
        Events.on(ContentInitEvent::class.java) {
            this.loadLate();
        }
    }

    fun loadLate() {
        bocchiRegion = Core.atlas.find("bocchi-melt");
        bocchiRegion.flip(false, true);
        Blocks.forceProjector.buildType = Prov{ModifiedForceBuild(Blocks.forceProjector as ForceProjector)};
    }
}