/*     */ package mod.ymt.air.cmn;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Modifier;
/*     */ import sun.misc.Unsafe;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class UnsafeAccessorImpl
/*     */   implements Reflection.UnsafeAccessor
/*     */ {
/*     */   private Unsafe unsafe;
/*     */   
/*     */   private UnsafeAccessorImpl() {
/*     */     try {
/* 134 */       Field f = Unsafe.class.getDeclaredField("theUnsafe");
/* 135 */       f.setAccessible(true);
/* 136 */       this.unsafe = (Unsafe)f.get((Object)null);
/*     */     }
/* 138 */     catch (NoSuchFieldException e) {
/* 139 */       throw new UnsupportedOperationException(e);
/*     */     }
/* 141 */     catch (SecurityException e) {
/* 142 */       throw new UnsupportedOperationException(e);
/*     */     }
/* 144 */     catch (IllegalArgumentException e) {
/* 145 */       throw new UnsupportedOperationException(e);
/*     */     }
/* 147 */     catch (IllegalAccessException e) {
/* 148 */       throw new UnsupportedOperationException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void putObjectVolatile(Object obj, Field f, Object value) {
/* 154 */     boolean isStatic = Modifier.isStatic(f.getModifiers());
/* 155 */     obj = isStatic ? this.unsafe.staticFieldBase(f) : obj;
/* 156 */     if (obj == null) {
/* 157 */       throw new NullPointerException("object is null");
/*     */     }
/* 159 */     long offset = isStatic ? this.unsafe.staticFieldOffset(f) : this.unsafe.objectFieldOffset(f);
/* 160 */     this.unsafe.putObjectVolatile(obj, offset, value);
/*     */   }
/*     */   
/*     */   public static Reflection.UnsafeAccessor newInstance() {
/* 164 */     return new UnsafeAccessorImpl();
/*     */   }
/*     */ }


/* Location:              D:\Downloads\HariboteAirCraft_172v3.zip!\mod\ymt\air\cmn\Reflection$UnsafeAccessorImpl.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */