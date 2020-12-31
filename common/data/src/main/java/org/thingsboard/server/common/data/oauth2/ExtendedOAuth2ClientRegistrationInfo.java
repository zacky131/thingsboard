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

@EqualsAndHashCode(callSuper = true)
@Data
public class ExtendedOAuth2ClientRegistrationInfo extends OAuth2ClientRegistrationInfo {

    private String domainName;
    private SchemeType domainScheme;

    public ExtendedOAuth2ClientRegistrationInfo() {
        super();
    }

    public ExtendedOAuth2ClientRegistrationInfo(OAuth2ClientRegistrationInfo oAuth2ClientRegistrationInfo,
                                                SchemeType domainScheme,
                                                String domainName) {
        super(oAuth2ClientRegistrationInfo);
        this.domainScheme = domainScheme;
        this.domainName = domainName;
    }
}
