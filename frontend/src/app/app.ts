import {Component} from '@angular/core';
import {RouterLink, RouterLinkActive, RouterOutlet} from '@angular/router';

@Component({
  selector: 'c3-root',
  imports: [RouterOutlet, RouterLink, RouterLinkActive],
  template: `
    <div id="site">
      <header>
        <h1 style="white-space: nowrap;">🥷 <a [routerLink]="['']">C3 Coding Dojo</a> 🥷</h1>

        <nav>
          <ul>
            <li><a [routerLink]="['katas', 'sql', 'dml']" routerLinkActive="c3-link--active">SQL :: DML</a></li>
            <li><a [routerLink]="['katas', 'css', 'marc']" routerLinkActive="c3-link--active">CSS :: Marc</a></li>
          </ul>
        </nav>
      </header>

      <main>
        <router-outlet/>
      </main>
    </div>
  `,
  styles: [],
})
export class App {
}
