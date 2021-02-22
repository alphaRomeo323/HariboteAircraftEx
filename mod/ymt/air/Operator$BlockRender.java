package mod.ymt.air;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderManager;

public interface BlockRender {
  boolean hasSpecialRender();
  
  void renderBlock(RenderBlocks paramRenderBlocks, BlockData paramBlockData);
  
  void renderBlockSpecial(RenderManager paramRenderManager, RenderBlocks paramRenderBlocks, BlockData paramBlockData);
}


/* Location:              D:\Downloads\HariboteAirCraft_172v3.zip!\mod\ymt\air\Operator$BlockRender.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */