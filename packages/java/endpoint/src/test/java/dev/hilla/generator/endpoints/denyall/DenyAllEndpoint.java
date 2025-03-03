/*
 * Copyright 2000-2022 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package dev.hilla.generator.endpoints.denyall;

import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.Endpoint;

@Endpoint
@DenyAll
public class DenyAllEndpoint {

    public void shouldBeDenied() {
    }

    @PermitAll
    public void shouldBeDisplayed1() {
    }

    @RolesAllowed("test")
    public void shouldBeDisplayed2() {
    }

    @AnonymousAllowed
    public void shouldBeDisplayed3() {
    }
}
