/*
 * Copyright (c) 2002-2019 "Neo4j,"
 * Neo4j Sweden AB [http://neo4j.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.kernel.api.impl.fulltext;

import org.apache.lucene.analysis.Analyzer;

import org.neo4j.common.TokenNameLookup;
import org.neo4j.internal.schema.IndexProviderDescriptor;
import org.neo4j.internal.schema.IndexType;
import org.neo4j.internal.schema.SchemaDescriptor;
import org.neo4j.storageengine.api.StorageIndexReference;

class FulltextIndexDescriptor implements StorageIndexReference
{
    private final StorageIndexReference descriptor;
    private final String[] propertyNames;
    private final Analyzer analyzer;
    private final String analyzerName;
    private final boolean eventuallyConsistent;

    FulltextIndexDescriptor( StorageIndexReference descriptor, String[] propertyNames, Analyzer analyzer, String analyzerName, boolean eventuallyConsistent )
    {
        this.descriptor = descriptor;
        this.propertyNames = propertyNames;
        this.analyzer = analyzer;
        this.analyzerName = analyzerName;
        this.eventuallyConsistent = eventuallyConsistent;
    }

    String[] propertyNames()
    {
        return propertyNames;
    }

    public Analyzer analyzer()
    {
        return analyzer;
    }

    String analyzerName()
    {
        return analyzerName;
    }

    @Override
    public boolean isEventuallyConsistent()
    {
        return eventuallyConsistent;
    }

    @Override
    public boolean isUnique()
    {
        return descriptor.isUnique();
    }

    @Override
    public boolean hasUserSuppliedName()
    {
        return descriptor.hasUserSuppliedName();
    }

    @Override
    public String name()
    {
        return descriptor.name();
    }

    @Override
    public String userDescription( TokenNameLookup tokenNameLookup )
    {
        return descriptor.userDescription( tokenNameLookup );
    }

    @Override
    public IndexType getIndexType()
    {
        return descriptor.getIndexType();
    }

    @Override
    public SchemaDescriptor schema()
    {
        return descriptor.schema();
    }

    @Override
    public String providerKey()
    {
        return descriptor.providerKey();
    }

    @Override
    public String providerVersion()
    {
        return descriptor.providerVersion();
    }

    @Override
    public FulltextIndexDescriptor withIndexProvider( IndexProviderDescriptor indexProvider )
    {
        return new FulltextIndexDescriptor( descriptor.withIndexProvider( indexProvider ), propertyNames, analyzer, analyzerName, eventuallyConsistent );
    }

    @Override
    public FulltextIndexDescriptor withSchemaDescriptor( SchemaDescriptor schema )
    {
        return new FulltextIndexDescriptor( descriptor.withSchemaDescriptor( schema ), propertyNames, analyzer, analyzerName, eventuallyConsistent );
    }

    @Override
    public long indexReference()
    {
        return descriptor.indexReference();
    }

    @Override
    public long getId()
    {
        return descriptor.getId();
    }

    @Override
    public boolean hasOwningConstraintReference()
    {
        return descriptor.hasOwningConstraintReference();
    }

    @Override
    public long owningConstraintReference()
    {
        return descriptor.owningConstraintReference();
    }
}