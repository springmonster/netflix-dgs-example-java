package com.ut.graphqldgs;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsRuntimeWiring;
import graphql.language.StringValue;
import graphql.schema.*;
import graphql.schema.idl.RuntimeWiring;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;

@DgsComponent
public class InstantScalar {

    @DgsRuntimeWiring
    public RuntimeWiring.Builder addScalar(RuntimeWiring.Builder builder) {
        Coercing<Instant, String> instantStringCoercing = new Coercing<Instant, String>() {
            @Override
            public String serialize(@NotNull Object o) throws CoercingSerializeException {
                if (o instanceof Instant) {
                    return ((Instant) o).toString();
                } else {
                    throw new CoercingSerializeException("Not a valid Instant");
                }
            }

            @Override
            public @NotNull
            Instant parseValue(@NotNull Object o) throws CoercingParseValueException {
                return Instant.parse((CharSequence) o);
            }

            @Override
            public @NotNull
            Instant parseLiteral(@NotNull Object o) throws CoercingParseLiteralException {
                if (o instanceof StringValue) {
                    return Instant.parse(((StringValue) o).getValue());
                }
                throw new CoercingSerializeException("Not a valid Instant");
            }
        };

        return builder.scalar(GraphQLScalarType.newScalar()
                .name("Instant").description("java.time.Instant")
                .coercing(instantStringCoercing).build());
    }
}
