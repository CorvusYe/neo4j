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
package org.neo4j.kernel.api.exceptions.index;

import org.junit.jupiter.api.Test;

import org.neo4j.kernel.api.StatementConstants;
import org.neo4j.storageengine.api.schema.LabelSchemaDescriptor;
import org.neo4j.storageengine.api.schema.SchemaDescriptorFactory;
import org.neo4j.values.storable.Value;
import org.neo4j.values.storable.ValueTuple;
import org.neo4j.values.storable.Values;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.neo4j.common.TokenNameLookup.idTokenNameLookup;

class IndexEntryConflictExceptionTest
{
    private static final int labelId = 1;
    private static final Value value = Values.of( "hi" );

    @Test
    void shouldMakeEntryConflicts()
    {
        LabelSchemaDescriptor schema = SchemaDescriptorFactory.forLabel( labelId, 2 );
        IndexEntryConflictException e = new IndexEntryConflictException( 0L, 1L, value );

        assertThat( e.evidenceMessage( idTokenNameLookup, schema ),
                equalTo( "Both Node(0) and Node(1) have the label `label[1]` and property `property[2]` = 'hi'" ) );
    }

    @Test
    void shouldMakeEntryConflictsForOneNode()
    {
        LabelSchemaDescriptor schema = SchemaDescriptorFactory.forLabel( labelId, 2 );
        IndexEntryConflictException e = new IndexEntryConflictException( 0L, StatementConstants.NO_SUCH_NODE, value );

        assertThat( e.evidenceMessage( idTokenNameLookup, schema ),
                equalTo( "Node(0) already exists with label `label[1]` and property `property[2]` = 'hi'" ) );
    }

    @Test
    void shouldMakeCompositeEntryConflicts()
    {
        LabelSchemaDescriptor schema = SchemaDescriptorFactory.forLabel( labelId, 2, 3, 4 );
        ValueTuple values = ValueTuple.of( true, "hi", new long[]{6L, 4L} );
        IndexEntryConflictException e = new IndexEntryConflictException( 0L, 1L, values );

        assertThat( e.evidenceMessage( idTokenNameLookup, schema ),
                equalTo( "Both Node(0) and Node(1) have the label `label[1]` " +
                        "and properties `property[2]` = true, `property[3]` = 'hi', `property[4]` = [6, 4]" ) );
    }
}
