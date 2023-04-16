package me.mars.bocchi;

import arc.graphics.Color;
import arc.graphics.Gl;
import arc.graphics.Pixmap;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.graphics.g2d.TextureRegion;
import arc.graphics.gl.FrameBuffer;
import arc.math.Mathf;
import arc.util.Tmp;
import mindustry.Vars;
import mindustry.graphics.Layer;
import mindustry.world.blocks.defense.ForceProjector;

import static me.mars.bocchi.BocchiKt.bocchiRegion;

public class ModifiedForceBuild extends ForceProjector.ForceBuild {
	private FrameBuffer frameBuffer;

	ModifiedForceBuild(ForceProjector projector) {
		projector.super();
		int fboSize = Mathf.ceilPositive((projector.radius + projector.phaseRadiusBoost)*2f);
		this.frameBuffer = new FrameBuffer(Pixmap.Format.rgba8888, fboSize, fboSize, false, true);
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
			Tmp.m1.set(Draw.proj());
			float fbw = this.frameBuffer.getWidth(), fbh = this.frameBuffer.getHeight();
			this.frameBuffer.begin(Color.clear);
			Gl.clear(Gl.stencilBufferBit);
			Draw.proj(-fbw/2f, -fbh/2f, fbw, fbh);
			float drawRad = Math.max(boosted, radius);
			Draw.stencil(() -> Fill.poly(0f, 0f, 6, boosted), () -> {
				Draw.color(Color.white);
				Draw.rect(bocchiRegion, 0f, 0f, drawRad*2f, drawRad*2f);
				Lines.rect(-drawRad, -drawRad, drawRad*2f, drawRad*2f);
			});
			Lines.stroke(1f);
			this.frameBuffer.end();
			Draw.proj(Tmp.m1);
			Draw.reset();
			TextureRegion bufferTex = Draw.wrap(this.frameBuffer.getTexture());
			Draw.color();
			Draw.rect(bufferTex, this.x, this.y, bufferTex.width, bufferTex.height);
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

	@Override
	public void remove() {
		super.remove();
		this.frameBuffer.dispose();
	}

	private ForceProjector proj() {
		return (ForceProjector) this.block;
	}

}