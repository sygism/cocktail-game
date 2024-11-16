package com.cg.util;

import com.cg.exceptions.InternalApplicationException;

import java.util.Random;
import java.util.Set;

public class SetUtil {

    public static <T> T getRandomElementAndPop(Set<T> set) {
        try {
            T randomElement = set.stream()
                    .skip(new Random().nextInt(set.size()))
                    .findFirst()
                    .orElse(null);
            if (randomElement != null) {
                set.remove(randomElement);
            }
            return randomElement;
        } catch (IllegalArgumentException e) {
            throw new InternalApplicationException("out of hints");
        }
    }

    public static <T> void addToSetIfNotNull(Set<T> set, T element) {
        if (element != null) {
            set.add(element);
        }
    }
}
