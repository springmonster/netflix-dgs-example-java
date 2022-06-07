package com.postg.graphqldgs.scalars;

import com.netflix.graphql.dgs.DgsScalar;
import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;

import java.awt.Cursor;
import java.util.UUID;

@DgsScalar(name = "Cursor")
public class CursorScalar implements Coercing<Cursor, String> {
    @Override
    public String serialize(Object o) throws CoercingSerializeException {
        return o.toString();
    }

    @Override
    public Cursor parseValue(Object o) throws CoercingParseValueException {
        return Cursor.getDefaultCursor();
    }

    @Override
    public Cursor parseLiteral(Object input) throws CoercingParseLiteralException {
        return Cursor.getDefaultCursor();
    }
}
