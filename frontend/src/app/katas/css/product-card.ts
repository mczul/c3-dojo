import {Component, computed, input} from '@angular/core';
import {DatePipe, DecimalPipe, NgOptimizedImage} from '@angular/common';

export type Details = {
  id: string;
  title: string;
  name: string;
  description: string;
  price: number;
  currencySymbol: '€' | '$';
  availability: number;
  unit: 'pcs' | 'kg' | 'ml';
  reviews: ReadonlyArray<{ id: number; userName: string; comment: string; stars: number; createdAt: Date }>
};

@Component({
  selector: 'c3-product-card',
  imports: [
    DecimalPipe,
    DatePipe,
    NgOptimizedImage
  ],
  template: `
    <article class="product">
      <h3 class="product__title">{{ details().title }} <br><small>{{ details().name }}</small></h3>
      <div class="product__availability">{{ details().availability | number }} {{ details().unit }} in stock</div>
      <div class="product__price">{{ details().price | number:'1.2-2' }} {{ details().currencySymbol }}</div>
      <div class="product__rating">🌟 {{ rating() | number:'1.1-1' }} 🌟</div>
      <div class="product__thumbnail">
        <img ngSrc="https://picsum.photos/id/237/320/240"
             width="320"
             height="240"
             priority
             alt="A image of product {{ details().name }}"/>
      </div>
      <section class="product__description">
        {{ details().description }}
      </section>
      <section class="product__reviews">
        @for (review of details().reviews; track review.id) {
          <div class="review">
            <h4 style="white-space: nowrap;">{{ review.userName }} | {{ review.createdAt | date }}</h4>
            <p>{{ review.comment }} | <span style="white-space: nowrap;">{{ review.stars }} 🌟</span></p>
          </div>
        }
      </section>
      <section class="product__actions">
        <button type="button"
                class="c3-button">Add to shopping cart</button>
      </section>
    </article>
  `,
  styles: `
    .product {
      margin: var(--space-400);
      padding: var(--space-200);
      border: solid 0.1rem var(--clr-primary);
      border-radius: var(--space-200);

      .product__title {
      }

      .product__thumbnail {
      }

      .product__availability {
      }

      .product__price {
        font-size: var(--fs-500);
        font-weight: bold;
      }

      .product__rating {
      }

      .product__reviews {
      }

      .product__actions {
        display: flex;
        justify-content: flex-end;
      }

      .product__description {
        white-space: preserve-breaks;
      }

      .product__availability,
      .product__price,
      .product__rating {
      }
    }
  `,
})
export class ProductCard {
  details = input.required<Details>();

  protected rating = computed(() => {
    return this.details().reviews
      .map(review => review.stars)
      .reduce((a, b) => a + b) / this.details().reviews.length;
  });

}
