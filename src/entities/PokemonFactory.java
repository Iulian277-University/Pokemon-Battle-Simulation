package entities;

import common.ItemStats;
import common.PokemonStats;
import utils.DeepCopy;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public final class PokemonFactory {
    // Pattern: Singleton
    private static PokemonFactory factory;
    public PokemonFactory() {}
    public static PokemonFactory generateFactory() {
        if(factory == null)
            factory = new PokemonFactory();
        return factory;
    }

    public Pokemon createPokemon(String pokemonName, List<String> itemsName) {
        Map<String, Object> pokemonStatsMap = getFieldObjectMap(PokemonStats.class, pokemonName);
        if (pokemonStatsMap.isEmpty())
            return null; // Pokemon doesn't exist in the db

        // Extract pokemon's attributes
        String  extractedName           = (String)  extractFieldValues(pokemonStatsMap, "NAME");
        Integer extractedHP             = (Integer) extractFieldValues(pokemonStatsMap, "HP");
        Integer extractedNormalAttack   = (Integer) extractFieldValues(pokemonStatsMap, "NORMAL_ATTACK");
        Integer extractedSpecialAttack  = (Integer) extractFieldValues(pokemonStatsMap, "SPECIAL_ATTACK");
        Integer extractedNormalDefense  = (Integer) extractFieldValues(pokemonStatsMap, "NORMAL_DEFENSE");
        Integer extractedSpecialDefense = (Integer) extractFieldValues(pokemonStatsMap, "SPECIAL_DEFENSE");
        Ability extractedFirstAbility   = (Ability) extractFieldValues(pokemonStatsMap, "FIRST_ABILITY");
        Ability extractedSecondAbility  = (Ability) extractFieldValues(pokemonStatsMap, "SECOND_ABILITY");

        Pokemon.PokemonBuilder pokemonBuilder = new Pokemon.PokemonBuilder(extractedName)
                .HP(extractedHP)
                .attack(extractedNormalAttack)
                .specialAttack(extractedSpecialAttack)
                .defense(extractedNormalDefense)
                .specialDefense(extractedSpecialDefense)
                .firstAbility(DeepCopy.deepCopy(extractedFirstAbility))
                .secondAbility(DeepCopy.deepCopy(extractedSecondAbility));

        // Extract item's attributes
        for (String itemName: itemsName) {
            Map<String, Object> itemStatsMap = getFieldObjectMap(ItemStats.class, itemName);
            if (itemStatsMap.isEmpty())
                continue; // Item doesn't exist in the db

            extractedName           = (String)  extractFieldValues(itemStatsMap, "NAME");
            extractedHP             = (Integer) extractFieldValues(itemStatsMap, "HP");
            extractedNormalAttack   = (Integer) extractFieldValues(itemStatsMap, "NORMAL_ATTACK");
            extractedSpecialAttack  = (Integer) extractFieldValues(itemStatsMap, "SPECIAL_ATTACK");
            extractedNormalDefense  = (Integer) extractFieldValues(itemStatsMap, "NORMAL_DEFENSE");
            extractedSpecialDefense = (Integer) extractFieldValues(itemStatsMap, "SPECIAL_DEFENSE");

            pokemonBuilder.addItem(new Item.ItemBuilder(extractedName)
                    .HP(extractedHP)
                    .attack(extractedNormalAttack)
                    .specialAttack(extractedSpecialAttack)
                    .defense(extractedNormalDefense)
                    .specialDefense(extractedSpecialDefense)
                    .build());
        }

        return pokemonBuilder.build();
    }


    private Map<String, Object> getFieldObjectMap(Class<?> cls, String name) {

        List<Field> fields = Arrays.stream(cls.getDeclaredFields())
                .filter(f -> Modifier.isPublic(f.getModifiers()) &&
                        f.getName().toUpperCase().contains(name.toUpperCase()))
                .toList();

        if (fields.isEmpty()) {
            System.out.println("Field '" + name + "' doesn't exist");
            return Collections.emptyMap();
        }

        Map<String, Object> fieldObjectMap = new HashMap<>();
        for (Field field: fields) {
            try {
                fieldObjectMap.put(field.getName(), field.get(cls));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return fieldObjectMap;
    }

    private Object extractFieldValues(Map<String, Object> fieldObjectMap, String keyString) {
        return fieldObjectMap.entrySet().stream()
                .filter(entry -> entry.getKey().contains(keyString.toUpperCase()))
                .map(Map.Entry::getValue)
                .toList().get(0);
    }
}
