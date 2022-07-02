package com.dynamicschema.datafetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsTypeDefinitionRegistry;
import graphql.language.FieldDefinition;
import graphql.language.InputValueDefinition;
import graphql.language.ObjectTypeExtensionDefinition;
import graphql.language.TypeName;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@DgsComponent
public class DynamicTypeDefinitions {

    @DgsTypeDefinitionRegistry
    public TypeDefinitionRegistry registry() {
        TypeDefinitionRegistry typeDefinitionRegistry = new TypeDefinitionRegistry();

        ObjectTypeExtensionDefinition createRandomIntQuery = createRandomIntQuery();
        ObjectTypeExtensionDefinition createUserMutation = createUserMutation();

        typeDefinitionRegistry.add(createRandomIntQuery);
        typeDefinitionRegistry.add(createUserMutation);

        return typeDefinitionRegistry;
    }

    @NotNull
    private ObjectTypeExtensionDefinition createRandomIntQuery() {
        InputValueDefinition inputValueDefinition = InputValueDefinition.newInputValueDefinition()
                .name("bound")
                .type(new TypeName("Int"))
                .build();

        return ObjectTypeExtensionDefinition.newObjectTypeExtensionDefinition().name("Query").fieldDefinition(
                FieldDefinition.newFieldDefinition().name("randomNumber").type(new TypeName("Int"))
                        .inputValueDefinition(inputValueDefinition)
                        .build()
        ).build();
    }

    @NotNull
    private ObjectTypeExtensionDefinition createUserMutation() {
        InputValueDefinition userNameInputValueDefinition = InputValueDefinition.newInputValueDefinition()
                .name("username")
                .type(new TypeName("String"))
                .build();

        InputValueDefinition passwordInputValueDefinition = InputValueDefinition.newInputValueDefinition()
                .name("password")
                .type(new TypeName("String"))
                .build();

        List<InputValueDefinition> inputValueDefinitions = List.of(userNameInputValueDefinition, passwordInputValueDefinition);

        return ObjectTypeExtensionDefinition.newObjectTypeExtensionDefinition().name("Mutation").fieldDefinition(
                FieldDefinition.newFieldDefinition().name("createUser").type(new TypeName("User"))
                        .inputValueDefinitions(inputValueDefinitions)
                        .build()
        ).build();
    }
}
