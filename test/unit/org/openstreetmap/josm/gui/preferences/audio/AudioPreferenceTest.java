// License: GPL. For details, see LICENSE file.
package org.openstreetmap.josm.gui.preferences.audio;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.openstreetmap.josm.gui.preferences.PreferenceSetting;
import org.openstreetmap.josm.gui.preferences.PreferencesTestUtils;
import org.openstreetmap.josm.spi.preferences.Config;

/**
 * Unit tests of {@link AudioPreference} class.
 */
@Execution(ExecutionMode.SAME_THREAD)
class AudioPreferenceTest {
	
	private boolean originalMenuInvisible;

    @BeforeEach
    void setUp() {
    	originalMenuInvisible = Config.getPref().getBoolean("audio.menuinvisible", false);
    }

    @AfterEach
    void tearDown() {
    	Config.getPref().putBoolean("audio.menuinvisible", originalMenuInvisible);
    }
    
    
    /**
     * Unit test of {@link AudioPreference#AudioPreference}.
     */
    @Test
    void testAudioPreference_ShouldCreateValidInstance() {
    	final PreferenceSetting preference = new AudioPreference.Factory().createPreferenceSetting();
    	assertNotNull(preference, "The factory should create a valid AudioPreference instance.");
    }

    /**
     * Unit test of {@link AudioPreference#addGui} when the menu is invisible.
     */
    @Test
    void testAddGui_WhenMenuInvisible_ShouldExecuteWithoutException() {
        Config.getPref().putBoolean("audio.menuinvisible", true);
        
        assertDoesNotThrow(() -> 
            PreferencesTestUtils.doTestPreferenceSettingAddGui(new AudioPreference.Factory(), null),
            "Should not throw an exception when menu is invisible."
        );
    }
    
    /**
     * Unit test of {@link AudioPreference#addGui} when the menu is visible.
     */
    @Test
    void testAddGui_WhenMenuVisible_ShouldExecuteWithoutException() {
        Config.getPref().putBoolean("audio.menuinvisible", false);
        
        assertDoesNotThrow(() -> 
            PreferencesTestUtils.doTestPreferenceSettingAddGui(new AudioPreference.Factory(), null),
            "Should not throw an exception when menu is visible."
        );
    }
}
