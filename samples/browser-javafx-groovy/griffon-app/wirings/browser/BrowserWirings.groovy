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
package browser

import javafx.beans.value.ChangeListener

// The following statements must be executed inside the UI thread
// as this script is built outside of the UI thread as it's not
// marked as a View script

application.getUIThreadManager().runInsideUIAsync { ->
    // use the (Closure as Type) notation in order to gain access to
    // binding variables. Anonymous class definition will fail as the
    // instance won't have access to builder binding variables

    browser.engine.loadWorker.messageProperty().addListener({ observableValue, oldValue, newValue ->
        builder.model.status = newValue
        // update history and textField value if a link
        // was clicked. Sadly this check is brittle as we
        // rely on 'parsing' the message value *sigh*
        if (newValue.startsWith('Loading http')) {
            String url = newValue[8..-1]
            builder.urlField.text = url
        }
    } as ChangeListener)

    browser.engine.loadWorker.progressProperty().addListener({ observableValue, oldValue, newValue ->
        if (newValue > 0.0d && newValue < 1.0d) {
            builder.model.status = "Loading: ${(newValue * 100).intValue()} %"
        }
    } as ChangeListener)
}