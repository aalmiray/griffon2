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
package org.codehaus.griffon.runtime.util;

import griffon.util.CompositeResourceBundleBuilder;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Provider;
import java.util.ResourceBundle;

import static griffon.util.GriffonNameUtils.requireNonBlank;
import static java.util.Objects.requireNonNull;

/**
 * @author Andres Almiray
 * @since 2.0.0
 */
public class ResourceBundleProvider implements Provider<ResourceBundle> {
    private final String basename;

    private CompositeResourceBundleBuilder resourceBundleBuilder;

    public ResourceBundleProvider(@Nonnull String basename) {
        this.basename = requireNonBlank(basename, "Argument 'basename' cannot be blank");
    }

    @Inject
    public void setResourceBundleBuilder(@Nonnull CompositeResourceBundleBuilder resourceBundleBuilder) {
        this.resourceBundleBuilder = requireNonNull(resourceBundleBuilder, "Argument 'resourceBundleBuilder' cannot be null");
    }

    @Override
    public ResourceBundle get() {
        return resourceBundleBuilder.create(basename);
    }
}
