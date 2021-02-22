/*    */ package mod.ymt.air.cmn;
/*    */ 
/*    */ import java.lang.ref.Reference;
/*    */ import java.util.Iterator;
/*    */ import net.minecraft.entity.Entity;
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
/*    */ class null
/*    */   implements Iterator<E>
/*    */ {
/*    */   public boolean hasNext() {
/* 33 */     return iter.hasNext();
/*    */   }
/*    */ 
/*    */   
/*    */   public E next() {
/* 38 */     Reference<E> ref = iter.next();
/* 39 */     Entity entity = (Entity)ref.get();
/* 40 */     if (entity == null || entity.field_70128_L) {
/* 41 */       iter.remove();
/* 42 */       WeakCollection.logger.fine("delete DeadEntity");
/* 43 */       entity = null;
/*    */     } 
/* 45 */     return (E)entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public void remove() {
/* 50 */     iter.remove();
/*    */   }
/*    */ }


/* Location:              D:\Downloads\HariboteAirCraft_172v3.zip!\mod\ymt\air\cmn\WeakEntityCollection$1.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */