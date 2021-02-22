/*    */ package mod.ymt.air.cmn;
/*    */ 
/*    */ import java.lang.ref.Reference;
/*    */ import java.util.Iterator;
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
/*    */ class null
/*    */   implements Iterator<E>
/*    */ {
/*    */   public boolean hasNext() {
/* 56 */     return iter.hasNext();
/*    */   }
/*    */ 
/*    */   
/*    */   public E next() {
/* 61 */     Reference<E> ref = iter.next();
/* 62 */     E result = ref.get();
/* 63 */     if (result == null) {
/* 64 */       iter.remove();
/* 65 */       WeakCollection.logger.fine("delete Reference");
/*    */     } 
/* 67 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public void remove() {
/* 72 */     iter.remove();
/*    */   }
/*    */ }


/* Location:              D:\Downloads\HariboteAirCraft_172v3.zip!\mod\ymt\air\cmn\WeakCollection$1.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */