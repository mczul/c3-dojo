import {Component} from '@angular/core';
import {ProductCard, Details} from './product-card';

@Component({
  selector: 'c3-product-list',
  imports: [
    ProductCard
  ],
  template: `
    <h2>Our Products</h2>

    <div class="c3-product-list">
      @for (details of data; track details.id) {
        <c3-product-card [details]="details"/>
      }

    </div>
  `,
  styles: `
    .c3-product-list {
      margin-block: var(--space-700);
    }
  `,
})
export class ProductList {
  protected data: readonly Details[] = [
    {
      id: 'prod-1',
      title: 'First Product',
      name: 'c3-product-1',
      availability: 1,
      unit: "pcs",
      price: 47.11,
      currencySymbol: "€",
      description: `Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aspernatur beatae delectus, enim fuga
      magnam molestias quaerat sint sit voluptas voluptate! Asperiores dolorum esse excepturi illo mollitia natus qui
      quisquam veritatis!`,
      reviews: [
        {id: 4711, userName: 'Max Mustermann', comment: 'Fantastisches Produkt, klare Kaufempfehlung!', stars: 5, createdAt: new Date(2026, 0, 15, 12, 45)},
        {id: 4712, userName: 'Maria Mustermann', comment: 'I like it!', stars: 3, createdAt: new Date(2026, 0, 25, 8, 0)},
        {id: 4713, userName: 'Moritz Mustermann', comment: '👌', stars: 4.5, createdAt: new Date(2025, 7, 3, 16, 30)},
      ]
    },
    {
      id: 'prod-2',
      title: 'Second Product',
      name: 'c3-product-2',
      availability: 4.2,
      unit: "kg",
      price: 420.99,
      currencySymbol: "$",
      description: `Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aspernatur beatae delectus, enim fuga
      magnam molestias quaerat sint sit voluptas voluptate! Asperiores dolorum esse excepturi illo mollitia natus qui
      quisquam veritatis!

      Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aspernatur beatae delectus, enim fuga
      magnam molestias quaerat sint sit voluptas voluptate! Asperiores dolorum esse excepturi illo mollitia natus qui
      quisquam veritatis!`,
      reviews: [
        {id: 4811, userName: 'Mohammed Mustermann', comment: 'Hatte für den Preis schon etwas mehr erwartet!', stars: 2, createdAt: new Date(2025, 11, 24, 18, 45)},
        {id: 4812, userName: 'Maria Mustermann', comment: 'I like it!', stars: 3, createdAt: new Date(2026, 0, 25, 8, 0)},
      ]
    },
    {
      id: 'prod-3',
      title: 'Third Product',
      name: 'c3-product-3',
      availability: 2_500,
      unit: "ml",
      price: 815.50,
      currencySymbol: "€",
      description: `Lorem ipsum dolor sit amet, consectetur adipisicing elit.`,
      reviews: [
        {id: 4811, userName: 'Mao Mustermann', comment: 'So jucy!', stars: 5, createdAt: new Date(2025, 3, 10, 19, 15)},
        {id: 4812, userName: 'Maria Mustermann', comment: 'I like it!', stars: 3, createdAt: new Date(2026, 0, 25, 8, 0)},
      ]
    },
  ];
}
