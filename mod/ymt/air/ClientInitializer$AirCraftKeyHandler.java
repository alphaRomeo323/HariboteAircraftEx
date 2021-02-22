/*    */ package mod.ymt.air;
/*    */ 
/*    */ import mod.ymt.air.cmn.fml.SimpleKeyHandler;
/*    */ import net.minecraft.client.settings.KeyBinding;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AirCraftKeyHandler
/*    */   extends SimpleKeyHandler
/*    */ {
/*    */   public AirCraftKeyHandler(KeyBinding[] kb) {
/* 83 */     super(kb);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onKeyDown(int index, KeyBinding kb) {
/* 88 */     (AirCraftCore.getInstance()).net.sendKeyToServer((byte)index);
/*    */   }
/*    */ }


/* Location:              D:\Downloads\HariboteAirCraft_172v3.zip!\mod\ymt\air\ClientInitializer$AirCraftKeyHandler.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */