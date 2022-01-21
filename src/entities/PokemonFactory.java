package entities;

import common.PokemonStats;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

public final class PokemonFactory {
    // Pattern: Singleton
    private static PokemonFactory factory;
    public PokemonFactory() {}
    public static PokemonFactory generateFactory() {
        if(factory == null)
            factory = new PokemonFactory();
        return factory;
    }


    // TODO: Populate for all the types
    public Pokemon createPokemon(String name) {
        return switch (name) {
            case PokemonStats.PIKACHU_NAME -> newPokemon(name);
            default -> null;
        };
    }

    private Pokemon newPokemon(String name) {
        List<Field> fields = Arrays.stream(PokemonStats.class.getDeclaredFields())
                .filter(f -> Modifier.isPublic(f.getModifiers()) &&
                             f.getName().toUpperCase().contains(name.toUpperCase()))
                .collect(Collectors.toList());

        Map<String, Object> fieldObjectMap = new HashMap<>();
        for (Field field : fields) {
            try {
                fieldObjectMap.put(field.getName(), field.get(PokemonStats.class));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        String extractedName = (String) extractFieldValues(fieldObjectMap, "NAME");
        Integer extractedHP = (Integer) extractFieldValues(fieldObjectMap, "HP");
        Integer extractedNormalAttack = (Integer) extractFieldValues(fieldObjectMap, "NORMAL_ATTACK");
        Integer extractedSpecialAttack = (Integer) extractFieldValues(fieldObjectMap, "SPECIAL_ATTACK");
        Integer extractedNormalDefense = (Integer) extractFieldValues(fieldObjectMap, "NORMAL_DEFENSE");
        Integer extractedSpecialDefense = (Integer) extractFieldValues(fieldObjectMap, "SPECIAL_DEFENSE");
        Ability extractedFirstAbility = (Ability) extractFieldValues(fieldObjectMap, "FIRST_ABILITY");
        Ability extractedSecondAbility = (Ability) extractFieldValues(fieldObjectMap, "SECOND_ABILITY");

        return new Pokemon.PokemonBuilder(extractedName)
                .HP(extractedHP)
                .attack(extractedNormalAttack)
                .specialAttack(extractedSpecialAttack)
                .defense(extractedNormalDefense)
                .specialDefense(extractedSpecialDefense)
                .firstAbility(extractedFirstAbility)
                .secondAbility(extractedSecondAbility)
                .build();

    }

    private Object extractFieldValues(Map<String, Object> fieldObjectMap, String keyString) {
        return fieldObjectMap.entrySet().stream()
                .filter(entry -> entry.getKey().contains(keyString.toUpperCase()))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList()).get(0);
    }
}
