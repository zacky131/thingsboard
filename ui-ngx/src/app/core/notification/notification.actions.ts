///
/// Copyright © 2020 SOLTEKNO.COM
///
/// Licensed under the Apache License, Version 2.0 (the "License");
/// you may not use this file except in compliance with the License.
/// You may obtain a copy of the License at
///
///     http://www.apache.org/licenses/LICENSE-2.0
///
/// Unless required by applicable law or agreed to in writing, software
/// distributed under the License is distributed on an "AS IS" BASIS,
/// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
/// See the License for the specific language governing permissions and
/// limitations under the License.
///

import { Action } from '@ngrx/store';
import { HideNotification, NotificationMessage } from '@app/core/notification/notification.models';

export enum NotificationActionTypes {
  SHOW_NOTIFICATION = '[Notification] Show',
  HIDE_NOTIFICATION = '[Notification] Hide'
}

export class ActionNotificationShow implements Action {
  readonly type = NotificationActionTypes.SHOW_NOTIFICATION;

  constructor(readonly notification: NotificationMessage ) {}
}

export class ActionNotificationHide implements Action {
  readonly type = NotificationActionTypes.HIDE_NOTIFICATION;

  constructor(readonly hideNotification: HideNotification ) {}
}

export type NotificationActions =
  | ActionNotificationShow | ActionNotificationHide;
