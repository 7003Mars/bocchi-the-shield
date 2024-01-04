package me.mars.bocchi;

import arc.graphics.Color;
import arc.graphics.Gl;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.graphics.gl.FrameBuffer;
import arc.math.Mathf;
import mindustry.Vars;
import mindustry.graphics.Layer;
import mindustry.graphics.Shaders;
import mindustry.world.blocks.defense.ForceProjector;

import static me.mars.bocchi.Bocchi.bocchiRegion;

public class ModifiedForceBuild extends ForceProjector.ForceBuild {
	static FrameBuffer frameBuffer;

	ModifiedForceBuild(ForceProjector projector) {
		projector.super();
	}

	@Override
	public void drawShield() {
		if (broken) return;
		if (!Vars.renderer.animateShields) {
			super.drawShield();
			return;
		}
		float boosted = this.realRadius();
		float radius = this.proj().radius;
		Draw.draw(Layer.shields, () -> {
			// Shield draw
			Draw.color(this.team.color, Color.white, Mathf.clamp(this.hit));
			Fill.poly(this.x, this.y, 6, boosted);
		});
		Draw.draw(Layer.shields+0.01f, () -> {
			frameBuffer.begin(Color.clear);
			Gl.clear(Gl.stencilBufferBit);
			float drawRad = Math.max(boosted, radius);
			Draw.stencil(() -> Fill.poly(this.x, this.y, 6, boosted), () -> {
				Draw.color(Color.white);
				Draw.rect(bocchiRegion, this.x, this.y, drawRad*2f, drawRad*2f);
				Lines.rect(this.x-drawRad, this.x-drawRad, drawRad*2f, drawRad*2f);
			});
			Lines.stroke(1f);
			frameBuffer.end();
			frameBuffer.blit(Shaders.screenspace);
			Draw.reset();
			Draw.color(this.team.color);
			Lines.poly(this.x, this.y, 6, boosted);
			Lines.stroke(1f);
		});
		// Image draw

			// Debug draws down here
//			Draw.color(Color.blue);
//			Lines.poly(this.x, this.y, 6, radius);
//			Draw.color(Color.green);
//			Lines.poly(this.x, this.y, 6, boosted);
//			Draw.color(Color.violet);
//			float drawRad = Math.max(boosted, radius);
//			Lines.rect(this.x-drawRad, this.y-drawRad, drawRad*2f, drawRad*2f);
//                Draw.color(Color.purple);
//                Lines.rect(this.x-this.frameBuffer./2f, this.y-this.frameBuffer.height/2f,
//                    this.frameBuffer.width.toFloat(), this.frameBuffer.height.toFloat());                // Debug draws down here
//                Draw.color(Color.blue);
//                Lines.poly(this.x, this.y, 6, radius);
//                Draw.color(Color.green);
//                Lines.poly(this.x, this.y, 6, boosted);
//                Draw.color(Color.violet);
//                val drawRad: Float = Math.max(boosted, radius);
//                Lines.rect(this.x-drawRad, this.y-drawRad, drawRad*2f, drawRad*2f);
//                Draw.color(Color.purple);
//                Lines.rect(this.x-this.frameBuffer.width/2f, this.y-this.frameBuffer.height/2f,
//                    this.frameBuffer.width.toFloat(), this.frameBuffer.height.toFloat());
	}
	private ForceProjector proj() {
		return (ForceProjector) this.block;
	}

}