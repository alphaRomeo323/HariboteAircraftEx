/**
 * Copyright 2013 Yamato
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package mod.ymt.air.cmn;

import java.lang.ref.Reference;
import java.util.Iterator;
import net.minecraft.entity.Entity;

/**
 * @author Yamato
 *
 */
public class WeakEntityCollection<E extends Entity> extends WeakCollection<E> {
	@Override
	public Iterator<E> iterator() {
		final Iterator<Reference<E>> iter = inner.iterator();
		return new Iterator<E>() {
			@Override
			public boolean hasNext() {
				return iter.hasNext();
			}

			@Override
			public E next() {
				Reference<E> ref = iter.next();
				E result = ref.get();
				if (result == null || result.isDead) {
					iter.remove();
					logger.fine("delete DeadEntity");
					result = null;
				}
				return result;
			}

			@Override
			public void remove() {
				iter.remove();
			}
		};
	}
}
