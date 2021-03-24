/*******************************************************************************
 * Copyright 2013 M. Chris Golledge
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package golledge.empire.game;

import java.util.ArrayList;
import java.util.LinkedList;

/* Every turn of the game has fleets put into a bucket.
 * During fleet processing the list of fleets is processed one bucket
 * at a time. 
 */
public final class FleetQueue {

	private ArrayList<LinkedList<Fleet>> buckets;

	public FleetQueue(int numBuckets) {
		buckets = new ArrayList<LinkedList<Fleet>>();
		for (int bucket = 1; bucket <= numBuckets; bucket++)
			buckets.add(new LinkedList<Fleet>());
	}

	// no current use for this method
	public void addBucket() {
		buckets.add(new LinkedList<Fleet>());
	}

	public LinkedList<Fleet> getArrivals(final int year) {
		return buckets.get(year - 2);
	}

	public void removeBucket(final int index) {
		buckets.remove(index);
	}

	public int size() {
		return buckets.size();
	}

	public void addFleet(Fleet f, int year) {
		// Buckets are 0-based and years are 1-based. Plus, fleets take at least
		// 1 year to get anywhere; there is no bucket needed for year 1.
		// Hence, index of bucket == year - 2
		buckets.get(year - 2).add(f);
	}
}
