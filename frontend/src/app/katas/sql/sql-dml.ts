import {Component, inject, OnDestroy, OnInit, signal} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Page} from '../../shared/model';
import {form, FormField, required} from '@angular/forms/signals';
import {FormsModule} from '@angular/forms';
import {finalize, Subject, takeUntil} from 'rxjs';
import {DecimalPipe, JsonPipe} from '@angular/common';

export type ContactQuery = {
  email: string;
  createdAfter: Date | null;
  createdBefore: Date | null;
}

export type ContactInfo = {
  id: string;
  email: string;
  name: string;
  createdAt: Date;
}

@Component({
  selector: 'c3-sql-dml',
  imports: [
    FormField,
    FormsModule,
    DecimalPipe,
    JsonPipe
  ],
  template: `
    <h2>
      Contacts
    </h2>

    <form (ngSubmit)="handleSubmit()">
      <fieldset>
        <legend>Basic search</legend>
        <section class="c3-form-field">
          <label for="contact-mail">Mail</label>
          <input id="contact-mail" type="text" [formField]="form.email">
        </section>

        <!-- TODO: Filter nach Zeitspannen derzeit noch dysfunktional -->
        <!--        <section class="c3-form-field">-->
        <!--          <label for="contact-created-after">Created after</label>-->
        <!--          <input id="contact-created-after" type="datetime-local" [formField]="form.createdAfter">-->
        <!--        </section>-->
        <!--        <section class="c3-form-field">-->
        <!--          <label for="contact-created-before">Created before</label>-->
        <!--          <input id="contact-created-before" type="datetime-local" [formField]="form.createdBefore">-->
        <!--        </section>-->
      </fieldset>
      <section class="c3-form__actions">
        <button type="submit" class="c3-button">Search</button>
      </section>
    </form>

    @let p = page();
    @let s = queryDurationInSeconds();

    @if (!!p) {
      <div>
        <dl>
          <dt>Query duration (in seconds)</dt>
          <dd>{{ s | number:'1.3-3' }}</dd>
          <dt>Matching records (total)</dt>
          <dd>{{ p.page.totalElements | number }}</dd>
        </dl>
      </div>

      <div>
        @for (info of p.content; track info.id) {
          <div class="contact">
            {{ info | json }}
          </div>
        } @empty {
          <h4>no matches found.</h4>
        }
      </div>
    }
  `,
  styles: `
    :host {
      display: flex;
      flex-direction: column;
      gap: var(--space-700);
    }

    .contact {
      display: flex;
      flex-direction: column;
      gap: var(--space-400);
      padding: var(--space-200) var(--space-300);

      border: solid 0.1em var(--clr-primary);

      white-space: preserve-breaks;

      &:not(:last-child) {
        margin-bottom: var(--space-400);
      }
    }
  `,
})
export class SqlDml implements OnInit, OnDestroy {
  protected httpClient = inject(HttpClient);
  protected destroyed$ = new Subject<void>();
  protected query = signal<ContactQuery>({
    email: '',
    createdBefore: null,
    createdAfter: null
  });
  protected form = form(this.query, (schemaPath) => {
    required(schemaPath.email);
  });
  protected page = signal<Page<ContactInfo> | undefined>(undefined);
  protected queryDurationInSeconds = signal<number | undefined>(undefined);

  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  // ~~~ Event handling

  protected handleSubmit(): void {
    const started = new Date();
    const params = new HttpParams()
      .set('size', 25)
      .set('page', 0)
      .set('sort', 'id,asc');

    this.httpClient.post<Page<ContactInfo>>('/api/sql-dml/search', this.form().value(), {params})
      .pipe(
        takeUntil(this.destroyed$),
        finalize(() => {
          this.queryDurationInSeconds.set((new Date().getTime() - started.getTime()) / 1_000);
        }),
      )
      .subscribe({
        next: data => {
          this.page.set(data);
        },
        error: err => {
          this.page.set(undefined);
        }
      })
  }

  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  // ~~~ Lifecycle

  ngOnInit(): void {

  }

  ngOnDestroy(): void {
    this.destroyed$.next();
    this.destroyed$.complete();
  }

}
