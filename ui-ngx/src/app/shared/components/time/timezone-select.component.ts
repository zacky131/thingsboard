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

import { AfterViewInit, Component, forwardRef, Input, NgZone, OnInit, ViewChild } from '@angular/core';
import { ControlValueAccessor, FormBuilder, FormGroup, NG_VALUE_ACCESSOR } from '@angular/forms';
import { Observable, of } from 'rxjs';
import { map, mergeMap, share, tap } from 'rxjs/operators';
import { Store } from '@ngrx/store';
import { AppState } from '@app/core/core.state';
import { TranslateService } from '@ngx-translate/core';
import { coerceBooleanProperty } from '@angular/cdk/coercion';
import * as _moment from 'moment-timezone';
import { MatAutocompleteTrigger } from '@angular/material/autocomplete';

interface TimezoneInfo {
  id: string;
  name: string;
  offset: string;
  nOffset: number;
}

@Component({
  selector: 'tb-timezone-select',
  templateUrl: './timezone-select.component.html',
  styleUrls: [],
  providers: [{
    provide: NG_VALUE_ACCESSOR,
    useExisting: forwardRef(() => TimezoneSelectComponent),
    multi: true
  }]
})
export class TimezoneSelectComponent implements ControlValueAccessor, OnInit, AfterViewInit {

  selectTimezoneFormGroup: FormGroup;

  modelValue: string | null;

  defaultTimezoneId: string = null;

  defaultTimezoneInfo: TimezoneInfo = null;

  timezones: TimezoneInfo[] = _moment.tz.names().map((zoneName) => {
    const tz = _moment.tz(zoneName);
    return {
      id: zoneName,
      name: zoneName.replace(/_/g, ' '),
      offset: `UTC${tz.format('Z')}`,
      nOffset: tz.utcOffset()
    }
  });

  @Input()
  set defaultTimezone(timezone: string) {
    if (this.defaultTimezoneId !== timezone) {
      this.defaultTimezoneId = timezone;
      if (this.defaultTimezoneId) {
        this.defaultTimezoneInfo =
          this.timezones.find((timezoneInfo) => timezoneInfo.id === this.defaultTimezoneId);
      } else {
        this.defaultTimezoneInfo = null;
      }
    }
  }

  private requiredValue: boolean;
  get required(): boolean {
    return this.requiredValue;
  }
  @Input()
  set required(value: boolean) {
    this.requiredValue = coerceBooleanProperty(value);
  }

  @Input()
  disabled: boolean;

  @ViewChild('timezoneInput', {static: true, read: MatAutocompleteTrigger}) timezoneInputTrigger: MatAutocompleteTrigger;

  filteredTimezones: Observable<Array<TimezoneInfo>>;

  searchText = '';

  ignoreClosePanel = false;

  private dirty = false;

  private propagateChange = (v: any) => { };

  constructor(private store: Store<AppState>,
              public translate: TranslateService,
              private ngZone: NgZone,
              private fb: FormBuilder) {
    this.selectTimezoneFormGroup = this.fb.group({
      timezone: [null]
    });
  }

  registerOnChange(fn: any): void {
    this.propagateChange = fn;
  }

  registerOnTouched(fn: any): void {
  }

  ngOnInit() {
    this.filteredTimezones = this.selectTimezoneFormGroup.get('timezone').valueChanges
      .pipe(
        tap(value => {
          let modelValue;
          if (typeof value === 'string' || !value) {
            modelValue = null;
          } else {
            modelValue = value.id;
          }
          this.updateView(modelValue);
          if (value === null) {
            this.clear();
          }
        }),
        map(value => value ? (typeof value === 'string' ? value : value.name) : ''),
        mergeMap(name => this.fetchTimezones(name) ),
        share()
      );
  }

  ngAfterViewInit(): void {
  }

  setDisabledState(isDisabled: boolean): void {
    this.disabled = isDisabled;
    if (this.disabled) {
      this.selectTimezoneFormGroup.disable({emitEvent: false});
    } else {
      this.selectTimezoneFormGroup.enable({emitEvent: false});
    }
  }

  writeValue(value: string | null): void {
    this.searchText = '';
    let foundTimezone: TimezoneInfo = null;
    if (value !== null) {
      foundTimezone = this.timezones.find(timezoneInfo => timezoneInfo.id === value);
    }
    if (foundTimezone !== null) {
      this.modelValue = value;
      this.selectTimezoneFormGroup.get('timezone').patchValue(foundTimezone, {emitEvent: false});
    } else {
      if (this.defaultTimezoneInfo) {
        this.selectTimezoneFormGroup.get('timezone').patchValue(this.defaultTimezoneInfo, {emitEvent: false});
        setTimeout(() => {
          this.updateView(this.defaultTimezoneInfo.id);
        }, 0);
      } else {
        this.modelValue = null;
        this.selectTimezoneFormGroup.get('timezone').patchValue('', {emitEvent: false});
      }
    }
    this.dirty = true;
  }

  onFocus() {
    if (this.dirty) {
      this.selectTimezoneFormGroup.get('timezone').updateValueAndValidity({onlySelf: true, emitEvent: true});
      this.dirty = false;
    }
  }

  onPanelClosed() {
    if (this.ignoreClosePanel) {
      this.ignoreClosePanel = false;
    } else {
      if (!this.modelValue && this.defaultTimezoneInfo) {
        this.ngZone.run(() => {
          this.selectTimezoneFormGroup.get('timezone').reset(this.defaultTimezoneInfo, {emitEvent: true});
        });
      }
    }
  }

  updateView(value: string | null) {
    if (this.modelValue !== value) {
      this.modelValue = value;
      this.propagateChange(this.modelValue);
    }
  }

  displayTimezoneFn(timezone?: TimezoneInfo): string | undefined {
    return timezone ? `${timezone.name} (${timezone.offset})` : undefined;
  }

  fetchTimezones(searchText?: string): Observable<Array<TimezoneInfo>> {
    this.searchText = searchText;
    let result = this.timezones;
    if (searchText && searchText.length) {
      result = this.timezones.filter((timezoneInfo) =>
       timezoneInfo.name.toLowerCase().includes(searchText.toLowerCase()));
    }
    return of(result);
  }

  clear() {
    this.selectTimezoneFormGroup.get('timezone').patchValue('', {emitEvent: true});
    setTimeout(() => {
      this.timezoneInputTrigger.openPanel();
    }, 0);
  }

}
