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
package org.thingsboard.server.common.data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.thingsboard.server.common.data.id.EntityId;
import org.thingsboard.server.common.data.id.TenantId;
import org.thingsboard.server.common.data.id.ApiUsageStateId;

@ToString
@EqualsAndHashCode(callSuper = true)
public class ApiUsageState extends BaseData<ApiUsageStateId> implements HasTenantId {

    private static final long serialVersionUID = 8250339805336035966L;

    @Getter
    @Setter
    private TenantId tenantId;
    @Getter
    @Setter
    private EntityId entityId;
    @Getter
    @Setter
    private ApiUsageStateValue transportState;
    @Getter
    @Setter
    private ApiUsageStateValue dbStorageState;
    @Getter
    @Setter
    private ApiUsageStateValue reExecState;
    @Getter
    @Setter
    private ApiUsageStateValue jsExecState;
    @Getter
    @Setter
    private ApiUsageStateValue emailExecState;
    @Getter
    @Setter
    private ApiUsageStateValue smsExecState;

    public ApiUsageState() {
        super();
    }

    public ApiUsageState(ApiUsageStateId id) {
        super(id);
    }

    public ApiUsageState(ApiUsageState ur) {
        super(ur);
        this.tenantId = ur.getTenantId();
        this.entityId = ur.getEntityId();
        this.transportState = ur.getTransportState();
        this.dbStorageState = ur.getDbStorageState();
        this.reExecState = ur.getReExecState();
        this.jsExecState = ur.getJsExecState();
        this.emailExecState = ur.getEmailExecState();
        this.smsExecState = ur.getSmsExecState();
    }

    public boolean isTransportEnabled() {
        return !ApiUsageStateValue.DISABLED.equals(transportState);
    }

    public boolean isReExecEnabled() {
        return !ApiUsageStateValue.DISABLED.equals(reExecState);
    }

    public boolean isDbStorageEnabled() {
        return !ApiUsageStateValue.DISABLED.equals(dbStorageState);
    }

    public boolean isJsExecEnabled() {
        return !ApiUsageStateValue.DISABLED.equals(jsExecState);
    }

    public boolean isEmailSendEnabled(){
        return !ApiUsageStateValue.DISABLED.equals(emailExecState);
    }

    public boolean isSmsSendEnabled(){
        return !ApiUsageStateValue.DISABLED.equals(smsExecState);
    }
}
