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

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.thingsboard.server.common.data.BaseData;
import org.thingsboard.server.common.data.id.OAuth2ClientRegistrationId;
import org.thingsboard.server.common.data.id.OAuth2ClientRegistrationInfoId;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString
@NoArgsConstructor
public class OAuth2ClientRegistration extends BaseData<OAuth2ClientRegistrationId> {

    private OAuth2ClientRegistrationInfoId clientRegistrationId;
    private String domainName;
    private SchemeType domainScheme;

    public OAuth2ClientRegistration(OAuth2ClientRegistration clientRegistration) {
        super(clientRegistration);
        this.clientRegistrationId = clientRegistration.clientRegistrationId;
        this.domainName = clientRegistration.domainName;
        this.domainScheme = clientRegistration.domainScheme;
    }
}
