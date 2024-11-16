package com.cg.util;

import com.cg.exceptions.InternalApplicationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class SetUtilTest {

    private Set<String> testSet;

    @Before
    public void setup() {
        testSet = new HashSet<>();
    }

    @Test
    public void getRandomElementAndPop_ThrowsException_IfSetIsEmpty() {
        assertThrows(InternalApplicationException.class, () -> SetUtil.getRandomElementAndPop(testSet));
    }

    @Test
    public void getRandomElementAndPop_ReturnsRandomElement_IfSetIsNotEmpty() {
        testSet.add("element1");
        testSet.add("element2");
        String element = SetUtil.getRandomElementAndPop(testSet);
        assertFalse(testSet.contains(element));
    }

    @Test
    public void addToSetIfNotNull_AddsElementToSet_IfElementIsNotNull() {
        SetUtil.addToSetIfNotNull(testSet, "element");
        assertTrue(testSet.contains("element"));
    }

    @Test
    public void addToSetIfNotNull_DoesNotAddElementToSet_IfElementIsNull() {
        SetUtil.addToSetIfNotNull(testSet, null);
        assertFalse(testSet.contains(null));
    }
}
