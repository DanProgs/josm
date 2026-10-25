// License: GPL. For details, see LICENSE file.
package org.openstreetmap.josm.gui.preferences.display;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.openstreetmap.josm.gui.preferences.PreferenceSetting;
import org.openstreetmap.josm.gui.preferences.PreferencesTestUtils;

/**
 * Unit tests of {@link DisplayPreference} class.
 */
@Execution(ExecutionMode.SAME_THREAD)
class DisplayPreferenceTest {
	
    /**
     * Unit test of {@link DisplayPreference#DisplayPreference}.
     */
    @Test
    void testDisplayPreference_ShouldCreateValidInstance() {
    	final PreferenceSetting preferenceSetting = new DisplayPreference.Factory().createPreferenceSetting();
    	
    	assertNotNull(preferenceSetting, () -> "The factory should not return null.");
        assertInstanceOf(DisplayPreference.class, preferenceSetting, 
                () -> "The factory should create a valid DisplayPreference instance.");
    }

    /**
     * Smoke test for {@link DisplayPreference#addGui} without verifying tab integration.
     */
    @Test
    void testAddGui_WithoutParentClass_ShouldNotThrowException() {
        assertDoesNotThrow(() -> 
        PreferencesTestUtils.doTestPreferenceSettingAddGui(new DisplayPreference.Factory(), null),
        "Should not throw an exception when parent class is null (e.g. when menu is invisible or not registered).");
    }
}
