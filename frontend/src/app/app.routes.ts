import {Routes} from '@angular/router';
import {Home} from './home';
import {ProductList} from './katas/css/product-list';
import {SqlDml} from './katas/sql/sql-dml';
import {SseDummy} from './katas/sse/sse-dummy';

export const routes: Routes = [
  {path: 'katas/sql/dml', component: SqlDml},
  {path: 'katas/css/marc', component: ProductList},
  {path: 'katas/sse/dummy', component: SseDummy},
  {path: '', component: Home, pathMatch: 'full'},
];
