/*
 * Copyright 2008-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package griffon.javafx.editors;

import griffon.core.editors.AbstractPropertyEditor;
import griffon.metadata.PropertyEditorFor;
import javafx.geometry.Rectangle2D;

import java.util.List;
import java.util.Map;

import static griffon.util.GriffonNameUtils.isBlank;

/**
 * @author Andres Almiray
 */
@PropertyEditorFor(Rectangle2D.class)
public class Rectangle2DPropertyEditor extends AbstractPropertyEditor {
    public String getAsText() {
        if (null == getValue()) return null;
        Rectangle2D r = (Rectangle2D) getValue();
        return r.getMinX() + ", " + r.getMinY() + ", " + r.getWidth() + ", " + r.getHeight();
    }

    protected void setValueInternal(Object value) {
        if (null == value) {
            super.setValueInternal(null);
        } else if (value instanceof CharSequence) {
            handleAsString(String.valueOf(value));
        } else if (value instanceof List) {
            handleAsList((List) value);
        } else if (value instanceof Map) {
            handleAsMap((Map) value);
        } else if (value instanceof Rectangle2D) {
            super.setValueInternal(value);
        } else {
            throw illegalValue(value, Rectangle2D.class);
        }
    }

    private void handleAsString(String str) {
        if (isBlank(str)) {
            super.setValueInternal(null);
            return;
        }

        String[] parts = str.split(",");
        switch (parts.length) {
            case 4:
                double x = parseValue(parts[0]);
                double y = parseValue(parts[1]);
                double w = parseValue(parts[2]);
                double h = parseValue(parts[3]);
                super.setValueInternal(new Rectangle2D(x, y, w, h));
                break;
            default:
                throw illegalValue(str, Rectangle2D.class);
        }
    }

    private void handleAsList(List<?> list) {
        if (list.isEmpty()) {
            super.setValueInternal(null);
            return;
        }

        switch (list.size()) {
            case 4:
                double x = parseValue(list.get(0));
                double y = parseValue(list.get(1));
                double w = parseValue(list.get(2));
                double h = parseValue(list.get(3));
                super.setValueInternal(new Rectangle2D(x, y, w, h));
                break;
            default:
                throw illegalValue(list, Rectangle2D.class);
        }
    }

    private void handleAsMap(Map<?, ?> map) {
        if (map.isEmpty()) {
            super.setValueInternal(null);
            return;
        }

        double x = getMapValue(map, "x", 0);
        double y = getMapValue(map, "y", 0);
        double w = getMapValue(map, "width", 0);
        double h = getMapValue(map, "height", 0);
        super.setValueInternal(new Rectangle2D(x, y, w, h));
    }

    private double parseValue(Object value) {
        if (value instanceof CharSequence) {
            return parse(String.valueOf(value));
        } else if (value instanceof Number) {
            return parse((Number) value);
        }
        throw illegalValue(value, Rectangle2D.class);
    }

    private double parse(String val) {
        try {
            return Double.parseDouble(val.trim());
        } catch (NumberFormatException e) {
            throw illegalValue(val, Rectangle2D.class, e);
        }
    }

    private double parse(Number val) {
        return val.doubleValue();
    }

    private double getMapValue(Map<?, ?> map, String key, double defaultValue) {
        Object val = map.get(key);
        if (null == val) val = map.get(String.valueOf(key.charAt(0)));
        if (null == val) {
            return defaultValue;
        } else if (val instanceof CharSequence) {
            return parse(String.valueOf(val));
        } else if (val instanceof Number) {
            return parse((Number) val);
        }
        throw illegalValue(map, Rectangle2D.class);
    }
}
