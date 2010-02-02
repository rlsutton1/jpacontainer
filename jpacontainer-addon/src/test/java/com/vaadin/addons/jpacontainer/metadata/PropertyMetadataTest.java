/*
 * JPAContainer
 * Copyright (C) 2009 Oy IT Mill Ltd
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.vaadin.addons.jpacontainer.metadata;

import java.lang.reflect.Method;
import javax.persistence.Id;
import javax.persistence.Version;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test case for {@link PropertyMetadata}.
 *
 * @author Petter Holmström (IT Mill)
 * @since 1.0
 */
public class PropertyMetadataTest {

    // Dummy methods for testing
    
    @Id
    private String getDummy() {
        return "";
    }

    @Version
    private void setDummy(String value) {

    }

    @Test
    public void testReadOnlyProperty() throws Exception {
        Method getter = getClass().getDeclaredMethod("getDummy");
        PropertyMetadata property = new PropertyMetadata("dummy", String.class, getter, null);
        assertEquals("dummy", property.getName());
        assertSame(String.class, property.getType());
        assertArrayEquals(getter.getAnnotations(), property.getAnnotations());
        assertNotNull(property.getAnnotation(Id.class));
        assertNull(property.getAnnotation(Version.class));
        assertSame(getter, property.getter);
        assertNull(property.setter);
        assertFalse(property.isWritable());
    }

    @Test
    public void testWritableProperty() throws Exception {
        Method getter = getClass().getDeclaredMethod("getDummy");
        Method setter = getClass().getDeclaredMethod("setDummy", String.class);
        PropertyMetadata property = new PropertyMetadata("dummy", String.class, getter, setter);
        assertEquals("dummy", property.getName());
        assertSame(String.class, property.getType());
        assertArrayEquals(getter.getAnnotations(), property.getAnnotations());
        assertNotNull(property.getAnnotation(Id.class));
        assertNull(property.getAnnotation(Version.class));
        assertSame(getter, property.getter);
        assertSame(setter, property.setter);
        assertTrue(property.isWritable());
    }
}