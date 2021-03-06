/*
 * Copyright 2013 Proofpoint Inc.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.kairosdb.core.datastore;

import org.kairosdb.core.aggregator.Aggregator;
import org.kairosdb.core.groupby.GroupBy;
import org.kairosdb.util.Preconditions;

import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

public class QueryMetric implements DatastoreMetricQuery
{
	private long startTime;
	private long endTime = -1;
	private int cacheTime;
	private String name;
	private Map<String, String> tags = new HashMap<String, String>();
	private List<GroupBy> groupBys = new ArrayList<GroupBy>();
	private List<Aggregator> aggregators;

	public QueryMetric(long start_time, int cacheTime, String name)
	{
		this.aggregators = new ArrayList<Aggregator>();
		this.startTime = start_time;
		this.cacheTime = cacheTime;
		this.name = Preconditions.checkNotNullOrEmpty(name);
	}

	public QueryMetric addAggregator(Aggregator aggregator)
	{
		checkNotNull(aggregator);

		this.aggregators.add(aggregator);
		return (this);
	}

	public QueryMetric setTags(Map<String, String> tags)
	{
		this.tags = new HashMap<String, String>(tags);
		return this;
	}

	public QueryMetric addTag(String name, String value)
	{
		this.tags.put(name, value);
		return this;
	}

	@Override
	public String getName()
	{
		return name;
	}

	public List<Aggregator> getAggregators()
	{
		return aggregators;
	}

	@Override
	public SortedMap<String, String> getTags()
	{
		return new TreeMap<String, String>(tags);
	}

	@Override
	public long getStartTime()
	{
		return startTime;
	}

	@Override
	public long getEndTime()
	{
		if (endTime > -1)
			return endTime;
		return System.currentTimeMillis();
	}

	public int getCacheTime()
	{
		return cacheTime;
	}

	public void setEndTime(long endTime)
	{
		this.endTime = endTime;
	}

	public List<GroupBy> getGroupBys()
	{
		return Collections.unmodifiableList(groupBys);
	}

	public void addGroupBy(GroupBy groupBy)
	{
		this.groupBys.add(groupBy);
	}
}