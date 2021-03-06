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
package griffon.core.controller;

import griffon.core.artifact.GriffonController;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

/**
 * @author Andres Almiray
 * @since 2.0.0
 */
public interface ActionManager {
    String ACTION = "Action";

    @Nonnull
    Map<String, Action> actionsFor(@Nonnull GriffonController controller);

    @Nullable
    Action actionFor(@Nonnull GriffonController controller, @Nonnull String actionName);

    void createActions(@Nonnull GriffonController controller);

    @Nonnull
    String normalizeName(@Nonnull String actionName);

    void invokeAction(@Nonnull GriffonController controller, @Nonnull String actionName, Object... args);

    void addActionInterceptor(@Nonnull ActionInterceptor actionInterceptor);
}
