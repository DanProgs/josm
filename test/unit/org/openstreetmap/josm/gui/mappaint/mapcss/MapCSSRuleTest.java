package org.openstreetmap.josm.gui.mappaint.mapcss;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link MapCSSRule} verifying the contracts of
 * {@code equals}, {@code hashCode}, and {@code compareTo}.
 */
class MapCSSRuleTest {

    /**
     * Helper method to create test instances with a specific index.
     * Adjust this factory method to match the actual constructor of MapCSSRule.
     *
     * @param idx the declaration index to assign
     * @return a new {@link MapCSSRule} instance
     */
    private MapCSSRule createRuleWithIdx(int idx) {
        final Declaration decl = new Declaration(Collections.emptyList(), idx);
        return new MapCSSRule(Collections.emptyList(), decl);
    }

    /**
     * Verifies that {@link MapCSSRule#equals(Object)} fulfills the general contract:
     * reflexivity, symmetry, transitivity, and non-nullity.
     */
    @Test
    void testEqualsContract() {
        final MapCSSRule rule1 = createRuleWithIdx(10);
        final MapCSSRule rule2 = createRuleWithIdx(10);
        final MapCSSRule rule3 = createRuleWithIdx(10);
        final MapCSSRule differentRule = createRuleWithIdx(20);

        // Reflexivity: an object must equal itself
        assertEquals(rule1, rule1);

        // Symmetry: rule1.equals(rule2) == rule2.equals(rule1)
        assertEquals(rule1, rule2);
        assertEquals(rule2, rule1);

        // Transitivity: rule1 == rule2 and rule2 == rule3 => rule1 == rule3
        assertEquals(rule2, rule3);
        assertEquals(rule1, rule3);

        // Inequality for different index values
        assertNotEquals(rule1, differentRule);

        // Null and type safety
        assertNotEquals(null, rule1);
        assertNotEquals("A String", rule1);
    }

    /**
     * Verifies that equal objects produce identical hash codes
     * as required by {@link Object#hashCode()}.
     */
    @Test
    void testHashCodeConsistency() {
        final MapCSSRule rule1 = createRuleWithIdx(42);
        final MapCSSRule rule2 = createRuleWithIdx(42);

        // Equal objects must return the same hashCode
        assertEquals(rule1, rule2);
        assertEquals(rule1.hashCode(), rule2.hashCode());
    }

    /**
     * Verifies that {@link MapCSSRule#compareTo(MapCSSRule)} is consistent with {@code equals}
     * and orders elements properly.
     */
    @Test
    void testCompareToAndEqualsConsistency() {
        final MapCSSRule smaller = createRuleWithIdx(5);
        final MapCSSRule equal1 = createRuleWithIdx(10);
        final MapCSSRule equal2 = createRuleWithIdx(10);
        final MapCSSRule larger = createRuleWithIdx(15);

        // Consistency with equals: compareTo == 0 if and only if equals == true
        assertEquals(0, equal1.compareTo(equal2));
        assertEquals(equal1, equal2);

        // Verify natural ordering
        assertTrue(smaller.compareTo(larger) < 0);
        assertTrue(larger.compareTo(smaller) > 0);
    }

    /**
     * Ensures {@link MapCSSRule#compareTo(MapCSSRule)} handles extreme integer values
     * without suffering from integer overflow.
     */
    @Test
    void testCompareToNoIntegerOverflow() {
        final MapCSSRule minRule = createRuleWithIdx(Integer.MIN_VALUE);
        final MapCSSRule maxRule = createRuleWithIdx(Integer.MAX_VALUE);

        // Verify Integer.compare prevents sign-flipping overflow
        assertTrue(minRule.compareTo(maxRule) < 0);
        assertTrue(maxRule.compareTo(minRule) > 0);
    }
}
