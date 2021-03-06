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
package griffon.core.editors

import spock.lang.Specification
import spock.lang.Unroll

import java.beans.PropertyEditorManager

@Unroll
class PropertyEditorResolverSpec extends Specification {
    void "PropertyEditor for type #type should be #editorClass"() {
        setup:
        PropertyEditorManager.registerEditor(String, StringPropertyEditor)

        expect:
        PropertyEditorResolver.findEditor(type)?.class == editorClass

        where:
        type    | editorClass
        Numbers | EnumPropertyEditor
        String  | StringPropertyEditor
        Object  | null
    }
}
