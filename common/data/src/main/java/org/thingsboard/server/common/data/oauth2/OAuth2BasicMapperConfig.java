/**
 * Copyright © 2020 SOLTEKNO.COM
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
package org.thingsboard.server.common.data.oauth2;

import lombok.*;

@Builder(toBuilder = true)
@EqualsAndHashCode
@Data
@ToString
public class OAuth2BasicMapperConfig {
    private final String emailAttributeKey;
    private final String firstNameAttributeKey;
    private final String lastNameAttributeKey;
    private final TenantNameStrategyType tenantNameStrategy;
    private final String tenantNamePattern;
    private final String customerNamePattern;
    private final String defaultDashboardName;
    private final boolean alwaysFullScreen;
}
