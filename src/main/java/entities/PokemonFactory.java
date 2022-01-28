package entities;

import common.Constants;
import common.ItemStats;
import common.PokemonStats;
import utils.DeepCopy;
import utils.GetFieldsOfClass;

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
        Map<String, Object> pokemonStatsMap = GetFieldsOfClass.getFieldObjectMap(PokemonStats.class, pokemonName);
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

        // Neutrel1 and Neutrel2 can't be used
        if (PokemonStats.NEUTREL1_NAME.equals(extractedName)) {
            System.err.println(Constants.ERROR_LOG + "You can't use Neutrel1");
            return null;
        }
        if (PokemonStats.NEUTREL2_NAME.equals(extractedName)) {
            System.err.println(Constants.ERROR_LOG + "You can't use Neutrel2");
            return null;
        }

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
            Map<String, Object> itemStatsMap = GetFieldsOfClass.getFieldObjectMap(ItemStats.class, itemName);
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

    private Object extractFieldValues(Map<String, Object> fieldObjectMap, String keyString) {
        return fieldObjectMap.entrySet().stream()
                .filter(entry -> entry.getKey().contains(keyString.toUpperCase()))
                .map(Map.Entry::getValue)
                .toList().get(0);
    }
}
