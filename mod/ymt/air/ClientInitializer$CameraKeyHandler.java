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
/*    */ public class CameraKeyHandler
/*    */   extends SimpleKeyHandler
/*    */ {
/*    */   public CameraKeyHandler(KeyBinding kb) {
/* 94 */     super(new KeyBinding[] { kb });
/*    */   }
/*    */ 
/*    */   
/*    */   public void onKeyDown(int index, KeyBinding kb) {
/* 99 */     ThirdPersonCameraController.getInstance().turn();
/*    */   }
/*    */ }


/* Location:              D:\Downloads\HariboteAirCraft_172v3.zip!\mod\ymt\air\ClientInitializer$CameraKeyHandler.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */